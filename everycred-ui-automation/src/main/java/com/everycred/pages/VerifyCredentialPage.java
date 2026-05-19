package com.everycred.pages;

import java.time.Duration;
import java.util.Set;

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

    public void verifyCredentialFlow() throws InterruptedException {

        System.out.println("Starting Verify Credential Flow");

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

        Thread.sleep(2000);

        // =============================
        // ISSUED TAB
        // =============================

        System.out.println("Opening Issued tab");

        WebElement issuedTab = waitClickable(
                By.xpath("//button[normalize-space()='Issued']"));

        jsClick(issuedTab);

        Thread.sleep(3000);

        waitVisible(By.xpath("//tbody/tr[1]"));

        Thread.sleep(2000);

        System.out.println("Click latest verify");

        WebElement verifyTick = waitClickable(
                By.xpath("(//button[@aria-label='Verify credential'])[1]"));

        jsClick(verifyTick);

        System.out.println("Switching window");

        String mainWindow = driver.getWindowHandle();

        // ✅ WAIT FOR NEW WINDOW (FIX ADDED)
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();

        for (String win : allWindows) {

            if (!win.equals(mainWindow)) {

                driver.switchTo().window(win);
                break;
            }
        }

        System.out.println("Switched to verifier window");

        // ✅ WAIT FOR PAGE LOAD FIRST (FIX ADDED)
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.tagName("body")));

        waitVisible(By.xpath("//body"));

        Thread.sleep(3000);

        // Scroll slowly (important for rendering)
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2)");
        Thread.sleep(1000);

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        delay();

        // ✅ SAFE FIND (WITH FALLBACK) (FIX ADDED)
        WebElement verifierBtn;

        try {
            verifierBtn = waitClickable(
                    By.xpath("//button[@id='verifier-button']"));
        } catch (Exception e) {

            System.out.println("Primary locator failed, trying fallback");

            verifierBtn = waitClickable(
                    By.xpath("//button[contains(text(),'Verify')]"));
        }

        jsClick(verifierBtn);

        Thread.sleep(5000);

        driver.close();

        driver.switchTo().window(mainWindow);

        delay();


        // ======================================
        // NAVIGATION FIX (IMPORTANT)
        // ======================================

        System.out.println("Navigating to Subjects tab");

        WebElement subjectsTab = waitClickable(
                By.xpath("//a[normalize-space()='Subjects']"));

        jsClick(subjectsTab);

        delay();

        System.out.println("Navigating to Credentials tab");

        WebElement credentialTab = waitClickable(
                By.xpath("//a[normalize-space()='Credentials']"));

        jsClick(credentialTab);

        delay();

        System.out.println("Opening Issued tab again");

        WebElement issuedTabAgain = waitClickable(
                By.xpath("//button[normalize-space()='Issued']"));

        jsClick(issuedTabAgain);

        Thread.sleep(3000);
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