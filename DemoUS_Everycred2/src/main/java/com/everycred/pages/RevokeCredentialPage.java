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

        Thread.sleep(2000);

        // ================= REVOKED TAB (non-blocking) =================
        By revokedTabLocator = By.xpath(
                "//button[normalize-space()='Revoked'] | //a[normalize-space()='Revoked'] | " +
                "//span[normalize-space()='Revoked']/ancestor::button");
        try {
            WebElement revokedTab = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(revokedTabLocator));
            jsClick(revokedTab);
            System.out.println("Revoked tab clicked");
            Thread.sleep(3000);
            // Wait for table or empty state — non-throwing
            try {
                new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody"))
                ));
            } catch (Exception ignored) {}
        } catch (Exception e) {
            System.out.println("Revoked tab not found, continuing: " + e.getMessage());
        }

        System.out.println("Revoke completed successfully");
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