package com.everycred.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class UploadSpreadsheetPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public UploadSpreadsheetPage(WebDriver driver) {
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

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ======================================
    // SAFE WAIT (🔥 FIX FOR YOUR FAILURE)
    // ======================================

    private void waitForUploadResult() {

        System.out.println("Waiting for upload result (dynamic)");

        wait.until(ExpectedConditions.or(

                // Case 1: Table loaded
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")),

                // Case 2: In-process text (any variation)
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'process') or contains(text(),'Process')]")),

                // Case 3: Success / uploaded text
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'uploaded') or contains(text(),'Uploaded')]"))
        ));

        System.out.println("Upload result detected");
    }

    // ======================================
    // MAIN FLOW
    // ======================================

    public void uploadSpreadsheetFlow() throws InterruptedException {

        System.out.println("Starting Upload Spreadsheet Flow");

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

        Thread.sleep(2000);

        // ======================================
        // OPEN SUBJECTS TAB
        // ======================================

        System.out.println("Opening Subjects Tab");

        WebElement subjectsTab = waitClickable(
                By.xpath("//a[normalize-space()='Subjects']"));

        jsClick(subjectsTab);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody//tr")));

        delay();

        // ======================================
        // CLICK ADD RECORD
        // ======================================

        System.out.println("Clicking Add Record button");

        WebElement addRecordButton = waitClickable(
                By.xpath("//tbody/tr[1]//button[contains(.,'Add Record')]"));

        jsClick(addRecordButton);

        delay();

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

            if (driver.getCurrentUrl().contains("/credentials/add")) {
                System.out.println("✅ Already on Add Credential Page");
            } else {
                throw new RuntimeException("❌ Navigation failed");
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

            Thread.sleep(3000);
        }

        // ======================================
        // WAIT FOR ADD PAGE
        // ======================================

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Add New Record']")),
                ExpectedConditions.urlContains("/credentials/add")
        ));

        waitVisible(By.xpath("//button[.//span[normalize-space()='Upload Spreadsheet']]"));

        System.out.println("Add Credential Page Loaded");

        // ======================================
        // CLICK UPLOAD BUTTON
        // ======================================

        WebElement uploadBtn = waitClickable(
                By.xpath("//button[.//span[normalize-space()='Upload Spreadsheet']]"));

        jsClick(uploadBtn);

        Thread.sleep(1000);

        // ======================================
        // HANDLE POPUP
        // ======================================

        WebElement popupBtn = waitClickable(
                By.xpath("//button[contains(text(),'Upload Spreadsheet')]"));

        jsClick(popupBtn);

        Thread.sleep(1000);

        // ======================================
        // FILE UPLOAD
        // ======================================

        System.out.println("Uploading file");

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].style.display='block';", fileInput);

        fileInput.sendKeys("C:\\Users\\Nikhil Sonawane\\Downloads\\Employees ID Cards-template.xlsx");

        System.out.println("✅ File uploaded");

        Thread.sleep(2000);

        // ======================================
        // 🔥 FIXED WAIT HERE
        // ======================================

        waitForUploadResult();

        // ======================================
        // VERIFY TABLE
        // ======================================

        waitVisible(By.xpath("//tbody//tr"));

        System.out.println("✅ Upload flow completed successfully");
    }

    // ======================================
    // JS CLICK
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

                System.out.println("Retrying click...");
                attempts++;
                delay();
            }
        }

        throw new RuntimeException("Click failed");
    }
}