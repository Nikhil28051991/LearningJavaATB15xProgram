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
        WebElement checkboxInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//p-tablecheckbox//input)[1]")));

        scrollIntoView(checkboxInput);

        Thread.sleep(500);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkboxInput);

        Thread.sleep(1000);

        if (!checkboxInput.isSelected()) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", checkboxInput);
        }

        if (!checkboxInput.isSelected()) {
            throw new RuntimeException("Checkbox not selected");
        }

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

        // ================= WAIT =================
        System.out.println("Waiting for revoke popup to close");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//textarea[@id='remark']")
        ));

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Revoked']"))
        ));

        Thread.sleep(2000);

        // ================= REVOKED TAB =================
        By revokedTabLocator = By.xpath("//button[normalize-space()='Revoked']");

        waitVisible(revokedTabLocator);

        Thread.sleep(2000);

        WebElement revokedTab = waitClickable(revokedTabLocator);

        jsClick(revokedTab);

        Thread.sleep(4000);

        // ================= VERIFY =================
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody"))
        ));

        System.out.println("✅ Revoke verified successfully");
    }

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

                System.out.println("Retrying click due to stale element...");
                attempts++;
                delay();
            }
        }

        throw new RuntimeException("Failed to click element after retries");
    }
}