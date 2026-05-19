package com.everycred.pages;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssueCredentialPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public IssueCredentialPage(WebDriver driver) {

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

        if (!driver.getCurrentUrl().contains("/credentials")) {

            System.out.println("Navigating to Credentials tab manually");

            WebElement credentialTab = waitClickable(
                    By.xpath("//a[normalize-space()='Credentials']"));

            jsClick(credentialTab);

            wait.until(ExpectedConditions.urlContains("/credentials"));
        }

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
        delay();

        // Click Credentials
        WebElement credentialsTab = waitClickable(
                By.xpath("//a[normalize-space()='Credentials']"));

        jsClick(credentialsTab);

        wait.until(ExpectedConditions.urlContains("/credentials"));

        waitVisible(By.xpath("//tbody"));

        // 🔥 FIX: SWITCH TO ISSUED TAB
        System.out.println("Switching to Issued tab");

        WebElement issuedTab = waitClickable(
                By.xpath("//button[normalize-space()='Issued']"));

        jsClick(issuedTab);

        // wait for issued data or empty state
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'No data')]"))
        ));

        System.out.println("Now on Issued Credentials page");
    }

    // ======================================
    // ISSUE CREDENTIAL FLOW
    // ======================================

    public void issueCredentialFlow() throws InterruptedException {

        System.out.println("Starting Issue Credential Flow");

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL = " + currentUrl);

        // =============================
        // WAIT FOR ADD CREDENTIAL TABLE
        // =============================

        System.out.println("Waiting for Add Credential table");

        waitVisible(By.xpath("//tbody"));

        delay();

        // =============================
        // SELECT CHECKBOX
        // =============================

        System.out.println("Selecting record checkbox");

        WebElement checkbox = null;

        int attempts = 0;

        while (attempts < 10) {

            try {

                checkbox = wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//tbody/tr[1]//input[@type='checkbox']")));

                if (checkbox.isDisplayed()) {
                    break;
                }

            } catch (Exception e) {

                delay();
            }

            attempts++;
        }

        if (checkbox == null) {

            throw new RuntimeException("Checkbox not found in Issue Credential table!");
        }

        scrollIntoView(checkbox);

        delay();

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkbox);

        delay();

        // =============================
        // CLICK ISSUE BUTTON
        // =============================

        WebElement issueBtn = waitClickable(
                By.xpath("//span[normalize-space()='Issue Credentials']"));

        jsClick(issueBtn);

        delay();

        // =============================
        // POPUP OPEN WAIT
        // =============================

        waitVisible(By.xpath("//input[@placeholder='Valid From']"));

        delay();


/*
        // =============================
        // SEND VIA EMAIL (NO RADIO BUTTON)
        // =============================


        System.out.println("Closing popup");

        WebElement cancelBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Cancel']")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", cancelBtn);

        delay();

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", cancelBtn);

        // NEW FIX: wait until popup is fully gone before proceeding
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//input[@placeholder='Valid From']")));

        delay();

   System.out.println("Popup closed");
   
    }
    
*/

     // =============================
        // CLICK VALID FROM
        // =============================

        WebElement validFrom = waitClickable(
                By.xpath("//input[@placeholder='Valid From']"));

        jsClick(validFrom);

        delay();

        // =============================
        // SELECT CURRENT DATE
        // =============================

        System.out.println("Selecting current date");

        ZonedDateTime istDate =
                ZonedDateTime.now(java.time.ZoneId.of("Asia/Kolkata"));

        int today = istDate.getDayOfMonth();

        WebElement todayDate = waitClickable(
                By.xpath("//td[not(contains(@class,'other-month'))]//span[text()='" + today + "']"));

        scrollIntoView(todayDate);

        delay();

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", todayDate);

        delay();

        System.out.println("Validity date selected");

        // =============================
        // CLICK ISSUE BUTTON IN POPUP
        // =============================

        System.out.println("Click Issue button in popup");

        WebElement issuePopup = waitClickable(
                By.xpath("//button[.//span[normalize-space()='Issue']]"));

        jsClick(issuePopup);

        delay();

        System.out.println("Waiting popup to close");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//app-issue-credentials-dialog")));

        Thread.sleep(2000);

        // =============================
        // REFRESH PAGE
        // =============================

        System.out.println("Refreshing page to get latest issued credential");

        driver.navigate().refresh();

        waitVisible(By.xpath("//body"));

        Thread.sleep(2000);

        // =============================
        // 🔥 FINAL FIX APPLIED HERE
        // =============================

        navigateToCredentialListPage();

        // Ensure table loads again
        waitVisible(By.xpath("//tbody/tr"));

        System.out.println("Credential issued and page stabilized");
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