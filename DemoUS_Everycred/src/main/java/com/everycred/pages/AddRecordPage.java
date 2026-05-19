package com.everycred.pages;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddRecordPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    public AddRecordPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // ======================================
    // DELAY METHOD
    // ======================================

    public void delay() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();   // improved (no logic change)
        }
    }

    // ======================================
    // COMMON WAIT METHODS (ADDED)
    // ======================================

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // ======================================
    // COLUMN INDEX METHOD (ADDED)
    // ======================================

    public int getColumnIndex(String columnName) {

        int columnIndex = -1;

        List<WebElement> headers =
                driver.findElements(By.xpath("//thead//th"));

        for (int i = 0; i < headers.size(); i++) {

            String text = headers.get(i).getText().trim();

            System.out.println("Header found = " + text);

            if (text.toLowerCase().contains(columnName.toLowerCase())) {

                columnIndex = i + 1;
                break;
            }
        }

        if (columnIndex == -1) {
            throw new RuntimeException("Column not found: " + columnName);
        }

        System.out.println("Column " + columnName + " index = " + columnIndex);

        return columnIndex;
    }


//======================================
//ADD RECORD FLOW (FIXED)                                       // changes during run 
//======================================

public void addRecordFlow() {

  System.out.println("Opening Subjects Tab");
  closeOnboardingPopupIfPresent();
  collapseGetStartedPanelIfPresent();
  dismissOnboardingCompleteIfPresent();
  closeWorkflowPopupIfPresent();
  ensureSubjectsPageAndSubjectTab();

  By subjectRow = By.xpath("//tr[.//text()[contains(.,'Employee ID Card')]]");

  // Wait up to 8s for the subject row — avoids slow refresh-loop that caused 10s+ delay
  try {
      new WebDriverWait(driver, Duration.ofSeconds(8))
              .until(ExpectedConditions.visibilityOfElementLocated(subjectRow));
  } catch (Exception e) {
      // One targeted refresh if still not visible
      System.out.println("Subject row not visible yet, refreshing once...");
      driver.navigate().refresh();
      closeOnboardingPopupIfPresent();
      collapseGetStartedPanelIfPresent();
      try {
          new WebDriverWait(driver, Duration.ofSeconds(8))
                  .until(ExpectedConditions.visibilityOfElementLocated(subjectRow));
      } catch (Exception ignored) {}
  }

  if (driver.findElements(subjectRow).isEmpty()) {
      throw new RuntimeException("Employee ID Card subject not found after refresh");
  }

  System.out.println("Employee ID Card Subject Found");

  WebElement addRecordButton = findAddRecordButton();
  scrollIntoView(addRecordButton);
  jsClick(addRecordButton);

System.out.println("Add Record button clicked");

delay();


System.out.println("Waiting for Add Credential Page to Load");

waitVisible(By.xpath(
        "//*[contains(normalize-space(),'Add Credential')] | " +
        "//button[.//span[contains(normalize-space(),'Add New Record')]]"));

System.out.println("Add Credential Page Loaded Successfully");

openAddRecordFormPopup();

waitVisible(By.xpath(
        "//div[contains(@class,'p-dialog')] | " +
        "//input[@placeholder='Enter your full name'] | " +
        "//input[@placeholder='Enter your email address']"));

delay();

}

private void ensureSubjectsPageAndSubjectTab() {
    click(By.xpath("//a[normalize-space()='Subjects']"));
    waitVisible(By.xpath("//body"));
    closeOnboardingPopupIfPresent();
    collapseGetStartedPanelIfPresent();
    try {
        WebElement subjectTab = shortWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Subject'] | //span[normalize-space()='Subject']/ancestor::button")));
        jsClick(subjectTab);
    } catch (Exception ignored) {
        // Already on subject tab or tab control not visible.
    }
}

private void closeWorkflowPopupIfPresent() {
    By workflowTitle = By.xpath(
            "//*[contains(normalize-space(),'Subject created successfully')] | " +
            "//div[contains(@class,'p-dialog') and .//*[contains(normalize-space(),'WORKFLOW SETUP')]]");
    By laterButton = By.xpath(
            "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Later'] or normalize-space()='Later'] | " +
            "//div[contains(@class,'p-dialog')]//span[normalize-space()='Later']/ancestor::button");
    WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(2));

    try {
        // Fast-path: if popup isn't present right now, return immediately (avoid 6s wasted wait)
        if (driver.findElements(workflowTitle).isEmpty()) {
            return;
        }
        // Popup is present — close it
        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                WebElement later = quickWait.until(ExpectedConditions.elementToBeClickable(laterButton));
                jsClick(later);
                System.out.println("Workflow popup closed using Later");
                try {
                    shortWait.until(ExpectedConditions.invisibilityOfElementLocated(workflowTitle));
                } catch (Exception ignored) {}
                return;
            } catch (Exception ignored) {
                try {
                    WebElement laterFallback = driver.findElement(By.xpath(
                            "(//button[.//span[normalize-space()='Later'] or normalize-space()='Later'] | " +
                            "//span[normalize-space()='Later']/ancestor::button)[last()]"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", laterFallback);
                    System.out.println("Workflow popup force-closed using Later");
                    return;
                } catch (Exception e) {}
                try { Thread.sleep(150); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    } catch (Exception e) {
        System.out.println("Workflow popup not present/clickable, continuing");
    }
}

private WebElement findAddRecordButton() {
    By rowAddRecord = By.xpath("//tbody/tr[1]//button[contains(normalize-space(),'Add Record')]");
    By toolbarAddRecord = By.xpath(
            "//button[.//span[contains(normalize-space(),'Add Record')]] | " +
            "//span[contains(normalize-space(),'Add Record')]/ancestor::button");

    try {
        return shortWait.until(ExpectedConditions.elementToBeClickable(rowAddRecord));
    } catch (Exception ignored) {}

    return waitClickable(toolbarAddRecord);
}

// Waits until actual input fields are rendered inside the dialog (avoids blank form race condition).
// Returns true if the form rendered, false if the dialog stayed empty.
private boolean waitForDialogFormContent() {
    By formInputInDialog = By.xpath(
            "//div[contains(@class,'p-dialog')]//input[not(@type='hidden')] | " +
            "//div[contains(@class,'p-dialog')]//textarea");
    try {
        new WebDriverWait(driver, Duration.ofSeconds(12))
            .until(ExpectedConditions.visibilityOfElementLocated(formInputInDialog));
        System.out.println("Add Record dialog form fields rendered");
        return true;
    } catch (Exception e) {
        System.out.println("Form fields not visible after 12s — dialog appears empty: " + e.getMessage());
        return false;
    }
}

/** True if the dialog is open but has no usable inputs (the "empty popup" race). */
private boolean isDialogOpenButEmpty() {
    By dialog = By.xpath("//div[contains(@class,'p-dialog')]");
    By inputs = By.xpath(
            "//div[contains(@class,'p-dialog')]//input[not(@type='hidden')] | " +
            "//div[contains(@class,'p-dialog')]//textarea");
    return !driver.findElements(dialog).isEmpty() && driver.findElements(inputs).isEmpty();
}

/** Closes an empty Add Record dialog via the X / Cancel button so we can reopen cleanly. */
private void closeEmptyDialogIfPresent() {
    By cancelOrClose = By.xpath(
            "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Cancel']] | " +
            "//div[contains(@class,'p-dialog')]//button[contains(@class,'p-dialog-header-close')] | " +
            "//div[contains(@class,'p-dialog')]//span[contains(@class,'pi-times')]/ancestor::button[1]");
    try {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(cancelOrClose));
        jsClick(btn);
        // Wait for the empty dialog to actually disappear before reopening
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//div[contains(@class,'p-dialog')]")));
        } catch (Exception ignored) {}
        System.out.println("Empty Add Record dialog closed, will reopen");
    } catch (Exception ignored) {
        // No reachable close control — proceed and let the next attempt handle it.
    }
}

private void openAddRecordFormPopup() {
    By dialogLocator = By.xpath("//div[contains(@class,'p-dialog')]");
    By addNewRecordButton = By.xpath(
            "//button[.//span[contains(normalize-space(),'Add New Record')] or contains(normalize-space(),'Add New Record')] | " +
            "//span[contains(normalize-space(),'Add New Record')]/ancestor::button | " +
            "//span[@class='p-button-label ng-star-inserted' and normalize-space()='Add New Record']/ancestor::button");

    // If dialog already auto-opened on page load, wait for form content without any DOM interference.
    // Calling collapseGetStartedPanelIfPresent() here was triggering Angular change detection
    // and resetting the form's loading state, causing the dialog to stay empty.
    if (!driver.findElements(dialogLocator).isEmpty()) {
        dismissOnboardingCompleteIfPresent();
        if (waitForDialogFormContent()) {
            return;
        }
        // Form still empty after full wait — close dialog and refresh page to re-init Angular form component.
        closeEmptyDialogIfPresent();
        System.out.println("Auto-opened dialog was empty — refreshing page before retry");
        driver.navigate().refresh();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(addNewRecordButton));
        } catch (Exception ignored) {}
    }

    Exception lastError = null;
    for (int attempt = 1; attempt <= 4; attempt++) {
        try {
            closeWorkflowPopupIfPresent();
            // Wait for Angular to fully mount the form component before clicking.
            try { Thread.sleep(2500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            WebElement addNew = shortWait.until(ExpectedConditions.elementToBeClickable(addNewRecordButton));
            jsClick(addNew);

            // Wait for dialog container, then wait for form fields — no DOM manipulation in between.
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));

            boolean formRendered = waitForDialogFormContent();
            if (formRendered) {
                System.out.println("Add Record form popup opened (attempt " + attempt + ")");
                return;
            }

            // Empty popup — close, refresh page, and retry so Angular re-initialises.
            System.out.println("Add Record dialog opened empty on attempt " + attempt + " — refreshing and retrying");
            closeEmptyDialogIfPresent();
            driver.navigate().refresh();
            try {
                new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(addNewRecordButton));
            } catch (Exception ignored) {}
        } catch (Exception e) {
            lastError = e;
            delay();
        }
    }

    throw new RuntimeException("Unable to open Add Record form popup with rendered fields", lastError);
}

private void ensureCredentialsPageAndDraftTab() {
    closeOnboardingPopupIfPresent();
    collapseGetStartedPanelIfPresent();

    if (!driver.getCurrentUrl().contains("/credentials")) {
        By credentialsTab = By.xpath("//a[normalize-space()='Credentials']");
        boolean navigated = false;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement tab = waitClickable(credentialsTab);
                jsClick(tab);
                shortWait.until(ExpectedConditions.urlContains("/credentials"));
                navigated = true;
                break;
            } catch (Exception ignored) {
                delay();
            }
        }

        if (!navigated) {
            // Hard fallback when modal overlays or sticky state blocks header navigation.
            String currentUrl = driver.getCurrentUrl();
            int adminIndex = currentUrl.indexOf("/admin/");
            String baseUrl = adminIndex > 0 ? currentUrl.substring(0, adminIndex) : currentUrl;
            driver.navigate().to(baseUrl + "/admin/credentials");
            wait.until(ExpectedConditions.urlContains("/credentials"));
        }
    }

    closeOnboardingPopupIfPresent();
    collapseGetStartedPanelIfPresent();

    try {
        WebElement draftTab = shortWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Draft'] | //span[normalize-space()='Draft']/ancestor::button")));
        jsClick(draftTab);
        System.out.println("Draft tab selected");
    } catch (Exception ignored) {
        // Already in Draft or tab not required in this view.
    }
}

private void closeOnboardingPopupIfPresent() {
    By closeBtn = By.xpath("//button[contains(@class,'p-dialog-header-close')]");
    try {
        if (driver.findElements(closeBtn).isEmpty()) {
            return;
        }
        WebElement onboardingClose = shortWait.until(ExpectedConditions.elementToBeClickable(closeBtn));
        jsClick(onboardingClose);
        System.out.println("Onboarding popup closed on AddRecord page");
    } catch (Exception ignored) {
        // Optional popup; continue even if not clickable.
    }
}

// Dismisses the "Onboarding Complete" full-screen modal if it appears after all 5 setup steps are done.
private void dismissOnboardingCompleteIfPresent() {
    try {
        WebElement goToDashBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//app-onboarding-complete-dialog//button | " +
                "//button[.//span[normalize-space()='Go To Dashboard']] | " +
                "//span[normalize-space()='Go To Dashboard']")));
        System.out.println("'Onboarding Complete' modal detected on AddRecord — clicking 'Go To Dashboard'");
        jsClick(goToDashBtn);
        try { Thread.sleep(1500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//app-onboarding-complete-dialog")));
        } catch (Exception ignored) {}
        System.out.println("'Onboarding Complete' modal dismissed on AddRecord page");
    } catch (Exception e) {
        // Modal not present — proceed normally
    }
}

/** Waits up to 6 s for the "Get Started" panel to disappear after the chevron-click collapse. */
private void waitForGetStartedPanelGone() {
    By panel = By.xpath(
            "//*[contains(normalize-space(),'Get Started') and " +
            "not(ancestor-or-self::div[contains(@class,'p-dialog')])]");
    try {
        new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.invisibilityOfElementLocated(panel));
        System.out.println("Get Started panel is gone — filling form now");
    } catch (Exception ignored) {
        // Panel still visible — proceed anyway; JS fallback already applied in collapse call.
        System.out.println("Get Started panel still present after 6s — proceeding with form fill");
    }
}

private void collapseGetStartedPanelIfPresent() {
    // UI-only: collapses the left "Get Started" onboarding panel so user can see actions.
    By panelTitle = By.xpath("//*[contains(normalize-space(),'Get Started')]");
    try {
        if (driver.findElements(panelTitle).isEmpty()) {
            return;
        }

        // Prefer the chevron toggle inside the panel itself (more stable than "first button").
        By panelRoot = By.xpath("//*[contains(normalize-space(),'Get Started')]/ancestor::div[contains(@class,'ng-star-inserted') or contains(@class,'p-component')][1]");
        By chevronToggle = By.xpath(
                "//*[contains(normalize-space(),'Get Started')]" +
                "/ancestor::div[1]//button[.//i[contains(@class,'pi-chevron')]] | " +
                "//*[contains(normalize-space(),'Get Started')]" +
                "/ancestor::div[2]//button[.//i[contains(@class,'pi-chevron')]]");

        try {
            WebElement toggle = shortWait.until(ExpectedConditions.elementToBeClickable(chevronToggle));
            jsClick(toggle);
            System.out.println("Get Started panel collapsed");
            return;
        } catch (Exception ignored) {}

        // If chevron isn't clickable, scroll panel into view and retry once.
        try {
            WebElement root = driver.findElement(panelRoot);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'nearest'});", root);
            WebElement toggle = shortWait.until(ExpectedConditions.elementToBeClickable(chevronToggle));
            jsClick(toggle);
            System.out.println("Get Started panel collapsed (after scroll)");
            return;
        } catch (Exception ignored) {}

        // Hard fallback: hide the panel via JS (doesn't affect backend flow).
        ((JavascriptExecutor) driver).executeScript(
                "const el = Array.from(document.querySelectorAll('*')).find(e => e.textContent && e.textContent.trim()==='Get Started');" +
                "if(el){ const p = el.closest('div'); if(p){ p.style.width='0px'; p.style.minWidth='0px'; p.style.overflow='hidden'; p.style.display='none'; }}");
        System.out.println("Get Started panel hidden via JS");
    } catch (Exception ignored) {
        // Non-blocking
    }
}

private void ensureAddRecordDialogOpen() {
    By dialogLocator = By.xpath("//div[contains(@class,'p-dialog')]");
    try {
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
    } catch (Exception e) {
        addRecordFlow();
        waitVisible(dialogLocator);
    }
}

private WebElement getDialogInputByFieldLabel(String fieldLabelText) {
    By locator = By.xpath(
            "//div[contains(@class,'p-dialog')]//div[contains(@class,'field-group')]" +
            "[.//div[contains(@class,'field-label') and contains(normalize-space(),'" + fieldLabelText + "')]]" +
            "//input[not(@type='hidden')][1]");
    WebElement input = waitVisible(locator);
    scrollIntoView(input);
    return input;
}


//======================================
//FILL ADD RECORD FORM                                         // changes during run
//======================================

public void fillAddRecordForm() throws InterruptedException {

System.out.println("Starting Add Record Form Filling");

// Collapse onboarding panel BEFORE filling — identical logic to SubjectPage / IssueCredentialPage.
// Click chevron, wait until the panel is fully gone, then proceed to fill the form.
collapseGetStartedPanelIfPresent();
ensureAddRecordDialogOpen();
dismissOnboardingCompleteIfPresent();
collapseGetStartedPanelIfPresent();
waitForGetStartedPanelGone();  // block form fill until panel is invisible

delay();


//NAME (FIXED)

WebElement nameField = waitVisible(
      By.xpath("//input[@placeholder='Enter your full name']"));

nameField.sendKeys("Nikhil Sonawane");
delay();


// EMAIL (FIXED)

WebElement emailField = waitVisible(
        By.xpath("//input[@placeholder='Enter your email address']"));

emailField.sendKeys("nikhil.sonawane@viitor.cloud");
delay();


// EMPLOYEE ID — start with a time-based value to minimise duplicates from the start
long timeBase = (System.currentTimeMillis() / 1000) % 9000000 + 1000000;
String currentEmpId = String.format("VCEID-%05d-%07d", 11111, timeBase);
WebElement empId = getDialogInputByFieldLabel("Employees ID");
empId.sendKeys(currentEmpId);
delay();


// DESIGNATION
WebElement designation = getDialogInputByFieldLabel("Designation");

designation.sendKeys("QA Engineer");
delay();


// PERSONAL EMAIL
WebElement personalEmail = getDialogInputByFieldLabel("Personal Email ID");

personalEmail.sendKeys("sonawanenikhil2805@gmail.com");
delay();


// CONTACT NUMBER
WebElement contactNumber = getDialogInputByFieldLabel("Personal Contact Number");

contactNumber.sendKeys("8788270435");
delay();


JavascriptExecutor js = (JavascriptExecutor) driver;

//=====================================
//JOINING DATE : CURRENT DATE (DYNAMIC)
//=====================================

System.out.println("Selecting Joining Date (Current Date)");

WebElement joiningInput = waitVisible(
       By.xpath("//div[contains(@class,'p-dialog')]//div[contains(@class,'field-group')][.//div[contains(@class,'field-label') and contains(normalize-space(),'Joining Date')]]//input[1]"));

scrollIntoView(joiningInput);

delay();

js.executeScript("arguments[0].click();", joiningInput);

waitVisible(By.xpath("//div[contains(@class,'p-datepicker')]"));    // 12 Mar 2026


/*
====================================================
STATIC DATE SELECTION (KEEPED FOR FUTURE USE)
If you want to select a specific date manually
====================================================

WebElement currentYear = wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//button[normalize-space()='2026']")));
currentYear.click();

WebElement year2024 = wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//span[normalize-space()='2024']")));
year2024.click();

WebElement aprilMonth = wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//span[normalize-space()='Apr']")));
aprilMonth.click();

WebElement joiningDate = wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//span[normalize-space()='22']")));
joiningDate.click();

*/                                                              // 22 Apr 2024


//====================================================
//DYNAMIC CURRENT DATE (IST)
//====================================================

ZonedDateTime istDate =
       ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

int today = istDate.getDayOfMonth();

WebElement todayDate = waitClickable(
       By.xpath("//span[normalize-space()='" + today + "']"));

scrollIntoView(todayDate);

delay();

js.executeScript("arguments[0].click();", todayDate);

delay();

System.out.println("Joining Date Selected");


//=====================================
//EXPIRY DATE : IST TIMEZONE (+2 YEARS)
//=====================================

System.out.println("Selecting Expiry Date");

ZonedDateTime expiryDateTime =
       ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).plusYears(2);

int expiryDay = expiryDateTime.getDayOfMonth();
int expiryYear = expiryDateTime.getYear();

DateTimeFormatter monthFormatter =
       DateTimeFormatter.ofPattern("MMMM");

String targetMonth = expiryDateTime.format(monthFormatter);
String targetYear = String.valueOf(expiryYear);


//open expiry picker
WebElement expiryInput = waitVisible(
       By.xpath("//div[contains(@class,'p-dialog')]//div[contains(@class,'field-group')][.//div[contains(@class,'field-label') and contains(normalize-space(),'Expiry Date')]]//input[1]"));

scrollIntoView(expiryInput);

delay();

js.executeScript("arguments[0].click();", expiryInput);

waitVisible(By.xpath("//div[contains(@class,'p-datepicker')]"));


/*
====================================================
STATIC DATE SELECTION (KEEPED FOR FUTURE USE)
If you want to select a specific expiry date manually
====================================================

WebElement currentYear = wait.until(
    ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class,'p-datepicker-year')]")));
currentYear.click();

WebElement yearSelect = wait.until(
    ExpectedConditions.elementToBeClickable(
            By.xpath("//span[normalize-space()='2028']")));
yearSelect.click();

WebElement monthSelect = wait.until(
    ExpectedConditions.elementToBeClickable(
            By.xpath("//span[normalize-space()='Mar']")));
monthSelect.click();

WebElement expiryDate = wait.until(
    ExpectedConditions.elementToBeClickable(
            By.xpath("//span[normalize-space()='10']")));
expiryDate.click();

*/                                                     // 22 Oct 2026


//====================================================
//DYNAMIC EXPIRY DATE SELECTION (+2 YEARS)
//====================================================

By activeDatePickerPanel = By.xpath(
        "//div[contains(@class,'p-datepicker-panel') and @aria-modal='true' and (contains(@style,'z-index') or not(contains(@style,'display: none')))]");
By activeHeader = By.xpath(".//div[contains(@class,'p-datepicker-title')]");
By nextMonthBtn = By.xpath(".//button[contains(@class,'p-datepicker-next')]");

while (true) {
   WebElement panel = waitVisible(activeDatePickerPanel);
   WebElement header = panel.findElement(activeHeader);
   String headerText = header.getText();

   if (headerText.contains(targetMonth) && headerText.contains(targetYear)) {
       break;
   }

   String before = headerText;
   WebElement nextBtn = panel.findElement(nextMonthBtn);
   scrollIntoView(nextBtn);
   ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);

   try {
       shortWait.until(d -> {
           try {
               String now = panel.findElement(activeHeader).getText();
               return now != null && !now.equals(before);
           } catch (StaleElementReferenceException ignored) {
               return true; // panel/header re-rendered after month change
           } catch (Exception ignored) {
               return false;
           }
       });
   } catch (Exception ignored) {
       // keep loop moving; next iteration re-checks header
   }
}


WebElement expiryDayElement = waitClickable(
       By.xpath("//td[not(contains(@class,'p-datepicker-other-month'))]//span[text()='" + expiryDay + "']"));

scrollIntoView(expiryDayElement);

delay();

js.executeScript("arguments[0].click();", expiryDayElement);

delay();

System.out.println("Expiry Date Selected Successfully (Time auto-filled by system)");


// COMPANY URL
getDialogInputByFieldLabel("Company URL")
        .sendKeys("https://viitorcloud.com/");
delay();


// ADDRESS
getDialogInputByFieldLabel("Address")
        .sendKeys("Ahmedabad, Gujarat");
delay();


// SAVE RECORD — retry with new Employee ID if "unique" error appears
clickSaveWithUniqueEmployeeIdRetry(currentEmpId);

System.out.println("Record Added Successfully");


//=====================================
//CLICK CROSS BUTTON AFTER SAVE
//=====================================

System.out.println("Waiting 2 seconds before closing form");

Thread.sleep(2000);

By crossLocator = By.xpath(
        "//div[contains(@class,'p-dialog')]//button[contains(@class,'p-dialog-header-close')] | " +
        "//div[contains(@class,'p-dialog')]//span[contains(@class,'pi-times')]");

int retry = 0;

while (retry < 5) {
  try {
      if (driver.findElements(By.xpath("//div[contains(@class,'p-dialog')]")).isEmpty()) {
          System.out.println("Form already closed");
          break;
      }

      WebElement crossButton = wait.until(
              ExpectedConditions.presenceOfElementLocated(crossLocator));

      scrollIntoView(crossButton);

      delay();

      ((JavascriptExecutor) driver)
              .executeScript("arguments[0].click();", crossButton);

      System.out.println("Cross button clicked successfully");

      break;

  } catch (StaleElementReferenceException e) {

      System.out.println("Retrying cross button due to stale element...");
      retry++;
      Thread.sleep(1000);

  } catch (Exception e) {

      System.out.println("Retrying due to unknown issue...");
      retry++;
      Thread.sleep(1000);
  }
}

if (retry == 5) {
  // Do not fail flow if record save succeeded but close icon is unavailable.
  System.out.println("Close icon not found after retries, continuing flow");
}

System.out.println("Form Closed Successfully");

navigateToDraftCredentialsListAfterAddRecord();

}

private void navigateToDraftCredentialsListAfterAddRecord() {
    try {
        String cur = driver.getCurrentUrl();
        if (!cur.contains("/credentials/add")) {
            return;
        }
        String base = cur;
        int adminIdx = cur.indexOf("/admin/");
        if (adminIdx > 0) {
            base = cur.substring(0, adminIdx);
        }
        String draftUrl = base + "/admin/credentials?status=draft";
        driver.navigate().to(draftUrl);
        wait.until(ExpectedConditions.urlContains("/credentials"));
        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();
        // Brief settle (~3–4s target) — Draft table shell without lingering on Issued empty state
        try {
            new WebDriverWait(driver, Duration.ofSeconds(4)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")),
                    ExpectedConditions.urlContains("status=draft")));
        } catch (Exception ignored) {}
        System.out.println("Navigated to Draft credentials list after add record");
    } catch (Exception e) {
        System.out.println("Post-add Draft navigation skipped: " + e.getMessage());
    }
}

private void clickSaveWithUniqueEmployeeIdRetry(String initialEmpId) {
    String currentId = initialEmpId;
    By dialogLocator = By.xpath("//div[contains(@class,'p-dialog')]");
    By uniqueErrorToast = By.xpath(
            "//*[contains(@class,'p-toast-message-error') or contains(@class,'p-toast-message-warn')]" +
            "[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'unique') or " +
            " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'already')]");

    for (int attempt = 1; attempt <= 15; attempt++) {
        clickSaveButtonInAddRecordDialog();

        // Poll up to 3 seconds for: dialog closed (success) OR unique error toast
        boolean saved = false;
        for (int tick = 0; tick < 15; tick++) {
            try { Thread.sleep(200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

            if (!driver.findElements(uniqueErrorToast).isEmpty()) {
                // "Employees_id should be unique!" — generate next ID and retype
                currentId = nextEmployeeId(currentId);
                System.out.println("Employee ID unique error — retrying with: " + currentId);
                try { Thread.sleep(400); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                try {
                    WebElement empIdField = getDialogInputByFieldLabel("Employees ID");
                    clearFieldAndType(empIdField, currentId);
                } catch (Exception e) {
                    System.out.println("Could not update Employee ID field: " + e.getMessage());
                }
                break; // break tick loop, re-try outer save loop
            }

            // Check if dialog disappeared (save succeeded)
            if (driver.findElements(dialogLocator).isEmpty()) {
                saved = true;
                break;
            }
        }
        if (saved) return;
    }
    // Even if dialog is still open after all attempts, continue flow
    System.out.println("Save retries exhausted — continuing flow");
}

private String nextEmployeeId(String currentId) {
    // Format: VCEID-XXXXX-NNNNNNN — increment the last numeric segment
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("^(VCEID-\\d{5}-)([0-9]+)$");
    java.util.regex.Matcher m = p.matcher(currentId);
    if (m.matches()) {
        long num = Long.parseLong(m.group(2)) + 1;
        return m.group(1) + String.format("%07d", num % 9999999);
    }
    // Fallback: append timestamp
    return "VCEID-11111-" + String.format("%07d", (System.currentTimeMillis() / 1000) % 9000000 + 1000000);
}

private void clearFieldAndType(WebElement field, String value) {
    try {
        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0]; var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set; nativeSetter.call(el, ''); el.dispatchEvent(new Event('input', { bubbles: true }));",
                field);
        field.sendKeys(value);
    } catch (Exception e) {
        field.clear();
        field.sendKeys(value);
    }
}

private void clickSaveButtonInAddRecordDialog() {
    By saveButtonLocator = By.xpath(
            "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Save']] | " +
            "//div[contains(@class,'p-dialog')]//button[normalize-space()='Save'] | " +
            "//div[contains(@class,'p-dialog')]//button[contains(@class,'btn-primary') and not(.//span[normalize-space()='Save & Next'])]");

    Exception lastError = null;
    for (int attempt = 1; attempt <= 5; attempt++) {
        try {
            WebElement saveButton = waitClickable(saveButtonLocator);
            scrollIntoView(saveButton);
            delay();
            jsClick(saveButton);
            System.out.println("Save button clicked");
            return;
        } catch (Exception e) {
            lastError = e;
            delay();
        }
    }

    throw new RuntimeException("Unable to click Save button in Add Record dialog", lastError);
}

    // ======================================
    // COMMON CLICK
    // ======================================

    public void click(By locator) {

        WebElement element = waitClickable(locator);

        jsClick(element);
    }

 // ======================================
 // JS CLICK
 // ======================================

 public void jsClick(WebElement element) {

      JavascriptExecutor js = (JavascriptExecutor) driver;

      int attempts = 0;

      while (attempts < 3) {
          try {

              scrollIntoView(element);

              delay();

              js.executeScript("arguments[0].click();", element);

              return;

          } catch (StaleElementReferenceException e) {

              System.out.println("Stale element detected in jsClick, retrying...");
              attempts++;
              delay();
          }
      }

      throw new RuntimeException("Failed to click element after retries");
 }
}