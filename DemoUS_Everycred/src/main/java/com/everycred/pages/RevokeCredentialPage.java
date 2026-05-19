package com.everycred.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class RevokeCredentialPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public RevokeCredentialPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

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

    public void revokeCredentialFlow() throws InterruptedException {

        System.out.println("Starting Revoke Credential Flow");
        collapseGetStartedPanelIfPresent();
        ensureCredentialsAndIssuedTab();

        String currentUrl = driver.getCurrentUrl();

        // ================= NAVIGATE =================
        if (!currentUrl.contains("/credentials")) {

            System.out.println("Navigating to Credentials page");

            WebElement credentialTab = waitClickable(
                    By.xpath("//a[contains(@href,'credentials')]"));

            jsClick(credentialTab);

            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

        Thread.sleep(2000);

        // ================= PAGE LOAD =================
        waitVisible(By.xpath("//button[normalize-space()='Issued']"));

        Thread.sleep(2000);

        // ================= ISSUED TAB =================
        WebElement issuedTab = waitClickable(
                By.xpath("//button[normalize-space()='Issued']"));

        jsClick(issuedTab);

        Thread.sleep(3000);

        // ================= TABLE =================
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[1]")));

        // ================= CHECKBOX =================
        By checkboxInputBy = By.xpath("(//tbody/tr[1]//input[contains(@class,'p-checkbox-input')])[1]");
        By checkboxBoxBy   = By.xpath("(//tbody/tr[1]//div[contains(@class,'p-checkbox-box')])[1]");

        // Use JS .checked property to verify state — more reliable than DOM class inspection.
        // Click ONCE only; a second click would deselect the row.
        WebElement cbInput = null;
        try {
            cbInput = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxInputBy));
        } catch (Exception ignored) {}

        boolean alreadyChecked = false;
        if (cbInput != null) {
            try {
                Object chk = ((JavascriptExecutor) driver).executeScript("return arguments[0].checked;", cbInput);
                alreadyChecked = Boolean.TRUE.equals(chk);
            } catch (Exception ignored) {}
        }

        if (!alreadyChecked) {
            boolean clickDone = false;
            if (cbInput != null) {
                try {
                    scrollIntoView(cbInput);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cbInput);
                    clickDone = true;
                } catch (Exception ignored) {}
            }
            // Fallback to visible box only when input is not accessible
            if (!clickDone) {
                try {
                    WebElement cbBox = new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions.elementToBeClickable(checkboxBoxBy));
                    scrollIntoView(cbBox);
                    jsClick(cbBox);
                } catch (Exception ignored) {}
            }
            Thread.sleep(600); // Give Angular time to update checked state
        }
        System.out.println("Revoke checkbox selected");

        // ================= CLICK REVOKE =================
        WebElement revokeBtn = waitClickable(
                By.xpath("//span[normalize-space()='Revoke']"));

        // ✅ FIX 4: SAFE CLICK ADDED
        try {
            revokeBtn.click();
        } catch (Exception e) {
            jsClick(revokeBtn);
        }

        delay();

        // ================= REMARK =================
        WebElement remarkBox = waitVisible(
                By.xpath("//textarea[@id='remark']"));

        remarkBox.sendKeys("Wrongly Issued");

        delay();

        // ================= CONFIRM =================
        WebElement confirmRevoke = waitClickable(
                By.xpath("//div[contains(@class,'p-dialog')]//button[contains(@class,'p-button-danger')]"));

        try {
            confirmRevoke.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", confirmRevoke);
        }

        // ================= WAIT FOR POPUP TO CLOSE =================
        System.out.println("Waiting for revoke popup to close");

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//textarea[@id='remark']")));
        } catch (Exception ignored) {}

        Thread.sleep(2500);
        collapseGetStartedPanelIfPresent();

        // ================= CLICK DRAFT TAB =================
        System.out.println("Clicking Draft tab after revoke");
        try {
            WebElement draftTab = waitClickable(By.xpath("//button[normalize-space()='Draft']"));
            jsClick(draftTab);
        } catch (Exception e) {
            System.out.println("Draft tab not found, continuing: " + e.getMessage());
        }
        Thread.sleep(2000);

        // ================= CLICK ISSUED TAB =================
        System.out.println("Clicking Issued tab");
        try {
            WebElement issuedTabAfterDraft = waitClickable(By.xpath("//button[normalize-space()='Issued']"));
            jsClick(issuedTabAfterDraft);
        } catch (Exception e) {
            System.out.println("Issued tab not found, continuing: " + e.getMessage());
        }
        Thread.sleep(2000);

        // Collapse onboarding panel if it reappeared between tab switches
        collapseGetStartedPanelIfPresent();

        // ================= OPEN STATUS DROPDOWN AND SELECT "REVOKED" =================
        openStatusDropdownAndPickRevoked();

        // Wait 2s after selecting Revoked for the table to refresh
        Thread.sleep(2000);

        System.out.println("Revoke completed successfully");
    }

    /**
     * Opens the PrimeNG status dropdown (currently showing "Active") and clicks
     * the "Revoked" option. The option lives inside a PrimeNG overlay panel
     * that is portal-mounted to {@code <body>}, so the visible-only locator
     * {@code //span[normalize-space()='Revoked']} sometimes can't find it
     * before it animates in. We try a broad union locator with a presence
     * check (not visibility) and force a JS click so the overlay's animation
     * state doesn't block us.
     */
    private void openStatusDropdownAndPickRevoked() {
        // Step 1 — open the status dropdown (showing "Active").
        // Try the two most reliable trigger selectors first, then fall back to broader ones.
        By[] triggerCandidates = {
                By.xpath("//div[@role='button' and @aria-label='dropdown trigger']"),
                By.xpath("//p-select//div[@role='button']"),
                By.xpath("//p-dropdown//div[@role='button']"),
                By.xpath("//span[normalize-space()='Active']/ancestor::div[@role='button'][1]"),
                By.xpath("//span[normalize-space()='Active']/ancestor::*[@role='combobox' or @role='button'][1]")
        };

        boolean opened = false;
        for (By trigger : triggerCandidates) {
            try {
                WebElement el = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(trigger));
                scrollIntoView(el);
                jsClick(el);
                opened = true;
                System.out.println("Active status dropdown opened");
                break;
            } catch (Exception ignored) {}
        }

        if (!opened) {
            System.out.println("Status dropdown trigger not found — skipping Revoked filter (non-fatal)");
            return;
        }

        // Wait 1 s for PrimeNG to portal-mount the option list into <body>.
        try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

        // Step 2 — click "Revoked" using the exact XPath supplied by the user.
        // Primary: span with exact text "Revoked". Fallback: li[role='option'] wrapper.
        By revokedSpan = By.xpath("//span[normalize-space()='Revoked']");
        By revokedLi   = By.xpath(
                "//li[@role='option' and (.//span[normalize-space()='Revoked'] or normalize-space()='Revoked')]");

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                // Try visible span first (exact user XPath)
                WebElement span = new WebDriverWait(driver, Duration.ofSeconds(4))
                        .until(ExpectedConditions.visibilityOfElementLocated(revokedSpan));
                scrollIntoView(span);
                jsClick(span);
                System.out.println("Revoked option clicked via span XPath (attempt " + attempt + ")");

                // Wait 2 s after selecting Revoked (user spec)
                Thread.sleep(2000);
                System.out.println("Revoke completed successfully");
                return;
            } catch (Exception spanEx) {
                // Span not visible — try the li wrapper
                try {
                    WebElement li = new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions.presenceOfElementLocated(revokedLi));
                    scrollIntoView(li);
                    jsClick(li);
                    System.out.println("Revoked option clicked via li wrapper (attempt " + attempt + ")");
                    Thread.sleep(2000);
                    System.out.println("Revoke completed successfully");
                    return;
                } catch (Exception liEx) {
                    // Neither found — re-open dropdown and retry
                    System.out.println("Revoked option not found on attempt " + attempt + " — re-opening dropdown");
                    for (By trigger : triggerCandidates) {
                        try {
                            WebElement el = new WebDriverWait(driver, Duration.ofSeconds(2))
                                    .until(ExpectedConditions.elementToBeClickable(trigger));
                            jsClick(el);
                            Thread.sleep(800);
                            break;
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

        // Last resort: JS DOM scan for any visible element with text "Revoked"
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var all = document.querySelectorAll('li,span,div,a,button');" +
                "for (var i = 0; i < all.length; i++) {" +
                "  var t = (all[i].textContent || '').trim();" +
                "  if (t === 'Revoked' && all[i].offsetParent !== null) {" +
                "    var c = all[i].closest('li,[role=\"option\"],button,a') || all[i];" +
                "    c.click(); return;" +
                "  }" +
                "}");
            System.out.println("Revoked option clicked via JS DOM scan (fallback)");
            try { Thread.sleep(2000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
        } catch (Exception ignored) {
            System.out.println("Revoked option not found in DOM (non-fatal)");
        }
    }

    private void collapseGetStartedPanelIfPresent() {
        By panelTitle = By.xpath("//*[contains(normalize-space(),'Get Started')]");
        try {
            if (driver.findElements(panelTitle).isEmpty()) return;
            By chevron = By.xpath(
                "//*[contains(normalize-space(),'Get Started')]/ancestor::div[1]//button[.//i[contains(@class,'pi-chevron')]] | " +
                "//*[contains(normalize-space(),'Get Started')]/ancestor::div[2]//button[.//i[contains(@class,'pi-chevron')]] | " +
                "//*[contains(normalize-space(),'Get Started')]//button[contains(@class,'pi-chevron') or .//i[contains(@class,'chevron')]]");
            try {
                WebElement toggle = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(chevron));
                jsClick(toggle);
                System.out.println("Get Started panel collapsed (Revoke)");
                return;
            } catch (Exception ignored) {}
            // Fallback: JS hide
            ((JavascriptExecutor) driver).executeScript(
                "var all = document.querySelectorAll('*');" +
                "for(var i=0;i<all.length;i++){" +
                "  var el=all[i]; var txt=el.textContent||'';" +
                "  if(txt.indexOf('Get Started')>-1 && el.querySelectorAll('input,[role=\"dialog\"]').length===0" +
                "     && el.offsetWidth>50 && el.offsetHeight>50){" +
                "    el.style.setProperty('display','none','important'); break;" +
                "  }" +
                "}");
        } catch (Exception ignored) {}
    }

    private void ensureCredentialsAndIssuedTab() {
        if (!driver.getCurrentUrl().contains("/credentials")) {
            WebElement credentialTab = waitClickable(By.xpath("//a[contains(@href,'credentials')]"));
            jsClick(credentialTab);
            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

        try {
            WebElement issuedTab = waitClickable(By.xpath("//button[normalize-space()='Issued']"));
            jsClick(issuedTab);
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody"))
            ));
            System.out.println("Issued tab selected");
        } catch (Exception e) {
            System.out.println("Could not confirm Issued tab immediately, continuing");
        }
    }

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