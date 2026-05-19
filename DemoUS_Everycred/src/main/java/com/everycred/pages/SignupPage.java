package com.everycred.pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.*;

public class SignupPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    // ======================================
    // SIGN-UP FORM VALUES
    // ======================================

    // Unique 5-digit suffix from epoch millis — changes on every test run
    private static final String SUFFIX           = String.valueOf(System.currentTimeMillis() % 100000);
    private static final String NAME             = "Demo Credentials " + SUFFIX;
    private static final String EMAIL            = "democredtesting" + SUFFIX + "@yopmail.com";
    private static final String PASSWORD         = "Everycred@123";
    private static final String YOPMAIL_URL      = "https://yopmail.com/en/";
    private static final String YOPMAIL_USERNAME = "democredtesting" + SUFFIX;

    // ======================================
    // LOCATORS — SIGN UP FORM
    // ======================================

    private final By signUpLinkLocator        = By.xpath("//span[normalize-space()='Sign Up']");
    private final By nameInputLocator         = By.xpath("//input[@id='name']");
    private final By emailInputLocator        = By.xpath("//input[@id='email']");
    // Password fields are PrimeNG p-password components; target the inner input by class
    private final By passwordInputLocator     = By.xpath("(//input[contains(@class,'p-password-input')])[1]");
    private final By confirmPwdInputLocator   = By.xpath("(//input[contains(@class,'p-password-input')])[2]");
    private final By acceptTermsLocator       = By.xpath("//input[@id='acceptTerms']");
    private final By submitButtonLocator      = By.xpath("//button[@type='submit']");

    // ======================================
    // LOCATORS — YOPMAIL INBOX
    // ======================================

    private final By yopmailLoginInput = By.xpath("//input[@id='login']");

    // Go / forward arrow button on YOPmail
    private final By yopmailGoButton   = By.xpath(
            "//a[@id='go'] | //button[@id='go'] | " +
            "//*[contains(@class,'material-icons') and normalize-space()='forward'] | " +
            "//*[@onclick and contains(@class,'go')]");

    // Target the specific "Issuer Email Verification" email — user-provided exact XPath
    private final By verificationEmailRow = By.xpath(
            "//div[normalize-space()='EveryCred | Issuer Email Verification']");

    // YOPmail wraps the inbox list in a separate iframe
    private final By inboxFrame        = By.xpath("//iframe[@id='ifinbox']");
    // iframe that holds the rendered email body
    private final By emailBodyFrame    = By.xpath("//iframe[@id='ifmail']");
    // "Verify Email" button exact text match inside the email body
    private final By verifyEmailBtn    = By.xpath(
            "//a[normalize-space()='Verify Email'] | " +
            "//a[contains(normalize-space(),'Verify Email')] | " +
            "//button[normalize-space()='Verify Email']");

    // ======================================
    // CONSTRUCTOR
    // ======================================

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait      = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Static getters so CreateSubjectTest can pass credentials to LoginPage
    public static String getEmail()    { return EMAIL; }
    public static String getPassword() { return PASSWORD; }

    // ======================================
    // MAIN FLOW — called from test class
    // ======================================

    public void signupFlow() throws InterruptedException {
        System.out.println("===== STARTING SIGN UP FLOW =====");

        clickSignUpLink();
        fillSignUpForm();
        submitSignUpForm();
        verifyEmailViaYopmail();

        System.out.println("===== SIGN UP FLOW COMPLETED =====");
    }

    // ======================================
    // STEP 1 — CLICK SIGN UP LINK
    // ======================================

    private void clickSignUpLink() throws InterruptedException {
        System.out.println("Waiting 2 seconds for login page to fully render...");
        Thread.sleep(2000);

        System.out.println("Clicking Sign Up link on Login page");

        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(signUpLinkLocator));
        jsClick(link);

        // Wait for the Sign Up form to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInputLocator));
        Thread.sleep(300);
        System.out.println("Sign Up page loaded");
    }

    // ======================================
    // STEP 2 — FILL SIGN UP FORM
    // ======================================

    private void fillSignUpForm() throws InterruptedException {
        System.out.println("Filling Sign Up form");

        enterText(nameInputLocator, NAME);
        Thread.sleep(100);

        enterText(emailInputLocator, EMAIL);
        Thread.sleep(100);

        // Password fields are inside PrimeNG p-password wrappers
        enterText(passwordInputLocator, PASSWORD);
        Thread.sleep(100);

        enterText(confirmPwdInputLocator, PASSWORD);
        Thread.sleep(100);

        // Accept Terms & Conditions checkbox
        WebElement terms = wait.until(ExpectedConditions.presenceOfElementLocated(acceptTermsLocator));
        if (!terms.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);
            System.out.println("Terms and Conditions accepted");
        }
        Thread.sleep(100);

        System.out.println("Sign Up form filled successfully");
    }

    // ======================================
    // STEP 3 — SUBMIT SIGN UP FORM
    // ======================================

    private void submitSignUpForm() throws InterruptedException {
        System.out.println("Submitting Sign Up form");

        // "email sent" success popup — scoped to toast/dialog/message containers.
        // Patterns cover all observed popup variants including "Confirm your email" dialog
        // (shown in screenshot: "We've sent a verification link…Resend in 01:00").
        By verifyingState = By.xpath(
                "//*[contains(@class,'p-toast') or contains(@class,'p-dialog') or " +
                "    contains(@class,'p-message') or contains(@class,'p-inline-message')]" +
                "[contains(normalize-space(),'Verifying Email') or " +
                " contains(normalize-space(),'Verification email sent') or " +
                " contains(normalize-space(),'email has been sent') or " +
                " contains(normalize-space(),'Check your email') or " +
                " contains(normalize-space(),'Confirm your email') or " +
                " contains(normalize-space(),'sent a verification link') or " +
                " contains(normalize-space(),'Verification Link expires') or " +
                " contains(normalize-space(),'Resend in')]");

        // Captcha errors — scope to ERROR-class elements only so the always-present
        // reCAPTCHA widget itself does not trigger a false positive.
        By captchaError = By.xpath(
                "//*[contains(@class,'p-toast-message-error') or contains(@class,'p-inline-message-error') or " +
                "    contains(@class,'p-error') or contains(@class,'alert-danger') or " +
                "    contains(@class,'error-message') or contains(@class,'ng-invalid')]" +
                "[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'captcha') or " +
                " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'recaptcha') or " +
                " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'robot') or " +
                " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invalid captcha') or " +
                " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'verify you are human')]");

        // Separate fallback: plain-text captcha error outside of error-class containers
        By captchaPlainText = By.xpath(
                "//*[self::p or self::span or self::div or self::small or self::li]" +
                "[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invalid recaptcha') or " +
                " contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invalid captcha')]");

        By otherError = By.xpath(
                "//*[contains(@class,'p-toast-message-error') or contains(@class,'p-inline-message-error')]" +
                "[not(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'captcha') or " +
                "     contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'recaptcha') or " +
                "     contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'robot'))] | " +
                "//*[contains(@class,'p-message-error')]" +
                "[contains(normalize-space(),'already') or contains(normalize-space(),'exists') " +
                " or contains(normalize-space(),'registered') or contains(normalize-space(),'taken')]");

        // Retry loop: click Sign Up → if captcha error appears, wait 6s, re-fill form, and click again
        // Keeps retrying until "Verifying Email..." popup appears (= email was sent by the server)
        // YOPmail is ONLY opened after this popup confirms the email was dispatched.
        boolean emailSent = false;
        int maxAttempts = 15; // 15 × ~8s poll + 6s captcha wait ≈ up to ~3.5 minutes

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("Sign Up click attempt " + attempt + "/" + maxAttempts);

            // Re-fill form fields on every retry in case captcha failure cleared them
            if (attempt > 1) {
                System.out.println("Re-filling form after captcha failure (attempt " + attempt + ")...");
                try {
                    refillFormAfterCaptcha();
                } catch (Exception e) {
                    System.out.println("Form re-fill warning: " + e.getMessage());
                }
            }

            // Click the submit button
            try {
                WebElement submit = new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
                jsClick(submit);
            } catch (Exception e) {
                System.out.println("Submit button not clickable on attempt " + attempt + ": " + e.getMessage());
                continue;
            }

            // Poll up to 10 seconds for a response after each click
            long pollDeadline = System.currentTimeMillis() + 10_000;
            boolean captchaDetected = false;
            while (System.currentTimeMillis() < pollDeadline) {

                // ✅ "Verifying Email..." appeared — server accepted the form, email dispatched
                if (!driver.findElements(verifyingState).isEmpty()) {
                    System.out.println("'Verifying Email...' popup appeared — email is being sent (attempt " + attempt + ")");
                    emailSent = true;
                    break;
                }

                // ⚠️ Captcha error — wait 10s (let reCAPTCHA reset), then re-fill and retry
                java.util.List<WebElement> capElems = driver.findElements(captchaError);
                if (capElems.isEmpty()) {
                    capElems = driver.findElements(captchaPlainText);
                }
                if (!capElems.isEmpty()) {
                    String capText = "";
                    try { capText = capElems.get(0).getText().trim(); } catch (Exception ignored) {}
                    System.out.println("Captcha/reCAPTCHA error detected: [" + capText + "] — waiting 6s then retrying...");
                    Thread.sleep(6000);
                    captchaDetected = true;
                    break;
                }

                // ❌ Other server error (duplicate email, validation, etc.)
                // Do NOT go to YOPmail — the success popup never appeared.
                java.util.List<WebElement> errElems = driver.findElements(otherError);
                if (!errElems.isEmpty()) {
                    String errText = "";
                    try { errText = errElems.get(0).getText().trim(); } catch (Exception ignored) {}
                    System.out.println("WARNING: Sign-up blocked by server error — " + errText);
                    System.out.println("Retrying after error (email notification popup did not appear)...");
                    Thread.sleep(2000);
                    break; // retry next attempt without setting emailSent
                }

                Thread.sleep(300);
            }

            if (emailSent) break;
            if (!captchaDetected) {
                // No captcha, no success, no error — timed out polling; try again
                System.out.println("No response detected within poll window — retrying submit...");
            }
        }

        // ── GATE: only open YOPmail if email was confirmed sent ──
        if (!emailSent) {
            throw new RuntimeException(
                "Sign Up FAILED: 'Verifying Email...' popup never appeared after " + maxAttempts +
                " attempts. Captcha may be blocking repeatedly. " +
                "YOPmail will NOT be opened. Please investigate.");
        }

        // Popup confirmed — wait 2 seconds for the server to finish dispatching the email,
        // then open YOPmail. Do NOT wait for the popup to close (it has a 1-min resend timer).
        System.out.println("Sign Up popup confirmed — waiting 2s before opening YOPmail for " + EMAIL);
        Thread.sleep(2000);
    }

    // Re-fills the sign-up form fields after a captcha failure (the page may clear inputs).
    private void refillFormAfterCaptcha() throws InterruptedException {
        // Only overwrite fields that are empty or have been cleared
        try {
            WebElement nameField = driver.findElement(nameInputLocator);
            if (nameField.getAttribute("value") == null || nameField.getAttribute("value").isEmpty()) {
                nameField.clear();
                nameField.sendKeys(NAME);
            }
        } catch (Exception ignored) {}

        try {
            WebElement emailField = driver.findElement(emailInputLocator);
            if (emailField.getAttribute("value") == null || emailField.getAttribute("value").isEmpty()) {
                emailField.clear();
                emailField.sendKeys(EMAIL);
            }
        } catch (Exception ignored) {}

        try {
            WebElement pwdField = driver.findElement(passwordInputLocator);
            if (pwdField.getAttribute("value") == null || pwdField.getAttribute("value").isEmpty()) {
                pwdField.clear();
                pwdField.sendKeys(PASSWORD);
            }
        } catch (Exception ignored) {}

        try {
            WebElement confirmField = driver.findElement(confirmPwdInputLocator);
            if (confirmField.getAttribute("value") == null || confirmField.getAttribute("value").isEmpty()) {
                confirmField.clear();
                confirmField.sendKeys(PASSWORD);
            }
        } catch (Exception ignored) {}

        // Re-check terms checkbox if unchecked
        try {
            WebElement terms = driver.findElement(acceptTermsLocator);
            if (!terms.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", terms);
                System.out.println("Terms re-checked after captcha failure");
            }
        } catch (Exception ignored) {}

        Thread.sleep(200);
        System.out.println("Form re-filled for retry attempt");
    }

    // ======================================
    // STEP 4 — VERIFY EMAIL VIA YOPMAIL
    // ======================================

    private void verifyEmailViaYopmail() throws InterruptedException {
        System.out.println("Opening YOPmail to verify email");

        String mainWindow = driver.getWindowHandle();

        // Open YOPmail in a new browser tab (with DNS-error retry)
        driver.switchTo().newWindow(WindowType.TAB);
        loadYopmailWithRetry();
        System.out.println("YOPmail loaded");

        // Clear the search field and enter the disposable email username
        WebElement loginField = driver.findElement(yopmailLoginInput);
        loginField.clear();
        loginField.sendKeys(YOPMAIL_USERNAME);
        Thread.sleep(200);

        // Click Go button; fall back to Enter key
        boolean goClicked = false;
        try {
            WebElement goBtn = shortWait.until(ExpectedConditions.elementToBeClickable(yopmailGoButton));
            jsClick(goBtn);
            goClicked = true;
            System.out.println("YOPmail Go button clicked");
        } catch (Exception ignored) {}

        if (!goClicked) {
            loginField.sendKeys(Keys.ENTER);
            System.out.println("YOPmail Go triggered via Enter key");
        }

        // Wait for the verification email to arrive before first inbox check
        Thread.sleep(4000);

        // Step A: Click the email row in the inbox (opens it in the ifmail iframe)
        openConfirmationEmail();

        // Step B: Click the "Verify Email" button inside the email body iframe
        String yopmailTab   = driver.getWindowHandle();
        Set<String> before  = driver.getWindowHandles();
        clickVerifyEmailButton();
        Set<String> after   = driver.getWindowHandles();

        // If clicking "Verify Email" opened a NEW tab → it's the verification page
        String verifyTab = null;
        for (String h : after) {
            if (!before.contains(h)) {
                verifyTab = h;
                break;
            }
        }

        if (verifyTab != null) {
            driver.switchTo().window(verifyTab);
            System.out.println("Verification page loaded: " + driver.getCurrentUrl());

            // Wait for the page to load and click "Go to Dashboard"
            clickGoToDashboard();
        }

        // Close YOPmail tab
        try {
            driver.switchTo().window(yopmailTab);
            driver.close();
            System.out.println("YOPmail tab closed");
        } catch (Exception ignored) {}

        // If verification tab became the dashboard, switch to it; else use main window
        if (verifyTab != null && driver.getWindowHandles().contains(verifyTab)) {
            driver.switchTo().window(verifyTab);
        } else {
            driver.switchTo().window(mainWindow);
        }

        Thread.sleep(500);
        System.out.println("Email verification step completed — current page: " + driver.getCurrentUrl());
    }

    // ======================================
    // OPEN CONFIRMATION EMAIL IN INBOX
    // ======================================

    private void openConfirmationEmail() throws InterruptedException {
        System.out.println("Looking for 'EveryCred | Issuer Email Verification' in YOPmail inbox...");

        // Retry up to 6 times (10s each = 60s total), refreshing the inbox between attempts
        for (int attempt = 1; attempt <= 6; attempt++) {
            System.out.println("Inbox check attempt " + attempt + "/6...");

            boolean clicked = checkAndClickEmailInInbox();
            if (clicked) {
                System.out.println("Verification email clicked on attempt " + attempt);
                return;
            }

            if (attempt < 6) {
                System.out.println("Email not found — refreshing YOPmail inbox...");
                refreshYopmailInbox();
                Thread.sleep(5000); // wait for emails to load after refresh
            }
        }

        System.out.println("WARNING: Verification email not clicked after 6 attempts — proceeding anyway");
    }

    private boolean checkAndClickEmailInInbox() throws InterruptedException {
        // Fast-fail: if the browser session died (e.g. Chrome crashed due to too many open windows),
        // throw immediately instead of looping 6 times with meaningless retries.
        try {
            driver.getWindowHandle();
        } catch (org.openqa.selenium.NoSuchSessionException nsse) {
            throw new RuntimeException(
                "BROWSER SESSION LOST — Chrome crashed or was closed externally. " +
                "Please close all open Chrome windows from previous test runs and try again. " +
                "Error: " + nsse.getMessage(), nsse);
        }

        try {
            driver.switchTo().defaultContent();
            WebElement frame = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(inboxFrame));
            driver.switchTo().frame(frame);

            // Try exact XPath first
            try {
                WebElement emailRow = new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.elementToBeClickable(verificationEmailRow));
                jsClick(emailRow);
                Thread.sleep(2000);
                System.out.println("Clicked 'EveryCred | Issuer Email Verification' email row");
                driver.switchTo().defaultContent();
                return true;
            } catch (Exception ignored) {}

            // JS text-search fallback within ifinbox iframe
            String result = (String) ((JavascriptExecutor) driver).executeScript(
                "var all = document.querySelectorAll('div, span, td, a');" +
                "for (var i = 0; i < all.length; i++) {" +
                "  var t = (all[i].textContent || '').trim();" +
                "  if (t.includes('Issuer Email Verification') && all[i].offsetHeight > 0) {" +
                "    all[i].click();" +
                "    return 'clicked: ' + t.substring(0, 100);" +
                "  }" +
                "}" +
                "return 'not found';"
            );
            System.out.println("JS fallback: " + result);
            if (result != null && result.startsWith("clicked")) {
                Thread.sleep(2000);
                driver.switchTo().defaultContent();
                return true;
            }
        } catch (Exception e) {
            System.out.println("ifinbox check failed: " + e.getMessage());
        } finally {
            try { driver.switchTo().defaultContent(); } catch (Exception ignored) {}
        }
        return false;
    }

    private void refreshYopmailInbox() {
        try {
            driver.switchTo().defaultContent();
            // Navigate fresh to YOPmail — more reliable than finding the login input
            // after iframe interactions which can leave the page in an inconsistent state.
            driver.navigate().to(YOPMAIL_URL);
            Thread.sleep(2500);
            WebElement field = new WebDriverWait(driver, Duration.ofSeconds(12))
                    .until(ExpectedConditions.visibilityOfElementLocated(yopmailLoginInput));
            field.clear();
            field.sendKeys(YOPMAIL_USERNAME);
            field.sendKeys(Keys.ENTER);
            Thread.sleep(1500);
            System.out.println("YOPmail inbox refreshed");
        } catch (Exception e) {
            // Fallback: just reload the page
            try {
                driver.navigate().refresh();
                Thread.sleep(2000);
                System.out.println("YOPmail reloaded as fallback");
            } catch (Exception ignored) {}
            System.out.println("Inbox refresh warning: " + e.getMessage());
        }
    }

    // ======================================
    // CLICK "VERIFY EMAIL" BUTTON IN EMAIL
    // ======================================

    private void clickVerifyEmailButton() throws InterruptedException {
        System.out.println("Switching to email body iframe (ifmail)...");

        // Wait for the ifmail iframe to appear and have content
        boolean clicked = false;
        try {
            WebElement mailFrame = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(emailBodyFrame));
            driver.switchTo().frame(mailFrame);

            // Wait until the iframe body actually contains "Verify Email" text
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
                try {
                    String body = (String) ((JavascriptExecutor) d)
                            .executeScript("return document.body ? document.body.innerText : '';");
                    return body != null && body.toLowerCase().contains("verify");
                } catch (Exception ex) { return false; }
            });
            System.out.println("Email body loaded in ifmail iframe");

            // Try standard XPath first
            WebElement btn = null;
            try {
                btn = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.presenceOfElementLocated(verifyEmailBtn));
            } catch (Exception ignored) {}

            if (btn == null) {
                // Use JavaScript to find by exact/partial text
                btn = (WebElement) ((JavascriptExecutor) driver).executeScript(
                    "var links = document.querySelectorAll('a, button');" +
                    "for (var i = 0; i < links.length; i++) {" +
                    "  var t = (links[i].textContent || links[i].innerText || '').trim();" +
                    "  if (t === 'Verify Email' || t.toLowerCase().includes('verify email')) return links[i];" +
                    "}" +
                    "return null;"
                );
            }

            if (btn != null) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                Thread.sleep(500);
                System.out.println("Clicking Verify Email button...");
                try {
                    btn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
                System.out.println("Verify Email button clicked!");
                clicked = true;
                Thread.sleep(1500);
            } else {
                System.out.println("Verify Email element not found — will fall back to URL extraction");
            }

        } catch (Exception e) {
            System.out.println("ifmail approach failed: " + e.getMessage());
        } finally {
            try { driver.switchTo().defaultContent(); } catch (Exception ignored) {}
        }

        if (!clicked) {
            System.out.println("Falling back to direct URL navigation...");
            String verifyUrl = extractVerifyEmailUrl();
            if (verifyUrl != null && verifyUrl.startsWith("http")) {
                System.out.println("Navigating directly to: " + verifyUrl);
                driver.get(verifyUrl);
                Thread.sleep(2000);
            }
        }
    }

    // ======================================
    // CLICK "GO TO DASHBOARD" ON VERIFY PAGE
    // ======================================

    private void clickGoToDashboard() throws InterruptedException {
        System.out.println("Checking verification result page...");
        try {
            // Wait for page to fully load
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> ((JavascriptExecutor) d)
                            .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1500);

            String pageText = (String) ((JavascriptExecutor) driver)
                    .executeScript("return document.body ? document.body.innerText : '';");
            String cleanText = pageText.trim().replaceAll("\\s+", " ");
            System.out.println("Verification page text: " + cleanText);

            // Detect expired / already-used link
            if (cleanText.contains("expired") || cleanText.contains("Verification Failed")) {
                System.out.println("WARNING: Verification link has expired or was already used. " +
                        "This happens when the same YOPmail link is clicked more than once. " +
                        "Use a fresh email account for a new sign-up run.");
                // Click "Go to Sign in" to recover gracefully
                By signInBtn = By.xpath(
                        "//button[contains(normalize-space(),'Sign in') or contains(normalize-space(),'Sign In')] | " +
                        "//a[contains(normalize-space(),'Sign in') or contains(normalize-space(),'Sign In')]");
                try {
                    WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(signInBtn));
                    System.out.println("Clicking: " + btn.getText().trim());
                    try { btn.click(); } catch (Exception ex) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    }
                    Thread.sleep(1500);
                    System.out.println("Navigated to: " + driver.getCurrentUrl());
                } catch (Exception ignored) {}
                return;
            }

            // Success case — find "Go to Dashboard" or equivalent button
            By dashboardBtn = By.xpath(
                    "//*[contains(normalize-space(),'Go to Dashboard') or " +
                    "contains(normalize-space(),'Go To Dashboard') or " +
                    "contains(normalize-space(),'Dashboard') or " +
                    "contains(normalize-space(),'Go to Sign in') or " +
                    "contains(normalize-space(),'Go to Login') or " +
                    "contains(normalize-space(),'Proceed') or " +
                    "contains(normalize-space(),'Continue')]" +
                    "[self::button or self::a or self::span]");

            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(dashboardBtn));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            Thread.sleep(300);

            System.out.println("Clicking: " + btn.getText().trim());
            try {
                btn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }

            System.out.println("Button clicked! Waiting for dashboard navigation...");

            // Wait up to 10s for URL to change to dashboard (fast SPA redirect — typically 1-2s)
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(d -> {
                            String url = d.getCurrentUrl();
                            return url.contains("/admin/dashboard") || url.contains("/auth/login");
                        });
            } catch (Exception urlEx) {
                System.out.println("Dashboard URL not confirmed — current: " + driver.getCurrentUrl());
            }

            Thread.sleep(500); // brief pause for page paint — keeps total time ~3s
            System.out.println("Page after click: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("Verification page button not found: " + e.getMessage());
        }
    }

    // ======================================
    // FALLBACK — EXTRACT VERIFY URL VIA JS
    // ======================================

    private String extractVerifyEmailUrl() throws InterruptedException {
        System.out.println("Extracting Verify Email URL from ifmail iframe via JavaScript...");
        for (int attempt = 1; attempt <= 6; attempt++) {
            try {
                String url = (String) ((JavascriptExecutor) driver).executeScript(
                    "try {" +
                    "  var iframe = document.getElementById('ifmail');" +
                    "  if (!iframe) return null;" +
                    "  var doc = iframe.contentDocument || iframe.contentWindow.document;" +
                    "  if (!doc || !doc.body) return null;" +
                    "  var links = doc.getElementsByTagName('a');" +
                    "  for (var i = 0; i < links.length; i++) {" +
                    "    var txt = (links[i].textContent || links[i].innerText || '').trim();" +
                    "    if (txt === 'Verify Email') return links[i].href;" +
                    "  }" +
                    "  for (var j = 0; j < links.length; j++) {" +
                    "    var t = (links[j].textContent || links[j].innerText || '').trim().toLowerCase();" +
                    "    if (t.includes('verify') || t.includes('confirm') || t.includes('activate')) {" +
                    "      return links[j].href;" +
                    "    }" +
                    "  }" +
                    "  return null;" +
                    "} catch(e) { return null; }"
                );
                if (url != null && url.startsWith("http")) {
                    System.out.println("Fallback: Verify Email URL found → " + url);
                    return url;
                }
            } catch (Exception ignored) {}
            Thread.sleep(1000);
        }
        return null;
    }

    // ======================================
    // UTILITY — LOAD YOPMAIL WITH DNS-ERROR RETRY
    // ======================================

    private void loadYopmailWithRetry() throws InterruptedException {
        String[] urls = { YOPMAIL_URL, "https://www.yopmail.com/en/", "https://yopmail.com/" };
        for (int attempt = 1; attempt <= 3; attempt++) {
            String url = urls[Math.min(attempt - 1, urls.length - 1)];
            System.out.println("YOPmail load attempt " + attempt + " → " + url);
            driver.get(url);
            Thread.sleep(3000);
            String src = driver.getPageSource();
            boolean error = src.contains("ERR_NAME_NOT_RESOLVED") ||
                            src.contains("ERR_CONNECTION_") ||
                            src.contains("This site can't be reached") ||
                            src.contains("DNS_PROBE");
            if (!error) {
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.visibilityOfElementLocated(yopmailLoginInput));
                    System.out.println("YOPmail loaded successfully on attempt " + attempt);
                    return;
                } catch (Exception ignored) {
                    System.out.println("Login input not visible yet on attempt " + attempt);
                }
            } else {
                System.out.println("DNS error on attempt " + attempt + " — retrying...");
                Thread.sleep(2000);
            }
        }
        // Final fallback — throw if still can't load
        wait.until(ExpectedConditions.visibilityOfElementLocated(yopmailLoginInput));
    }

    // ======================================
    // UTILITY — ENTER TEXT
    // ======================================

    private void enterText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    // ======================================
    // UTILITY — JS CLICK WITH STALE RETRY
    // ======================================

    public void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int attempts = 0;
        while (attempts < 3) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                try { Thread.sleep(80); } catch (InterruptedException ignored) {}
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element on click — retrying (" + (attempts + 1) + ")");
                attempts++;
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("jsClick failed after retries");
    }
}
