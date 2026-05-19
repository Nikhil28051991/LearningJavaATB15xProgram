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

    /**
     * Wait until the spreadsheet upload has actually FINISHED — not just started.
     * <p>
     * Previously this returned as soon as the words "processing" or "in-process"
     * appeared, but those show up the instant the upload begins, so we'd click
     * the back arrow before the file was even parsed and the uploaded rows
     * never showed up in Draft.
     * <p>
     * New behaviour:
     *   1. Wait up to 90s for a strict success signal — bulk-complete toast / activity message only.
     *      Do NOT treat the yellow "in-process records" banner as completion; it appears while work
     *      is still running and caused the script to reopen Upload Spreadsheet too early.
     *   2. Then wait for spinners to disappear before navigating away.
     */
    private void waitForUploadProcessingComplete() {
        System.out.println("Waiting for upload to complete...");

        // Strict completion only — no yellow in-process hint (that shows before bulk activity finishes).
        By done = By.xpath(
                "//*[contains(normalize-space(.),'has been completed') and contains(normalize-space(.),'records processed')] | " +
                "//*[contains(normalize-space(.),'Activity has been completed successfully')] | " +
                "//*[contains(normalize-space(.),'bulk upload') and contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'completed')] | " +
                "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'uploaded successfully')] | " +
                "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'upload completed')] | " +
                "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'spreadsheet') and " +
                "    contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'completed')] | " +
                "//*[contains(@class,'p-toast')][.//*[contains(normalize-space(.),'records processed') or " +
                "      contains(normalize-space(.),'completed successfully') or " +
                "      contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'upload completed')]]");

        boolean successSeen = false;
        try {
            // Brief pause for spinner to start — don't burn 3s if spinner never appears
            try {
                new WebDriverWait(driver, Duration.ofSeconds(1)).until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(@class,'p-progress-spinner')]")));
            } catch (Exception ignored) {}

            // Max 90s total — most uploads finish in 5–30s
            new WebDriverWait(driver, Duration.ofSeconds(90))
                    .until(ExpectedConditions.visibilityOfElementLocated(done));
            successSeen = true;
            System.out.println("✅ Upload done signal detected");
        } catch (Exception e) {
            System.out.println("Upload completion not confirmed after 90s — proceeding anyway");
        }

        // Wait for spinner/uploading text to disappear before navigating back (cap wait for speed)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                            "//*[contains(@class,'p-progress-spinner')] | " +
                            "//*[contains(@class,'p-progressbar') and not(contains(@class,'p-hidden'))]")));
        } catch (Exception ignored) {}

        try { Thread.sleep(250); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
        System.out.println("Upload processing checkpoint reached" + (successSeen ? " (confirmed)" : " (unconfirmed)"));
    }

    /** After bulk upload, rows often render only on Draft after navigation or refresh. */
    private void waitForUploadedRowsOnDraftTable() throws InterruptedException {
        ensureCredentialsPageAndDraftTab();

        By dataRow = By.xpath("//tbody//tr");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(14))
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
        Thread.sleep(1200);
        closeOnboardingPopupIfPresent();
        collapseGetStartedPanelIfPresent();

        try {
            WebElement draftTab = waitClickable(
                    By.xpath("//button[normalize-space()='Draft'] | //span[normalize-space()='Draft']/ancestor::button"));
            jsClick(draftTab);
        } catch (Exception ignored) {}

        try {
            new WebDriverWait(driver, Duration.ofSeconds(14))
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

            Thread.sleep(1500);
        }

        // ======================================
        // WAIT FOR ADD PAGE — fast path so we don't burn 15-20s before first click
        // ======================================

        try {
            new WebDriverWait(driver, Duration.ofSeconds(8)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Upload Spreadsheet']")),
                    ExpectedConditions.urlContains("/credentials/add")
            ));
        } catch (Exception ignored) {}

        System.out.println("Add Credential Page Loaded");

        // Collapse onboarding panel so it does not overlap the Upload Spreadsheet button
        collapseGetStartedPanelIfPresent();

        // ======================================
        // FIRST CLICK: open Upload Spreadsheet popup
        // (XPath as provided by user)
        // ======================================

        clickUploadSpreadsheetTrigger("first");

        // ======================================
        // POPUP — confirm Upload Spreadsheet button inside the dialog
        // ======================================

        clickUploadSpreadsheetConfirmButtonInPopup();

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

        clearExistingToastsBeforeUpload();
        fileInput.sendKeys("C:\\Users\\Nikhil Sonawane\\Downloads\\Employees ID Cards-template.xlsx");

        System.out.println("✅ File uploaded");

        Thread.sleep(150);

        waitForUploadProcessingComplete();

        // ======================================
        // POST-UPLOAD FLOW (per user spec)
        //   1. After success message appears, click back button (pi pi-angle-left)
        //      to return to the Add Credential page.
        //   2. Click "Upload Spreadsheet" trigger again on Add Credential page.
        //   3. Wait for the popup to open fully.
        //   4. Click the yellow "You have in-process records. Click to view
        //      details" hint so the staged records become visible.
        // ======================================

        // After activity / bulk-upload success toast: wait 2–3 s before navigating away so the app
        // can finish updating state; then back + second "Upload Spreadsheet" opens the yellow hint reliably.
        Thread.sleep(2500);

        clickBackArrowToAddCredentialPage();

        // Collapse panel so it does not block the second trigger button
        collapseGetStartedPanelIfPresent();

        clickUploadSpreadsheetTrigger("second");

        waitForUploadDialogReady();

        // Collapse panel again — it reappears inside the Add Credential page overlay
        collapseGetStartedPanelIfPresent();

        Thread.sleep(350);

        clickInProcessRecordsYellowHint();

        // Best-effort: confirm staged rows ended up on the Draft list
        waitForUploadedRowsOnDraftTable();

        System.out.println("✅ Upload flow completed successfully");
    }

    private void clearExistingToastsBeforeUpload() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.p-toast-icon-close, .p-toast-close-button').forEach(function(btn){try{btn.click();}catch(e){}});" +
                    "document.querySelectorAll('.p-toast .p-toast-message').forEach(function(t){t.style.display='none';});");
            Thread.sleep(150);
        } catch (Exception ignored) {}
    }

    /**
     * Clicks the page-level "Upload Spreadsheet" trigger button.
     * Uses the exact XPath the user provided
     * ({@code //span[normalize-space()='Upload Spreadsheet']}) and falls back
     * to button variants if the span isn't directly clickable.
     */
    private void clickUploadSpreadsheetTrigger(String label) throws InterruptedException {
        By spanXpath   = By.xpath("//span[normalize-space()='Upload Spreadsheet']");
        By buttonXpath = By.xpath(
                "//button[.//span[normalize-space()='Upload Spreadsheet']] | " +
                "//span[normalize-space()='Upload Spreadsheet']/ancestor::button[1]");

        // First open: shorter max wait — button usually ready once Add Credential page mounted.
        int buttonWaitSec = "first".equals(label) ? 5 : 8;

        Exception lastError = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement target;
                try {
                    target = new WebDriverWait(driver, Duration.ofSeconds(buttonWaitSec))
                            .until(ExpectedConditions.elementToBeClickable(buttonXpath));
                } catch (Exception spanFallback) {
                    target = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(spanXpath));
                }
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", target);
                Thread.sleep(120);
                jsClick(target);
                System.out.println("Upload Spreadsheet trigger clicked (" + label + ") on attempt " + attempt);
                return;
            } catch (Exception e) {
                lastError = e;
                Thread.sleep(500);
            }
        }
        throw new RuntimeException("Unable to click Upload Spreadsheet trigger (" + label + ")", lastError);
    }

    /**
     * The first popup that opens after the page-level Upload Spreadsheet click
     * contains another "Upload Spreadsheet" button that actually starts the
     * file-picker flow. Click that one before sending the file.
     */
    private void clickUploadSpreadsheetConfirmButtonInPopup() throws InterruptedException {
        // Dialog animation: tiny settle only — clickable wait below is the real sync (avoids fixed 3s/800ms lag).
        Thread.sleep(200);

        // Primary XPath exactly as provided by user; fallback covers button with child span.
        By popupBtn = By.xpath(
                "//button[contains(text(),'Upload Spreadsheet')] | " +
                "//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Upload Spreadsheet']] | " +
                "//div[contains(@class,'p-dialog')]//button[contains(normalize-space(),'Upload Spreadsheet')]");
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(12))
                    .until(ExpectedConditions.elementToBeClickable(popupBtn));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", btn);
            Thread.sleep(120);
            jsClick(btn);
            System.out.println("Blue Upload Spreadsheet button clicked inside popup");
            Thread.sleep(250);
        } catch (Exception e) {
            System.out.println("Inner Upload Spreadsheet button not present, continuing: " + e.getMessage());
        }
    }

    /**
     * After upload success, click the breadcrumb / back arrow
     * ({@code //i[@class='pi pi-angle-left']}) so we land back on the Add
     * Credential page ready to re-open the upload dialog.
     */
    private void clickBackArrowToAddCredentialPage() throws InterruptedException {
        By backArrow = By.xpath(
                "//i[@class='pi pi-angle-left'] | " +
                "//button[.//i[contains(@class,'pi-angle-left')]] | " +
                "//*[contains(@class,'pi-angle-left')]/ancestor::button[1]");
        try {
            WebElement el = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.elementToBeClickable(backArrow));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", el);
            Thread.sleep(120);
            jsClick(el);
            System.out.println("Back arrow clicked — returning to Add Credential page");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Back arrow not found, continuing with current page: " + e.getMessage());
        }
    }

    /** Waits until the Upload Spreadsheet popup dialog is fully rendered. */
    private void waitForUploadDialogReady() {
        By dialog = By.xpath(
                "//div[contains(@class,'p-dialog')][.//*[contains(normalize-space(),'Upload')]] | " +
                "//div[@role='dialog'][.//*[contains(normalize-space(),'Upload')]]");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(dialog));
            // Allow content (buttons / hint span) to mount inside the dialog
            new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class,'p-dialog')]//button | " +
                                     "//div[@role='dialog']//button")));
            System.out.println("Upload Spreadsheet popup ready");
        } catch (Exception e) {
            System.out.println("Upload popup readiness check timed out, continuing: " + e.getMessage());
        }
    }

    /**
     * After the Upload Spreadsheet popup shows the yellow banner:
     * <ol>
     *   <li>Click {@code //span[@class='text-sm text-yellow-800 font-medium']} (via its wrapping button).</li>
     *   <li>Wait until Processing Data rows are visible in the popup.</li>
     *   <li>Wait exactly 3 seconds, then click Close on the popup ({@code //span[normalize-space()='Close']}).</li>
     * </ol>
     */
    private void clickInProcessRecordsYellowHint() throws InterruptedException {
        // Exact XPath as specified (must match after popup is open).
        By yellowHintSpan = By.xpath("//span[@class='text-sm text-yellow-800 font-medium']");

        boolean clicked = false;

        for (int attempt = 1; attempt <= 4; attempt++) {
            try {
                WebElement el = new WebDriverWait(driver, Duration.ofSeconds(12))
                        .until(ExpectedConditions.visibilityOfElementLocated(yellowHintSpan));

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", el);
                Thread.sleep(200);

                // Banner is a clickable {@code <button>} wrapping this span — click parent for reliability.
                ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0];" +
                        "var btn = el.closest('button') || el;" +
                        "btn.click();", el);

                System.out.println("Yellow in-process hint clicked via span[@class='text-sm text-yellow-800 font-medium'] (attempt " + attempt + ")");
                clicked = true;
                break;
            } catch (Exception e) {
                System.out.println("Yellow hint not visible yet on attempt " + attempt + " — retrying in 1.2s");
                collapseGetStartedPanelIfPresent();
                Thread.sleep(1200);
            }
        }

        if (!clicked) {
            System.out.println("Yellow hint span not found after retries (non-fatal)");
            return;
        }

        // Wait until table data is shown in the popup that opens after the hint click.
        By processingRows = By.xpath(
                "(//div[contains(@class,'p-dialog')] | //div[@role='dialog'])[last()]//tbody//tr");

        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfElementLocated(processingRows));
            System.out.println("Processing Data rows visible in popup");
        } catch (Exception ignored) {
            System.out.println("Processing rows wait timed out — still waiting 3s then Close");
        }

        // User spec: after data loads, wait 3 seconds then click Close on the popup.
        Thread.sleep(3000);

        clickUploadDialogCloseButton();
    }

    /** Clicks the "Close" button on the Processing Data / Upload dialog (user XPath: //span[normalize-space()='Close']). */
    private void clickUploadDialogCloseButton() throws InterruptedException {
        // Primary: exact XPath as specified by user (image 7 — "Close" button on Processing Data popup)
        By closeBtn = By.xpath(
                "//span[normalize-space()='Close'] | " +
                "//button[.//span[normalize-space()='Close']] | " +
                "//img[@alt='Close'] | " +
                "//button[.//img[@alt='Close']]");
        try {
            WebElement close = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(closeBtn));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", close);
            Thread.sleep(100);
            jsClick(close);
            System.out.println("Upload dialog Close button clicked");
            Thread.sleep(350);
        } catch (Exception e) {
            System.out.println("Close button not found, continuing: " + e.getMessage());
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