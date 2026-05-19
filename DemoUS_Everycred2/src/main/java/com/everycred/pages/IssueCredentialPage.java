package com.everycred.pages;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssueCredentialPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    public IssueCredentialPage(WebDriver driver) {

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
    // PAGE VALIDATION (EXISTING)
    // ======================================

    private void ensureOnCredentialsPage() {

        System.out.println("Ensuring we are on Credentials page");

        String url = driver.getCurrentUrl();

        // If we are on add/details pages like /admin/credentials/add/<id>,
        // go straight to Draft list (fast) instead of Subjects→Credentials→Issued wait
        if (url.contains("/admin/credentials/add") || !url.contains("/admin/credentials")) {
            try {
                if (url.contains("/admin/credentials/add")) {
                    jumpToCredentialsDraftList();
                } else {
                    navigateToCredentialListPage();
                }
                closeOnboardingPopupIfPresent();
                collapseGetStartedPanelIfPresent();
                return;
            } catch (Exception ignored) {
                // fall through to the existing tab-click navigation
            }
        }

        if (!url.contains("/credentials")) {

            System.out.println("Navigating to Credentials tab manually");

            WebElement credentialTab = waitClickable(
                    By.xpath("//a[normalize-space()='Credentials']"));

            jsClick(credentialTab);

            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();
        waitVisible(By.xpath("//body"));
    }


    // ======================================
    // 🔥 NEW METHOD (CRITICAL FIX)
    // ======================================

    private void navigateToCredentialListPage() {

        System.out.println("Forcing navigation → Subjects → Credentials");

        // Click Subjects
        WebElement subjectsTab = waitClickable(
                By.xpath("//a[normalize-space()='Subjects']"));

        jsClick(subjectsTab);

        waitVisible(By.xpath("//body"));
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}

        // Click Credentials
        WebElement credentialsTab = waitClickable(
                By.xpath("//a[normalize-space()='Credentials']"));

        jsClick(credentialsTab);

        wait.until(ExpectedConditions.urlContains("/credentials"));

        waitVisible(By.xpath("//body"));
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}

        System.out.println("Switching to Issued tab");

        try {
            WebElement issuedTab = waitClickable(
                    By.xpath("//button[normalize-space()='Issued']"));
            jsClick(issuedTab);
        } catch (Exception e) {
            System.out.println("Issued tab click failed: " + e.getMessage());
        }

        // wait for issued data or empty state — never throw on empty
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(),'No data') or contains(normalize-space(),'No records')]"))
            ));
        } catch (Exception ignored) {}

        System.out.println("Now on Issued Credentials page");
    }

    // ======================================
    // ISSUE CREDENTIAL FLOW
    // ======================================

    public void issueCredentialFlow() throws InterruptedException {

        System.out.println("Starting Issue Credential Flow");
        ensureCredentialsAndDraftTab();

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL = " + currentUrl);

        // =============================
        // WAIT FOR DRAFT TABLE
        // =============================

        System.out.println("Waiting for Draft Credential table");
        waitForTableOrEmpty();

        Thread.sleep(800); // extra buffer for table rows to stabilise

        // =============================
        // SELECT CHECKBOX
        // =============================

        System.out.println("Selecting record checkbox");
        clickFirstRecordCheckbox();

        Thread.sleep(150);

        // =============================
        // CLICK ISSUE BUTTON
        // =============================

        clickIssueCredentialsButton();
        // No extra sleep — the popup wait below handles synchronisation

        // =============================
        // POPUP OPEN WAIT
        // =============================

        System.out.println("Waiting for Issue Credentials popup...");

        // Wait max 8s for the popup — it opens almost immediately after clicking Issue Credentials
        By issueDialogLocator = By.xpath(
                "//app-issue-credentials-dialog | " +
                "//div[contains(@class,'p-dialog')][.//*[normalize-space()='Issue Credentials']]");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8)).until(
                    ExpectedConditions.visibilityOfElementLocated(issueDialogLocator));
        } catch (Exception e) {
            // Fallback: wait for any visible p-dialog
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        }

        // Wait 2 seconds after popup opens so the form fields fully render before interaction
        Thread.sleep(2000);

        selectSendViaEmailNoIfPresent();

        System.out.println("Issue popup open. Selecting Valid From date...");

        // =============================
        // CLICK VALID FROM DATE PICKER
        // =============================

        // Poll ALL candidates simultaneously every 200ms until one is visible+enabled.
        // This is far faster than trying each with a 1s individual timeout.
        final By[] validFromCandidates = {
            By.xpath("(//app-issue-credentials-dialog//p-calendar)[1]//input"),
            By.xpath("(//app-issue-credentials-dialog//input[@type='text'])[1]"),
            By.xpath("//input[@placeholder='Valid From']"),
            By.xpath("(//div[@role='dialog']//p-calendar)[1]//input"),
            By.xpath("(//div[@role='dialog']//input[@type='text'])[1]"),
            By.xpath("(//div[contains(@class,'p-dialog')]//input[@type='text'])[1]"),
        };
        WebElement validFrom = null;
        try {
            validFrom = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(200))
                .until(d -> {
                    for (By loc : validFromCandidates) {
                        try {
                            java.util.List<WebElement> els = d.findElements(loc);
                            if (!els.isEmpty() && els.get(0).isDisplayed() && els.get(0).isEnabled()) {
                                return els.get(0);
                            }
                        } catch (Exception ignored) {}
                    }
                    return null;
                });
        } catch (Exception e) {
            // Broadest fallback — any input inside the dialog component
            try {
                validFrom = waitClickable(By.xpath(
                    "//app-issue-credentials-dialog//input | //div[@role='dialog']//input"));
            } catch (Exception ignored) {}
        }

        scrollIntoView(validFrom);
        Thread.sleep(80);

        // =============================
        // SELECT CURRENT DATE
        // =============================

        System.out.println("Selecting current date");

        ZonedDateTime istDate = ZonedDateTime.now(java.time.ZoneId.of("Asia/Kolkata"));
        String todayStr = istDate.format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        int todayDay = istDate.getDayOfMonth();

        boolean dateConfirmed = false;

        // Single click opens the calendar — then click today's cell.
        // No keyboard fast-path before this: that caused the calendar to open twice.
        try {
            validFrom.click();
            Thread.sleep(200); // Let calendar animation complete

            By datepickerPanel = By.xpath("//div[contains(@class,'p-datepicker')]");
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(datepickerPanel));

            By todayCellLocator = By.xpath(
                    "//div[contains(@class,'p-datepicker')]" +
                    "//td[not(contains(@class,'p-datepicker-other-month'))]" +
                    "//span[not(contains(@class,'p-disabled')) and normalize-space(text())='" + todayDay + "']");
            WebElement todayCell = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(todayCellLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", todayCell);

            Thread.sleep(200);
            String val = validFrom.getAttribute("value");
            if (val != null && !val.trim().isEmpty()) {
                System.out.println("Valid From date selected via calendar: " + val);
                dateConfirmed = true;
            }
        } catch (Exception e) {
            System.out.println("Calendar click failed, trying keyboard: " + e.getMessage());
            // Keyboard fallback — only reached if the calendar did not open or today's cell was missing
            try {
                validFrom.sendKeys(Keys.chord(Keys.CONTROL, "a"), todayStr, Keys.TAB);
                Thread.sleep(150);
                String val = validFrom.getAttribute("value");
                if (val != null && !val.trim().isEmpty()) {
                    System.out.println("Valid From date set via keyboard fallback: " + val);
                    dateConfirmed = true;
                }
            } catch (Exception ignored) {}
        }

        if (!dateConfirmed) {
            System.out.println("Warning: could not confirm Valid From date, continuing anyway");
        }

        System.out.println("Validity date selection " + (dateConfirmed ? "confirmed" : "attempted") + ": " + todayStr);

        // Pause so the user can see the selected date before the Issue button is clicked
        Thread.sleep(1500);

        // =============================
        // CLICK ISSUE BUTTON IN POPUP
        // =============================

        System.out.println("Click Issue button in popup");

        // Broad locator for the Issue/Submit button inside the popup
        By issueButtonLocator = By.xpath(
                "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Issue'] or normalize-space()='Issue'] | " +
                "//app-issue-credentials-dialog//button[.//span[normalize-space()='Issue'] or normalize-space()='Issue']");

        WebElement issuePopup = waitClickable(issueButtonLocator);
        scrollIntoView(issuePopup);
        try { Thread.sleep(80); } catch (InterruptedException ignored) {}
        try {
            issuePopup.click();
        } catch (Exception e) {
            jsClick(issuePopup);
        }
        try { Thread.sleep(80); } catch (InterruptedException ignored) {}

        System.out.println("Waiting popup to close");

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//app-issue-credentials-dialog")));
        } catch (Exception e) {
            // Fallback: wait for dialog-level indicator
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-dialog')][.//*[contains(normalize-space(),'Issue Credentials')]]")));
            } catch (Exception ignored) {}
        }

        // Short settle time after popup closes — no full page refresh needed
        Thread.sleep(500);
        closeDashboardFlowCompletePopupIfPresent();

        // =============================
        // SWITCH TO ISSUED TAB DIRECTLY
        // =============================

        System.out.println("Switching to Issued tab to show issued credential");

        try {
            // We are still on the credentials page — just click the Issued tab directly.
            // This is much faster than navigating Subjects → Credentials → Issued.
            WebElement issuedTab = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Issued']")));
            jsClick(issuedTab);
        } catch (Exception e) {
            // Fallback: full navigation if Issued tab not immediately available
            navigateToCredentialListPage();
        }

        // Wait up to ~4s for the issued record to appear (avoid long idle on empty Issued view)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        } catch (Exception ignored) {}

        Thread.sleep(350);

        System.out.println("Credential issued and page stabilized");
    }

    private void selectSendViaEmailNoIfPresent() {
        try {
            // Use a direct JS call — no Selenium "clickable" wait needed, completes instantly
            Boolean clicked = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var el = document.getElementById('emailNo');" +
                "if (el && !el.checked) { el.click(); return true; } return false;");
            if (Boolean.TRUE.equals(clicked)) {
                System.out.println("Selected 'No' for Send credentials via email");
            }
        } catch (Exception ignored) {
            // Optional control; continue issuing even when not present.
        }
    }

    private void closeDashboardFlowCompletePopupIfPresent() {
        By flowCompleteDialog = By.xpath(
                "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'dashboard flow complete')]");
        By continueBtn = By.xpath(
                "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Continue'] or normalize-space()='Continue' or " +
                        ".//span[normalize-space()='Dashboard'] or normalize-space()='Dashboard' or " +
                        ".//span[normalize-space()='Close'] or normalize-space()='Close']");
        try {
            if (driver.findElements(flowCompleteDialog).isEmpty()) {
                return;
            }
            WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(continueBtn));
            jsClick(btn);
            System.out.println("Dashboard flow complete popup handled");
        } catch (Exception ignored) {
            // Non-blocking popup
        }
    }

    private void clickFirstRecordCheckbox() {
        collapseGetStartedPanelIfPresent();
        By checkboxInput = By.xpath("(//tbody/tr[1]//input[contains(@class,'p-checkbox-input')])[1]");
        By checkboxBox = By.xpath("(//tbody/tr[1]//div[contains(@class,'p-checkbox-box')])[1]");
        By selectedCheckboxBox = By.xpath("(//tbody/tr[1]//div[contains(@class,'p-checkbox-box') and contains(@class,'p-highlight')])[1]");
        By selectedCheckboxInput = By.xpath("(//tbody/tr[1]//input[contains(@class,'p-checkbox-input') and (@checked or @aria-checked='true')])[1]");
        By selectedRecordsText = By.xpath("//*[contains(normalize-space(),'Selected Records')]");

        Exception lastError = null;
        for (int attempt = 1; attempt <= 8; attempt++) {
            // Check selection state BEFORE clicking to avoid toggling off
            try {
                if (!driver.findElements(selectedCheckboxBox).isEmpty()
                        || !driver.findElements(selectedCheckboxInput).isEmpty()
                        || !driver.findElements(selectedRecordsText).isEmpty()) {
                    System.out.println("Record checkbox selected");
                    return;
                }
            } catch (Exception ignored) {}

            // Click input only; do NOT also click the box — clicking both toggles on then off
            boolean clicked = false;
            try {
                WebElement input = shortWait.until(ExpectedConditions.presenceOfElementLocated(checkboxInput));
                scrollIntoView(input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
                clicked = true;
            } catch (Exception e) {
                lastError = e;
            }

            // Fallback to visible box only when input click was not possible
            if (!clicked) {
                try {
                    WebElement box = shortWait.until(ExpectedConditions.elementToBeClickable(checkboxBox));
                    scrollIntoView(box);
                    jsClick(box);
                } catch (Exception e) {
                    lastError = e;
                }
            }

            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }

        throw new RuntimeException("Checkbox not found/clickable in Issue Credential table", lastError);
    }

    private void closeOnboardingPopupIfPresent() {
        By closeBtn = By.xpath("//button[contains(@class,'p-dialog-header-close')]");
        try {
            if (driver.findElements(closeBtn).isEmpty()) {
                return;
            }
            WebElement onboardingClose = shortWait.until(ExpectedConditions.elementToBeClickable(closeBtn));
            jsClick(onboardingClose);
            System.out.println("Onboarding popup closed on credentials page");
        } catch (Exception ignored) {
            // Non-blocking: onboarding may not be present for existing users.
        }
    }

    private void collapseGetStartedPanelIfPresent() {
        By panelTitle = By.xpath("//*[contains(normalize-space(),'Get Started')]");
        try {
            if (driver.findElements(panelTitle).isEmpty()) {
                return;
            }

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

            try {
                WebElement root = driver.findElement(panelRoot);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'nearest'});", root);
                WebElement toggle = shortWait.until(ExpectedConditions.elementToBeClickable(chevronToggle));
                jsClick(toggle);
                System.out.println("Get Started panel collapsed (after scroll)");
                return;
            } catch (Exception ignored) {}

            ((JavascriptExecutor) driver).executeScript(
                    "const el = Array.from(document.querySelectorAll('*')).find(e => e.textContent && e.textContent.trim()==='Get Started');" +
                    "if(el){ const p = el.closest('div'); if(p){ p.style.width='0px'; p.style.minWidth='0px'; p.style.overflow='hidden'; p.style.display='none'; }}");
            System.out.println("Get Started panel hidden via JS");
        } catch (Exception ignored) {
            // Non-blocking
        }
    }

    private void clickIssueCredentialsButton() {
        By issueButton = By.xpath(
                "//button[.//span[normalize-space()='Issue Credentials']] | " +
                "//span[normalize-space()='Issue Credentials']/ancestor::button");
        for (int attempt = 1; attempt <= 10; attempt++) {
            try {
                WebElement issueBtn = waitClickable(issueButton);
                scrollIntoView(issueBtn);
                try { Thread.sleep(80); } catch (InterruptedException ignored) {}
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", issueBtn);
                System.out.println("Issue Credentials button clicked (attempt " + attempt + ")");
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element on Issue Credentials button, retrying (attempt " + attempt + ")");
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            } catch (Exception e) {
                System.out.println("Issue button error on attempt " + attempt + ": " + e.getMessage());
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("Unable to click Issue Credentials button after 10 attempts");
    }

    private void ensureCredentialsAndDraftTab() {
        ensureOnCredentialsPage();
        try {
            if (driver.getCurrentUrl().contains("/admin/credentials/add")) {
                // Fast path: go straight to Draft list — avoids Subjects→Credentials→Issued wait (~15–20s)
                jumpToCredentialsDraftList();
            } else {
                WebElement draftTab = shortWait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Draft'] | //a[normalize-space()='Draft'] | " +
                                "//span[normalize-space()='Draft']/ancestor::button")));
                jsClick(draftTab);
            }
            // Short wait only (3–4s target) — table or draft URL is enough to proceed
            new WebDriverWait(driver, Duration.ofSeconds(4)).until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("status=draft"),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(),'No data') or contains(normalize-space(),'No records') " +
                            "or contains(normalize-space(),'credentials')]"))
            ));
            System.out.println("Draft tab selected");
        } catch (Exception e) {
            System.out.println("Draft tab click failed, continuing: " + e.getMessage());
        }
    }

    /** Direct navigation to credentials list on Draft tab (no Issued-tab detour). */
    private void jumpToCredentialsDraftList() {
        String cur = driver.getCurrentUrl();
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
        System.out.println("Jumped to Draft credentials list: " + driver.getCurrentUrl());
    }

    private void waitForTableOrEmpty() {
        try {
            // Wait for actual table rows — NOT just an empty tbody (which would cause clickFirstRecordCheckbox to fail)
            new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(normalize-space(),'No data') or " +
                                     "contains(normalize-space(),'No records') or " +
                                     "contains(normalize-space(),'empty')]"))
            ));
            System.out.println("Credential table rows are visible");
        } catch (Exception e) {
            System.out.println("Table rows not visible within 40s — continuing: " + e.getMessage());
        }
    }

    private void ensureCredentialsAndIssuedTab() {
        ensureOnCredentialsPage();
        try {
            if (driver.getCurrentUrl().contains("/admin/credentials/add")) {
                navigateToCredentialListPage();
            }
            WebElement issuedTab = waitClickable(By.xpath("//button[normalize-space()='Issued']"));
            jsClick(issuedTab);
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody"))
            ));
            System.out.println("Issued tab selected");
        } catch (Exception e) {
            System.out.println("Issued tab not confirmed immediately, continuing");
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
                scrollIntoView(element);
                try { Thread.sleep(80); } catch (InterruptedException ignored) {}
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying click due to stale element...");
                attempts++;
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("Failed to click element after retries");
    }
}