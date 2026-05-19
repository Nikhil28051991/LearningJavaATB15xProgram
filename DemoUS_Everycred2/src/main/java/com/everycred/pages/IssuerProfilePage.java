package com.everycred.pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.*;

public class IssuerProfilePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait;

    // ======================================
    // CONSTANTS — unique per run
    // ======================================

    private static final String DATE_SUFFIX =
            new SimpleDateFormat("ddMMHHmm").format(new Date());

    private static final String ISSUER_EMAIL_USER =
            "admin.albuquerque" + DATE_SUFFIX;

    private static final String ISSUER_EMAIL =
            ISSUER_EMAIL_USER + "@yopmail.com";

    private static final String ISSUER_NAME =
            "City of Albuquerque (For Dem " +
                    new SimpleDateFormat("ddMM").format(new Date()) + ")";

    private static final String DESCRIPTION =
            "The National Weather Services has declared a winter storm advisory. " +
                    "View updates on City of Albuquerque closures and delays as the information becomes available.";

    private static final String LOGO_PATH =
            "C:\\Users\\Nikhil Sonawane\\Downloads\\istockphoto-902800822-612x612.jpg";

    private static final String BANNER_PATH =
            "C:\\Users\\Nikhil Sonawane\\Downloads\\download (35).jpg";

    private static final String ISSUER_WEBSITE =
            "https://www.cabq.gov";

    private static final String X_URL =
            "https://x.com/cabq";

    private static final String LINKEDIN_URL =
            "https://www.linkedin.com/company/city-of-albuquerque";

    private static final String FACEBOOK_URL =
            "https://www.facebook.com/cabqinfo/";

    private static final String INSTAGRAM_URL =
            "https://www.instagram.com/oneabq/?hl=en";

    private static final String YOPMAIL_URL =
            "https://yopmail.com/en/";

    // ======================================
    // LOCATORS — DASHBOARD & NAVIGATION
    // ======================================

    private final By dashboardLink =
            By.xpath("//a[normalize-space()='Dashboard']");

    private final By onboardingCollapse =
            By.xpath("//button[@type='button']//i[contains(@class,'pi-chevron-down')]");

    private final By profileDropdown =
            By.xpath("//div[@class='max-md:hidden']//i[@class='pi pi-chevron-down']");

    private final By addIssuerSpan =
            By.xpath("//span[normalize-space()='Add Issuer'] | " +
                    "//a[contains(normalize-space(),'Add Issuer')] | " +
                    "//li[contains(normalize-space(),'Add Issuer')]//span[@class='text-sm']");

    private final By signOutSpan =
            By.xpath("//span[normalize-space()='Sign Out']");

    // ======================================
    // LOCATORS — ISSUER DETAILS FORM
    // ======================================

    private final By issuerNameInput =
            By.xpath("//input[@id='issuer_name']");

    private final By issuerEmailInput =
            By.xpath("//input[@id='email']");

    private final By descriptionTextarea =
            By.xpath("//textarea[@id='description']");

    private final By nextBtn =
            By.xpath("//button[normalize-space()='Next']");

    // ======================================
    // LOCATORS — ISSUER BRANDING
    // ======================================

    private final By logoFileInput =
            By.xpath("//input[@id='headerFooter']");

    private final By bannerFileInput =
            By.xpath("//input[@id='bannerImage']");

    private final By websiteInput =
            By.xpath("//input[@id='issuer_website']");

    private final By xUrlInput =
            By.xpath("//input[@id='x_url']");

    private final By linkedinInput =
            By.xpath("//input[@id='linkedin_url']");

    private final By facebookInput =
            By.xpath("//input[@id='facebook_url']");

    private final By instagramInput =
            By.xpath("//input[@id='instagram_url']");

    private final By saveBtn =
            By.xpath("//button[normalize-space()='Save']");

    // ======================================
    // LOCATORS — OTP POPUP
    // ======================================

    private final By otpPopup =
            By.xpath("//*[contains(normalize-space(),'OTP') or " +
                    "contains(normalize-space(),'Verify Your Email Address')]");

    private final By otpInputFields =
            By.xpath("//input[@maxlength='1'] | //p-inputotp//input");

    private final By verifyCodeBtn =
            By.xpath("//button[contains(normalize-space(),'Verify Code')]");

    // ======================================
    // LOCATORS — YOPMAIL
    // ======================================

    private final By yopmailLoginInput =
            By.xpath("//input[@id='login']");

    private final By yopmailGoButton =
            By.xpath("//a[@id='go'] | //button[@id='go'] | " +
                    "//*[contains(@class,'material-icons') and normalize-space()='forward']");

    private final By inboxFrame =
            By.xpath("//iframe[@id='ifinbox']");

    private final By emailBodyFrame =
            By.xpath("//iframe[@id='ifmail']");

    private final By otpEmailInInbox =
            By.xpath("//div[contains(normalize-space(),'OTP Verification')]");

    // ======================================
    // CONSTRUCTOR
    // ======================================

    public IssuerProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static String getIssuerEmail() {
        return ISSUER_EMAIL;
    }

    // ======================================
    // MAIN FLOW
    // ======================================

    public void issuerProfileFlow() throws InterruptedException {

        System.out.println("===== STARTING ISSUER PROFILE FLOW =====");

        System.out.println("Issuer Name  : " + ISSUER_NAME);
        System.out.println("Issuer Email : " + ISSUER_EMAIL);

        ensureOnDashboard();

        collapseOnboardingPanel();

        openProfileDropdownAndAddIssuer();

        fillIssuerDetails();

        fillBlockchainCredentialSetup();

        fillIssuerBranding();

        retrieveOTPAndVerify();

        logoutFromDashboard();

        System.out.println("===== ISSUER PROFILE FLOW COMPLETED =====");
    }

    // ======================================
    // STEP 0 — ENSURE DASHBOARD
    // ======================================

    private void ensureOnDashboard() throws InterruptedException {

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL before Issuer Profile: " + currentUrl);

        if (!currentUrl.contains("/admin/dashboard")) {

            System.out.println("Navigating to dashboard...");

            driver.get("https://demo-dcs-issuer-us.everycred.com/admin/dashboard");

            wait.until(d ->
                    ((JavascriptExecutor) d)
                            .executeScript("return document.readyState")
                            .equals("complete"));

            Thread.sleep(2000);

            String afterNav = driver.getCurrentUrl();

            if (afterNav.contains("/auth/login")) {

                throw new RuntimeException(
                        "Cannot reach dashboard — user not logged in");
            }

        } else {

            Thread.sleep(1500);
        }
    }

    // ======================================
    // STEP 1 — COLLAPSE PANEL
    // ======================================

    private void collapseOnboardingPanel() {

        try {

            WebElement icon =
                    new WebDriverWait(driver, Duration.ofSeconds(8))
                            .until(ExpectedConditions.visibilityOfElementLocated(onboardingCollapse));

            WebElement button =
                    icon.findElement(By.xpath("./ancestor::button"));

            jsClick(button);

            System.out.println("Get Started panel collapsed");

        } catch (Exception e) {

            System.out.println("Onboarding panel already collapsed");
        }
    }

    // ======================================
    // STEP 2 — ADD ISSUER
    // ======================================

    private void openProfileDropdownAndAddIssuer() throws InterruptedException {

        System.out.println("Opening profile dropdown...");

        WebElement arrow =
                wait.until(ExpectedConditions.elementToBeClickable(profileDropdown));

        jsClick(arrow);

        Thread.sleep(300);

        WebElement addIssuer =
                wait.until(ExpectedConditions.elementToBeClickable(addIssuerSpan));

        jsClick(addIssuer);

        wait.until(ExpectedConditions.visibilityOfElementLocated(issuerNameInput));

        System.out.println("Issuer Details page loaded");
    }

    // ======================================
    // STEP 3 — ISSUER DETAILS
    // ======================================

    private void fillIssuerDetails() {

        System.out.println("Filling issuer details...");

        enterText(issuerNameInput, ISSUER_NAME);

        enterText(issuerEmailInput, ISSUER_EMAIL);

        enterText(descriptionTextarea, DESCRIPTION);

        WebElement next =
                wait.until(ExpectedConditions.elementToBeClickable(nextBtn));

        jsClick(next);

        System.out.println("Issuer Details completed");
    }

    // ======================================
    // STEP 4 — BLOCKCHAIN
    // ======================================

    private void fillBlockchainCredentialSetup() {

        System.out.println("Blockchain setup — keeping defaults");

        WebElement next =
                wait.until(ExpectedConditions.elementToBeClickable(nextBtn));

        jsClick(next);
    }

    // ======================================
    // STEP 5 — BRANDING
    // ======================================

    private void fillIssuerBranding() throws InterruptedException {

        System.out.println("===== FILLING ISSUER BRANDING =====");

        // ==================================
        // LOGO
        // ==================================

        boolean logoDone = false;

        for (int i = 1; i <= 3; i++) {

            System.out.println("Logo upload attempt: " + i);

            uploadLogoWithBestInput(LOGO_PATH);

            Thread.sleep(500);

            logoDone = handleCropDialogIfPresent("Logo");

            if (logoDone) {
                break;
            }

            Thread.sleep(300);
        }

        if (!logoDone) {

            System.out.println("Logo crop dialog not detected");
        }

        // ==================================
        // BANNER
        // ==================================

        boolean bannerDone = false;

        for (int i = 1; i <= 3; i++) {

            System.out.println("Banner upload attempt: " + i);

            uploadFile(bannerFileInput, BANNER_PATH, "Banner");

            Thread.sleep(500);

            bannerDone = handleCropDialogIfPresent("Banner");

            if (bannerDone) {
                break;
            }

            Thread.sleep(300);
        }

        if (!bannerDone) {

            System.out.println("Banner crop dialog not detected");
        }

        // ==================================
        // URLS
        // ==================================

        enterTextScrolled(websiteInput, ISSUER_WEBSITE);

        enterTextScrolled(xUrlInput, X_URL);

        enterTextScrolled(linkedinInput, LINKEDIN_URL);

        enterTextScrolled(facebookInput, FACEBOOK_URL);

        enterTextScrolled(instagramInput, INSTAGRAM_URL);

        // ==================================
        // SAVE
        // ==================================

        WebElement save =
                wait.until(ExpectedConditions.elementToBeClickable(saveBtn));

        jsClick(save);

        System.out.println("Save clicked");

        wait.until(ExpectedConditions.visibilityOfElementLocated(otpPopup));

        System.out.println("OTP popup appeared");
    }

    // ======================================
    // STEP 6 — OTP
    // ======================================

    private void retrieveOTPAndVerify() throws InterruptedException {

        System.out.println("Opening YOPmail...");

        String currentWindow = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);

        loadYopmailWithRetry();

        WebElement loginField =
                driver.findElement(yopmailLoginInput);

        loginField.clear();

        loginField.sendKeys(ISSUER_EMAIL_USER);

        boolean clicked = false;

        try {

            WebElement goBtn =
                    shortWait.until(ExpectedConditions.elementToBeClickable(yopmailGoButton));

            jsClick(goBtn);

            clicked = true;

        } catch (Exception ignored) {
        }

        if (!clicked) {

            loginField.sendKeys(Keys.ENTER);
        }

        Thread.sleep(2500);

        openOTPEmailInInbox();

        String otpCode = extractOTPFromEmailBody();

        System.out.println("OTP extracted: " + otpCode);

        driver.close();

        driver.switchTo().window(currentWindow);

        Thread.sleep(500);

        if (otpCode != null && otpCode.matches("\\d{6}")) {

            enterOTPInPopup(otpCode);

        } else {

            throw new RuntimeException("Invalid OTP");
        }
    }

    // ======================================
    // OTP EMAIL
    // ======================================

    private void openOTPEmailInInbox() throws InterruptedException {

        System.out.println("Opening OTP email...");

        try {

            WebElement frame =
                    wait.until(ExpectedConditions.presenceOfElementLocated(inboxFrame));

            driver.switchTo().frame(frame);

            WebElement emailRow =
                    wait.until(ExpectedConditions.elementToBeClickable(otpEmailInInbox));

            jsClick(emailRow);

            driver.switchTo().defaultContent();

            Thread.sleep(1500);

        } catch (Exception e) {

            driver.switchTo().defaultContent();

            System.out.println("Inbox handling fallback");
        }
    }

    // ======================================
    // EXTRACT OTP
    // ======================================

    private String extractOTPFromEmailBody() throws InterruptedException {

        for (int i = 1; i <= 8; i++) {

            try {

                String otp =
                        (String) ((JavascriptExecutor) driver).executeScript(

                                "try {" +
                                        "var iframe = document.getElementById('ifmail');" +
                                        "if(!iframe) return null;" +
                                        "var doc = iframe.contentDocument || iframe.contentWindow.document;" +
                                        "if(!doc || !doc.body) return null;" +
                                        "var text = doc.body.innerText;" +
                                        "var match = text.match(/\\b(\\d{6})\\b/);" +
                                        "return match ? match[1] : null;" +
                                        "} catch(e) { return null; }"

                        );

                if (otp != null && otp.matches("\\d{6}")) {

                    return otp;
                }

            } catch (Exception ignored) {
            }

            Thread.sleep(1000);
        }

        return null;
    }

    // ======================================
    // ENTER OTP
    // ======================================

    private void enterOTPInPopup(String otpCode) throws InterruptedException {

        System.out.println("Entering OTP...");

        wait.until(ExpectedConditions.visibilityOfElementLocated(otpInputFields));

        List<WebElement> inputs =
                driver.findElements(otpInputFields);

        if (inputs.size() >= 6) {

            for (int i = 0; i < 6; i++) {

                inputs.get(i).sendKeys(
                        String.valueOf(otpCode.charAt(i)));
            }

        } else {

            inputs.get(0).sendKeys(otpCode);
        }

        WebElement verify =
                wait.until(ExpectedConditions.elementToBeClickable(verifyCodeBtn));

        jsClick(verify);

        System.out.println("Verify clicked");

        waitUntilGlobalLoadersQuiet(Duration.ofSeconds(30));

        waitForIssuerActivationSuccessAfterOtp(Duration.ofSeconds(60));

        waitUntilGlobalLoadersQuiet(Duration.ofSeconds(20));

        // IMPORTANT FIX
        // Wait before logout
        Thread.sleep(3000);

        System.out.println("Issuer activation successful");
    }

    // ======================================
    // SUCCESS WAIT
    // ======================================

    private void waitForIssuerActivationSuccessAfterOtp(Duration timeout) {

        WebDriverWait w =
                new WebDriverWait(driver, timeout);

        w.until(d -> {

            String body =
                    d.findElement(By.tagName("body"))
                            .getText()
                            .toLowerCase();

            return body.contains("success")
                    || body.contains("activated")
                    || body.contains("issuer profile created")
                    || body.contains("verification successful");
        });
    }

    // ======================================
    // LOGOUT
    // ======================================

    private void logoutFromDashboard() throws InterruptedException {

        System.out.println("Logging out...");

        waitUntilGlobalLoadersQuiet(Duration.ofSeconds(10));

        WebElement arrow =
                wait.until(ExpectedConditions.elementToBeClickable(profileDropdown));

        jsClick(arrow);

        Thread.sleep(300);

        WebElement signOut =
                wait.until(ExpectedConditions.elementToBeClickable(signOutSpan));

        jsClick(signOut);

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(d -> d.getCurrentUrl().contains("login"));

        Thread.sleep(500);

        System.out.println("Successfully logged out");
    }

    // ======================================
    // YOPMAIL RETRY
    // ======================================

    private void loadYopmailWithRetry() throws InterruptedException {

        String[] urls = {
                YOPMAIL_URL,
                "https://www.yopmail.com/en/",
                "https://yopmail.com/"
        };

        for (int i = 0; i < urls.length; i++) {

            driver.get(urls[i]);

            Thread.sleep(2500);

            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(yopmailLoginInput));

                return;

            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Unable to load YOPmail");
    }

    // ======================================
    // LOGO FIX
    // ======================================

    private void uploadLogoWithBestInput(String filePath) throws InterruptedException {

        System.out.println("Uploading Logo...");

        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0,300)");

        Thread.sleep(300);

        List<WebElement> inputs =
                driver.findElements(By.xpath("//input[@type='file']"));

        WebElement correctInput = null;

        for (WebElement input : inputs) {

            try {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].style.display='block';" +
                                "arguments[0].style.visibility='visible';" +
                                "arguments[0].style.opacity='1';",
                        input);

                if (input.isDisplayed() && input.isEnabled()) {

                    correctInput = input;

                    break;
                }

            } catch (Exception ignored) {
            }
        }

        if (correctInput == null) {

            throw new RuntimeException("Logo upload input not found");
        }

        correctInput.sendKeys(filePath);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                correctInput);

        System.out.println("Logo uploaded");
    }

    // ======================================
    // GENERIC FILE UPLOAD
    // ======================================

    private void uploadFile(By locator,
                            String filePath,
                            String label) {

        try {

            WebElement input =
                    wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.display='block';" +
                            "arguments[0].style.visibility='visible';" +
                            "arguments[0].style.opacity='1';",
                    input);

            input.sendKeys(filePath);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    input);

            System.out.println(label + " uploaded");

        } catch (Exception e) {

            throw new RuntimeException(label + " upload failed");
        }
    }

    // ======================================
    // HANDLE CROP
    // ======================================

    private boolean handleCropDialogIfPresent(String label)
            throws InterruptedException {

        By cropBtn =
                By.xpath("//button[.//span[normalize-space()='Crop']]");

        try {

            WebElement btn =
                    new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(cropBtn));

            jsClick(btn);

            Thread.sleep(1200);

            System.out.println(label + " crop completed");

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // ======================================
    // WAIT LOADERS
    // ======================================

    private void waitUntilGlobalLoadersQuiet(Duration timeout) {

        try {

            new WebDriverWait(driver, timeout).until(d -> {

                Number count =
                        (Number) ((JavascriptExecutor) d).executeScript(

                                "return document.querySelectorAll(" +
                                        "'.p-progress-spinner," +
                                        ".p-blockui," +
                                        ".loading-spinner').length"

                        );

                return count.intValue() == 0;
            });

        } catch (Exception ignored) {
        }
    }

    // ======================================
    // FAST TEXT ENTRY
    // ======================================

    private void enterTextScrolled(By locator,
                                   String value) {

        WebElement el =
                wait.until(ExpectedConditions.elementToBeClickable(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                el);

        el.click();

        el.sendKeys(Keys.CONTROL + "a");

        el.sendKeys(Keys.DELETE);

        el.sendKeys(value);
    }

    // ======================================
    // NORMAL TEXT ENTRY
    // ======================================

    private void enterText(By locator,
                           String value) {

        WebElement el =
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        el.clear();

        el.sendKeys(value);
    }

    // ======================================
    // JS CLICK
    // ======================================

    public void jsClick(WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        int attempts = 0;

        while (attempts < 3) {

            try {

                js.executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element);

                js.executeScript(
                        "arguments[0].click();",
                        element);

                return;

            } catch (StaleElementReferenceException e) {

                attempts++;
            }
        }

        throw new RuntimeException("JS Click failed");
    }
}