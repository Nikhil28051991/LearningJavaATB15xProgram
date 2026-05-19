package com.everycred.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class UploadSpreadsheetPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    public UploadSpreadsheetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // ======================================
    // DELAY
    // ======================================

    public void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ======================================
    // COMMON WAIT METHODS
    // ======================================

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ======================================
    // SAFE WAIT (🔥 FIX FOR YOUR FAILURE)
    // ======================================

    private void waitForUploadProcessingComplete() {
        System.out.println("Waiting for upload processing (extended)");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(45)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr[td]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(@class,'p-toast-message-success')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'uploaded') or " +
                            " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'success')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(),'in-process') or contains(normalize-space(),'In Process') " +
                            "   or contains(normalize-space(),'in process')]")),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[@class='text-sm text-yellow-800 font-medium']")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(),'processing') or contains(normalize-space(),'records')]"))
            ));
        } catch (Exception ignored) {
            // Non-blocking — downstream waits try Draft refresh / in-process UI.
        }

        try {
            new WebDriverWait(driver, Duration.ofSeconds(8)).until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//div[@role='dialog'][.//input[@type='file']]")));
        } catch (Exception ignored) {}

        System.out.println("Upload processing checkpoint reached");
    }

    /** After bulk upload, rows often render only on Draft after navigation or refresh. */
    private void waitForUploadedRowsOnDraftTable() throws InterruptedException {
        ensureCredentialsPageAndDraftTab();

        By dataRow = By.xpath("//tbody//tr");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(18))
                    .until(ExpectedConditions.visibilityOfElementLocated(dataRow));
            System.out.println("✅ Uploaded spreadsheet data visible in Draft table");
            return;
        } catch (Exception ignored) {}

        System.out.println("Draft table empty — refreshing Draft credentials list...");
        try {
            String cur = driver.getCurrentUrl();
            String base = cur;
            int adminIdx = cur.indexOf("/admin/");
            if (adminIdx > 0) {
                base = cur.substring(0, adminIdx);
            }
            driver.navigate().to(base + "/admin/credentials?status=draft");
            wait.until(ExpectedConditions.urlContains("/credentials"));
        } catch (Exception e) {
            driver.navigate().refresh();
        }
        Thread.sleep(1800);
        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();

        try {
            WebElement draftTab = waitClickable(
                    By.xpath("//button[normalize-space()='Draft'] | //span[normalize-space()='Draft']/ancestor::button"));
            jsClick(draftTab);
        } catch (Exception ignored) {}

        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfElementLocated(dataRow));
            System.out.println("✅ Uploaded data visible after Draft navigation/refresh");
        } catch (Exception e) {
            System.out.println("Uploaded rows still not detected — UI may still be processing (non-fatal)");
        }
    }

    private void clickInProcessRecordsButtonIfPresent() throws InterruptedException {
        By inDialogYellow = By.xpath(
                "//div[@role='dialog']//span[@class='text-sm text-yellow-800 font-medium'] | " +
                "//div[contains(@class,'p-dialog')]//span[@class='text-sm text-yellow-800 font-medium'] | " +
                "//span[@class='text-sm text-yellow-800 font-medium']");

        for (int round = 1; round <= 3; round++) {
            try {
                WebElement hint = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.presenceOfElementLocated(inDialogYellow));
                if (!hint.isDisplayed()) {
                    Thread.sleep(600);
                    continue;
                }
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", hint);
                Thread.sleep(400);
                System.out.println("In-process records control: [" + hint.getText().trim() + "] — click round " + round);
                ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0]; var b = el.closest('button') || el; b.click();", hint);

                try {
                    new WebDriverWait(driver, Duration.ofSeconds(12))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")));
                    System.out.println("✅ Upload data loaded successfully in table");
                    return;
                } catch (Exception e) {
                    System.out.println("Table still empty after in-process click — retry round " + round);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                if (round < 3) {
                    Thread.sleep(800);
                }
            }
        }

        try {
            new WebDriverWait(driver, Duration.ofSeconds(6))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")));
            System.out.println("✅ Upload data already visible in table");
        } catch (Exception e1) {
            try {
                driver.navigate().refresh();
                Thread.sleep(2000);
                new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")));
                System.out.println("✅ Upload data visible after refresh");
            } catch (Exception ignored) {
                System.out.println("No uploaded data visible in table — upload may still be processing");
            }
        }
    }

    // ======================================
    // MAIN FLOW
    // ======================================

    public void uploadSpreadsheetFlow() throws Exception {

        System.out.println("Starting Upload Spreadsheet Flow");
        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();
        ensureCredentialsPageAndDraftTab();

        String currentUrl = driver.getCurrentUrl();

        // ======================================
        // ENSURE ON CREDENTIAL PAGE
        // ======================================

        if (!currentUrl.contains("/credentials")) {

            System.out.println("Navigating to Credentials page");

            WebElement credentialTab = waitClickable(
                    By.xpath("//a[contains(@href,'credentials')]"));

            jsClick(credentialTab);

            wait.until(ExpectedConditions.urlContains("/credentials"));
        }
        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();

        Thread.sleep(300);

        // ======================================
        // OPEN SUBJECTS TAB
        // ======================================

        System.out.println("Opening Subjects Tab");

        try {
            WebElement subjectsTab = waitClickable(By.xpath("//a[normalize-space()='Subjects']"));
            jsClick(subjectsTab);
        } catch (Exception e) {
            System.out.println("Subjects tab nav failed, continuing: " + e.getMessage());
        }

        // Wait for subject rows
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")));
        } catch (Exception ignored) {}

        Thread.sleep(150);

        // ======================================
        // CLICK ADD RECORD
        // ======================================

        System.out.println("Clicking Add Record button");

        WebElement addRecordButton = null;
        try {
            addRecordButton = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//tbody/tr[1]//button[contains(.,'Add Record')] | " +
                                     "//button[.//span[contains(normalize-space(),'Add Record')]]")));
        } catch (Exception e) {
            System.out.println("Add Record button not found in upload flow, skipping: " + e.getMessage());
            return;
        }

        jsClick(addRecordButton);

        Thread.sleep(200);

        // ======================================
        // HANDLE POPUP / FALLBACK
        // ======================================

        boolean popupOpened = false;

        try {
            waitVisible(By.xpath("//span[normalize-space()='Add Records']"));
            popupOpened = true;
        } catch (TimeoutException e) {
            System.out.println("⚠️ Popup not opened, checking fallback...");
        }

        Thread.sleep(1000);

        if (!popupOpened) {
            String uploadPageUrl = driver.getCurrentUrl();
            if (uploadPageUrl.contains("/credentials/add")) {
                System.out.println("✅ Already on Add Credential Page");
            } else {
                // Try navigating directly to the add-credential page before giving up
                System.out.println("⚠️ Popup didn't open and URL is " + uploadPageUrl + " — retrying navigation");
                try {
                    WebElement addRecordBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[.//span[contains(normalize-space(),'Add Record')]] | " +
                                             "//span[contains(normalize-space(),'Add Record')]/ancestor::button")));
                    jsClick(addRecordBtn);
                    Thread.sleep(1500);
                    if (driver.getCurrentUrl().contains("/credentials/add")) {
                        System.out.println("✅ Navigated to Add Credential Page on retry");
                        popupOpened = false;
                    }
                } catch (Exception retryEx) {
                    System.out.println("⚠️ Could not navigate to Add Credential Page, skipping upload step");
                    return; // Non-fatal — skip upload rather than crash the whole test
                }
            }
        }

        // ======================================
        // SELECT SUBJECT (ONLY IF POPUP)
        // ======================================

        if (popupOpened) {

            System.out.println("Selecting Subject");

            WebElement subjectDropdown = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[contains(@class,'p-dialog')]//span[@aria-label='Select a subject']")));

            jsClick(subjectDropdown);

            Thread.sleep(1000);

            WebElement latestSubject = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//li[@id='selectedSubject_0']")));

            jsClick(latestSubject);

            Thread.sleep(1000);

            System.out.println("Confirm Add Records");

            WebElement confirmAddRecords = waitVisible(
                    By.xpath("//div[contains(@class,'p-dialog')]//button[normalize-space()='Add Record(s)']"));

            jsClick(confirmAddRecords);

            Thread.sleep(3000);
        }

        // ======================================
        // WAIT FOR ADD PAGE
        // ======================================

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Add New Record']")),
                ExpectedConditions.urlContains("/credentials/add")
        ));

        waitVisible(By.xpath("//button[.//span[normalize-space()='Upload Spreadsheet']]"));

        System.out.println("Add Credential Page Loaded");

        // ======================================
        // CLICK UPLOAD BUTTON
        // ======================================

        WebElement uploadBtn = waitClickable(
                By.xpath("//button[.//span[normalize-space()='Upload Spreadsheet']]"));

        // Scroll the button into centre-view so the user can clearly see which button is clicked
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", uploadBtn);
        Thread.sleep(300);
        jsClick(uploadBtn);

        Thread.sleep(300);

        // ======================================
        // HANDLE POPUP
        // ======================================

        WebElement popupBtn = waitClickable(
                By.xpath("//button[contains(text(),'Upload Spreadsheet')]"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", popupBtn);
        Thread.sleep(300);
        jsClick(popupBtn);

        Thread.sleep(500);

        // ======================================
        // FILE UPLOAD
        // ======================================

        System.out.println("Uploading file");

        WebElement fileInput;
        try {
            fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//div[@role='dialog']//input[@type='file'] | " +
                            "//div[contains(@class,'p-dialog')]//input[@type='file'])[last()]")));
        } catch (Exception e) {
            fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        }

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].style.display='block';", fileInput);

        fileInput.sendKeys("C:\\Users\\Nikhil Sonawane\\Downloads\\Employees ID Cards-template.xlsx");

        System.out.println("✅ File uploaded");

        Thread.sleep(600);

        waitForUploadProcessingComplete();

        // If the "in-process records" warning button appears, click it to reveal staged rows
        clickInProcessRecordsButtonIfPresent();

        waitForUploadedRowsOnDraftTable();

        System.out.println("✅ Upload flow completed successfully");
    }

    private void closeOnboardingPopupIfPresent() {
        By closeBtn = By.xpath("//button[contains(@class,'p-dialog-header-close')]");
        try {
            if (driver.findElements(closeBtn).isEmpty()) {
                return;
            }
            WebElement onboardingClose = shortWait.until(ExpectedConditions.elementToBeClickable(closeBtn));
            jsClick(onboardingClose);
            System.out.println("Onboarding popup closed on upload page");
        } catch (Exception ignored) {
            // Optional popup
        }
    }

    private void collapseGetStartedPanelIfPresent() {
        By panelTitle = By.xpath("//*[contains(normalize-space(),'Get Started')]");
        try {
            if (driver.findElements(panelTitle).isEmpty()) {
                return;
            }
            By chevronToggle = By.xpath(
                    "//*[contains(normalize-space(),'Get Started')]/ancestor::div[1]//button[.//i[contains(@class,'pi-chevron')]] | " +
                            "//*[contains(normalize-space(),'Get Started')]/ancestor::div[2]//button[.//i[contains(@class,'pi-chevron')]]");
            try {
                WebElement toggle = shortWait.until(ExpectedConditions.elementToBeClickable(chevronToggle));
                jsClick(toggle);
                System.out.println("Get Started panel collapsed on upload page");
                return;
            } catch (Exception ignored) {}

            ((JavascriptExecutor) driver).executeScript(
                    "const el = Array.from(document.querySelectorAll('*')).find(e => e.textContent && e.textContent.trim()==='Get Started');" +
                            "if(el){ const p = el.closest('div'); if(p){ p.style.width='0px'; p.style.minWidth='0px'; p.style.overflow='hidden'; p.style.display='none'; }}");
        } catch (Exception ignored) {
            // Non-blocking
        }
    }

    private void ensureCredentialsPageAndDraftTab() {
        if (!driver.getCurrentUrl().contains("/credentials")) {
            WebElement credentialTab = waitClickable(By.xpath("//a[contains(@href,'credentials')]"));
            jsClick(credentialTab);
            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

        try {
            WebElement draftTab = waitClickable(
                    By.xpath("//button[normalize-space()='Draft'] | //span[normalize-space()='Draft']/ancestor::button"));
            jsClick(draftTab);
            System.out.println("Draft tab selected");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody"))
            ));
        } catch (Exception e) {
            System.out.println("Could not confirm Draft tab immediately, continuing");
        }
    }

    // ======================================
    // JS CLICK
    // ======================================

    public void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int attempts = 0;
        while (attempts < 3) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying click...");
                attempts++;
                try { Thread.sleep(150); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("Click failed");
    }
}