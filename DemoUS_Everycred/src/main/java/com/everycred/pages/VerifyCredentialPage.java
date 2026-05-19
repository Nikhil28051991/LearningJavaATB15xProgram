package com.everycred.pages;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class VerifyCredentialPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public VerifyCredentialPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }


    // ======================================
    // DELAY
    // ======================================

    public void delay() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // improved (no behavior change)
        }
    }


    // ======================================
    // COMMON WAIT METHODS (ADDED)
    // ======================================

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    // ======================================
    // VERIFY CREDENTIAL FLOW
    // ======================================

    // ======================================
    // DISMISS ONBOARDING COMPLETE MODAL
    // ======================================

    private void dismissOnboardingCompleteIfPresent() throws InterruptedException {
        try {
            // The "Onboarding Complete" modal appears after all 5 setup steps finish.
            // It contains a "Go To Dashboard" button that must be clicked to close it.
            WebElement goToDashBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//app-onboarding-complete-dialog//button | " +
                    "//button[.//span[normalize-space()='Go To Dashboard']] | " +
                    "//span[normalize-space()='Go To Dashboard']")));
            System.out.println("'Onboarding Complete' modal detected — clicking 'Go To Dashboard'");
            jsClick(goToDashBtn);
            Thread.sleep(1500);
            // Wait for the modal to fully disappear
            new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//app-onboarding-complete-dialog")));
            System.out.println("'Onboarding Complete' modal dismissed");
        } catch (Exception e) {
            // Modal not present — proceed normally
            System.out.println("No 'Onboarding Complete' modal found — continuing");
        }
    }


    public void verifyCredentialFlow() throws InterruptedException {

        System.out.println("Starting Verify Credential Flow");

        // Dismiss the "Onboarding Complete" popup if it appears after issuing credential
        dismissOnboardingCompleteIfPresent();

        // ======================================
        // ENSURE NAVIGATION TO CREDENTIAL PAGE
        // ======================================

        if (!driver.getCurrentUrl().contains("/credentials")) {

            System.out.println("Navigating to Credentials page manually");

            WebElement credentialTab = waitClickable(
                    By.xpath("//a[contains(@href,'credentials')]"));

            jsClick(credentialTab);

            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

        Thread.sleep(500);

        // =============================
        // ISSUED TAB
        // =============================

        System.out.println("Opening Issued tab");

        WebElement issuedTab = waitClickable(
                By.xpath("//button[normalize-space()='Issued']"));

        jsClick(issuedTab);

        // Dismiss again in case the modal re-triggered after tab click
        dismissOnboardingCompleteIfPresent();

        // Wait for at least one issued row (retry with refresh if empty on first check)
        System.out.println("Waiting for issued credential to appear in table...");
        boolean rowFound = false;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]")));
                rowFound = true;
                System.out.println("Credential row found on attempt " + attempt);
                break;
            } catch (Exception e) {
                System.out.println("Row not visible yet (attempt " + attempt + ") — refreshing page...");
                driver.navigate().refresh();
                Thread.sleep(2000);
                // Click Issued tab again after refresh
                try {
                    WebElement issuedTabRetry = new WebDriverWait(driver, Duration.ofSeconds(8))
                        .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Issued']")));
                    jsClick(issuedTabRetry);
                    dismissOnboardingCompleteIfPresent();
                } catch (Exception ignored) {}
            }
        }
        if (!rowFound) {
            throw new RuntimeException("Issued credential row not found in table after 3 attempts");
        }

        Thread.sleep(500);

        System.out.println("Locating verify button in issued credentials row...");

        By verifyTickBy = By.xpath("(//button[@aria-label='Verify credential'])[1]");
        WebElement verifyTick = waitClickable(verifyTickBy);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // ── Intercept window.open BEFORE clicking so we capture the real verification URL ──
        js.executeScript(
            "window._capturedVerifyUrl = null; " +
            "var _orig = window.open; " +
            "window.open = function(url, name, features) { " +
            "  if (url) window._capturedVerifyUrl = url; " +
            "  return _orig.apply(this, arguments); " +
            "};");

        // Also look for a direct href on the credential row (share link, public URL)
        String domVerifyUrl = null;
        try {
            domVerifyUrl = (String) js.executeScript(
                "var row = document.querySelector('tbody tr:first-child');" +
                "if (!row) return null;" +
                "var vBtn = row.querySelector('[aria-label=\"Verify credential\"]');" +
                "if (vBtn) {" +
                "  var u = vBtn.getAttribute('data-url') || vBtn.getAttribute('data-href') || vBtn.getAttribute('href');" +
                "  if (u && u !== '#') return u;" +
                "}" +
                "var links = row.querySelectorAll('a[href]');" +
                "for (var l of links) { if (l.href && !l.href.includes('javascript') && !l.href.endsWith('#')) return l.href; }" +
                "return null;");
            if (domVerifyUrl != null) System.out.println("DOM credential URL found: " + domVerifyUrl);
        } catch (Exception ignored) {}

        String mainWindow = driver.getWindowHandle();

        // Try native click first — more reliable for buttons that open new windows
        System.out.println("Clicking verify button...");
        try {
            verifyTick.click();
        } catch (Exception e) {
            clickVerifyButtonWithRetry(verifyTickBy);
        }

        // ── Wait for the new window/tab to open ──
        boolean newWindowOpened = false;
        try {
            wait.until(d -> d.getWindowHandles().size() > 1);
            newWindowOpened = true;
        } catch (Exception e) {
            System.out.println("No new window opened after verify click — checking for in-page modal...");
        }

        // Re-read intercepted URL on main window (some builds set it async)
        String capturedVerifyUrl = null;
        try {
            capturedVerifyUrl = (String) js.executeScript("return window._capturedVerifyUrl;");
            if (capturedVerifyUrl != null && !capturedVerifyUrl.isEmpty()) {
                System.out.println("Intercepted verification URL: " + capturedVerifyUrl);
            }
        } catch (Exception ignored) {}

        String verifyUrl = capturedVerifyUrl != null ? capturedVerifyUrl : domVerifyUrl;

        // Always save app URL BEFORE any tab switching so we can navigate back cleanly.
        String appUrl = driver.getCurrentUrl();

        if (newWindowOpened) {
            // ── A new verifier tab opened — use it; the main tab stays on the app ──
            System.out.println("Verifier tab opened — switching to it (main tab stays on app)");
            switchToVerifierTab(mainWindow, verifyUrl);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                        d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
            } catch (Exception ignored) {}
            Thread.sleep(600);

            String currentTabUrl = driver.getCurrentUrl();
            System.out.println("Verifier tab URL: " + currentTabUrl);

            // If the tab landed on an auth redirect, navigate it to the real verifier URL
            boolean isAuthRedirect = currentTabUrl.contains("/auth/") || currentTabUrl.contains("/sign-up")
                    || currentTabUrl.contains("/login");
            if (isAuthRedirect && verifyUrl != null && !verifyUrl.isEmpty() && !verifyUrl.contains("/auth/")) {
                System.out.println("Auth redirect detected — navigating verifier tab directly to: " + verifyUrl);
                driver.navigate().to(verifyUrl);
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                            d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
                } catch (Exception ignored) {}
                Thread.sleep(800);
            }

            js = (JavascriptExecutor) driver;
            scrollVerifierPageForLazyContent(js);
            clickVerifyButtonInPage(js);
            System.out.println("Waiting ~4.5s after Verify click for verification to progress...");
            Thread.sleep(4500);
            waitForVerificationComplete();
            Thread.sleep(2000);

            // Close verifier tab → return to main tab → navigate back to Credentials
            cleanupAfterVerification(mainWindow, true);
            navigateBackToApp(appUrl, mainWindow);

        } else if (verifyUrl != null && !verifyUrl.isBlank()) {
            // ── No new tab opened but we have the URL — navigate main tab to verifier ──
            System.out.println("No new tab — navigating main tab to verifier URL: " + verifyUrl);
            driver.switchTo().window(mainWindow);
            driver.navigate().to(verifyUrl);
            try {
                new WebDriverWait(driver, Duration.ofSeconds(12)).until(
                        d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
            } catch (Exception ignored) {}
            Thread.sleep(600);

            js = (JavascriptExecutor) driver;
            scrollVerifierPageForLazyContent(js);
            clickVerifyButtonInPage(js);
            System.out.println("Waiting ~4.5s after Verify click for verification to progress...");
            Thread.sleep(4500);
            waitForVerificationComplete();
            Thread.sleep(1500);
            navigateBackToApp(appUrl, mainWindow);

        } else {
            // ── No new tab and no URL — in-page modal verification ──
            System.out.println("Checking for in-page verification modal...");
            clickVerifyButtonInPage(js);
            System.out.println("Waiting ~4.5s after Verify click...");
            Thread.sleep(4500);
            waitForVerificationComplete();
            navigateBackToApp(appUrl, mainWindow);
        }

    }

    /**
     * When verify opens a new tab while Sign-up / other tabs exist, picking the first non-main handle lands on the wrong page.
     * Prefer the tab whose URL matches the verifier host from the intercepted link.
     */
    private void switchToVerifierTab(String mainWindow, String preferredVerifyUrl) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(12)).until(d -> d.getWindowHandles().size() > 1);
        } catch (Exception ignored) {}

        String preferredHost = null;
        if (preferredVerifyUrl != null && !preferredVerifyUrl.isBlank()) {
            try {
                String pathSafe = preferredVerifyUrl.trim().split("\\?")[0].split("#")[0];
                if (!pathSafe.matches("(?i)^https?://.*")) {
                    pathSafe = "https://" + pathSafe;
                }
                preferredHost = URI.create(pathSafe).getHost();
            } catch (Exception ignored) {}
        }

        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        String chosenHandle = null;

        if (preferredHost != null) {
            for (String h : handles) {
                if (h.equals(mainWindow)) {
                    continue;
                }
                driver.switchTo().window(h);
                try {
                    String u = driver.getCurrentUrl();
                    if (u.contains(preferredHost)) {
                        chosenHandle = h;
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (chosenHandle == null) {
            for (String h : handles) {
                if (h.equals(mainWindow)) {
                    continue;
                }
                driver.switchTo().window(h);
                try {
                    String u = driver.getCurrentUrl();
                    if (u.contains("verifier") && !u.contains("/auth/")) {
                        chosenHandle = h;
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (chosenHandle == null) {
            for (String h : handles) {
                if (h.equals(mainWindow)) {
                    continue;
                }
                driver.switchTo().window(h);
                try {
                    String u = driver.getCurrentUrl();
                    if (!u.contains("/auth/sign-up") && !u.contains("/auth/login")) {
                        chosenHandle = h;
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (chosenHandle == null) {
            for (String h : handles) {
                if (!h.equals(mainWindow)) {
                    driver.switchTo().window(h);
                    chosenHandle = h;
                    break;
                }
            }
        }

        if (chosenHandle != null) {
            driver.switchTo().window(chosenHandle);
            System.out.println("Focused verifier workflow tab — URL: " + driver.getCurrentUrl());
        }
    }

    private void closeNonMainWindowsQuiet(String mainWindow) {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        for (String h : handles) {
            if (h.equals(mainWindow)) {
                continue;
            }
            try {
                driver.switchTo().window(h);
                driver.close();
            } catch (Exception ignored) {}
        }
        try {
            driver.switchTo().window(mainWindow);
        } catch (Exception ignored) {}
    }

    private void scrollVerifierPageForLazyContent(JavascriptExecutor js) throws InterruptedException {
        Long prev = null;
        for (int i = 0; i < 8; i++) {
            js.executeScript("window.scrollBy(0, Math.max(240, window.innerHeight * 0.9));");
            Thread.sleep(160);
            Number hNum = (Number) js.executeScript(
                    "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);");
            long h = hNum.longValue();
            if (prev != null && h == prev && i > 2) {
                break;
            }
            prev = h;
        }
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(120);
        js.executeScript("window.scrollTo(0, 0);");
        Thread.sleep(80);
    }

    // ======================================
    // HELPER — CLICK VERIFY BUTTON ON PAGE
    // ======================================

    private void clickVerifyButtonInPage(JavascriptExecutor js) throws InterruptedException {
        boolean buttonClicked = false;

        // Primary: #verifier-button (confirmed from application)
        for (int scrollPass = 0; scrollPass < 4 && !buttonClicked; scrollPass++) {
            try {
                int secs = scrollPass == 0 ? 18 : 6;
                WebElement verifyBtn = new WebDriverWait(driver, Duration.ofSeconds(secs))
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='verifier-button']")));
                js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", verifyBtn);
                Thread.sleep(220);
                try {
                    verifyBtn.click();
                } catch (Exception e1) {
                    jsClick(verifyBtn);
                }
                buttonClicked = true;
                System.out.println("✅ Verify button clicked: #verifier-button");
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0, Math.floor(window.innerHeight * 0.75));");
                Thread.sleep(200);
            }
        }

        if (!buttonClicked) {
            System.out.println("verifier-button not found after scroll passes — trying fallback XPaths...");
        }

        if (!buttonClicked) {
            String[] xpaths = {
                "//button[normalize-space()='Verify']",
                "//button[normalize-space()='Verify Credential']",
                "//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'verify')]",
                "//a[normalize-space()='Verify' or normalize-space()='Verify Credential']",
                "//*[@role='button'][contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'verify')]"
            };
            for (String xpath : xpaths) {
                try {
                    java.util.List<WebElement> candidates = driver.findElements(By.xpath(xpath));
                    if (!candidates.isEmpty()) {
                        System.out.println("Verify element found: " + xpath + " — text: " + candidates.get(0).getText());
                        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(5))
                                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        Thread.sleep(200);
                        btn.click();
                        buttonClicked = true;
                        System.out.println("✅ Verify button clicked via XPath fallback");
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (!buttonClicked) {
            try {
                Object result = js.executeScript(
                    "var elems=Array.from(document.querySelectorAll('button,[role=button],a[href],input[type=submit]'));" +
                    "var found=elems.find(function(e){var t=(e.textContent||e.value||'').toLowerCase().trim();return t.indexOf('verify')>=0;});" +
                    "if(found){found.click();return found.textContent||found.value;} return null;");
                if (result != null) {
                    System.out.println("✅ Verify button clicked via JS DOM scan: " + result);
                    buttonClicked = true;
                }
            } catch (Exception e) {
                System.out.println("JS scan failed: " + e.getMessage());
            }
        }

        if (!buttonClicked) {
            System.out.println("ℹ️ No Verify button found — page may auto-verify on load");
            System.out.println("   Current page URL  : " + driver.getCurrentUrl());
            System.out.println("   Current page title: " + driver.getTitle());
        }
    }

    // ======================================
    // HELPER — WAIT FOR VERIFICATION COMPLETE
    // ======================================

    private void waitForVerificationComplete() {
        System.out.println("Waiting for verification to complete...");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//*[contains(normalize-space(),'In Progress')]")));
            System.out.println("✅ Verification complete — In Progress status gone");
        } catch (Exception e) {
            System.out.println("In Progress status did not clear within 30s — closing anyway");
        }
    }

    // ======================================
    // HELPER — CLOSE VERIFIER TAB, RETURN TO MAIN
    // ======================================

    private void cleanupAfterVerification(String mainWindow, boolean closeCurrentTab) throws InterruptedException {
        if (closeCurrentTab) {
            System.out.println("Closing verifier tab — returning to main window...");
            driver.close();
        }
        driver.switchTo().window(mainWindow);
        Thread.sleep(300);
        System.out.println("✅ Back on main window");
    }

    /**
     * Navigates back to the app's Credentials > Issued tab.
     * Uses the saved app URL to avoid searching for nav links on the verifier domain.
     */
    private void navigateBackToApp(String savedAppUrl, String mainWindow) throws InterruptedException {
        try {
            driver.switchTo().window(mainWindow);
        } catch (Exception ignored) {}

        String currentUrl = driver.getCurrentUrl();
        // If still on verifier domain, navigate back to app via saved URL
        if (savedAppUrl != null && !currentUrl.contains(extractHost(savedAppUrl))) {
            System.out.println("Navigating back to app from verifier domain: " + savedAppUrl);
            driver.navigate().to(savedAppUrl);
            try {
                new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
            } catch (Exception ignored) {}
            Thread.sleep(500);
        }

        System.out.println("Navigating to Credentials > Issued tab");
        try {
            WebElement credTab = waitClickable(By.xpath("//a[normalize-space()='Credentials']"));
            jsClick(credTab);
            Thread.sleep(500);
            WebElement issuedTab = waitClickable(By.xpath("//button[normalize-space()='Issued']"));
            jsClick(issuedTab);
            Thread.sleep(1000);
            System.out.println("✅ Back on Credentials > Issued tab");
        } catch (Exception e) {
            System.out.println("Post-verify navigation warning: " + e.getMessage());
        }
    }

    private String extractHost(String url) {
        try {
            return URI.create(url).getHost();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Re-locates the verify button on each retry so stale references cannot
     * fail the flow. This is the guard for intermittent DOM re-render after
     * opening the Issued tab.
     */
    private void clickVerifyButtonWithRetry(By locator) throws InterruptedException {
        Exception last = null;
        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(4))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                Thread.sleep(120);
                try {
                    btn.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
                System.out.println("Verify button clicked on retry attempt " + attempt);
                return;
            } catch (Exception ex) {
                last = ex;
                Thread.sleep(250);
            }
        }
        throw new RuntimeException("Failed to click verify button after retries", last);
    }

    // ======================================
    // JS CLICK (IMPROVED WITH RETRY)
    // ======================================

    public void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int attempts = 0;
        while (attempts < 3) {
            try {
                js.executeScript("arguments[0].scrollIntoView(true);", element);
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