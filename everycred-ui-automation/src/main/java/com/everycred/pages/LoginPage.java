package com.everycred.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // =========================
    // LOCATORS
    // =========================
    private By emailField = By.id("email");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");

    private By dashboardElement = By.xpath("//a[contains(@href,'subjects')]");
    private By pageHeader = By.xpath("//header | //nav");

    private By loginError = By.xpath("//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'reCAPTCHA')]");

    // CAPTCHA detection only
    private By captchaFrame = By.xpath("//iframe[contains(@src,'captcha') or contains(@title,'captcha')]");
    private By captchaCheckbox = By.cssSelector("#recaptcha-anchor, .recaptcha-checkbox-checkmark");

    // =========================
    // CONSTRUCTOR
    // =========================
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // =========================
    // LOGIN METHOD (WITH RETRY)
    // =========================
    public void login(String username, String password) {

        int retry = 0;
        int maxRetry = 2;

        while (retry <= maxRetry) {

            try {
                System.out.println("===== LOGIN ATTEMPT: " + (retry + 1) + " =====");

                performLogin(username, password);

                System.out.println("✅ Login successful");
                return;

            } catch (RuntimeException e) {

                System.out.println("❌ Login attempt failed: " + e.getMessage());

                retry++;

                if (retry > maxRetry) {
                    throw new RuntimeException("❌ LOGIN FAILED AFTER RETRIES", e);
                }

                System.out.println("🔁 Retrying login...");
                refreshPage();
            }
        }
    }

    // =========================
    // CORE LOGIN FLOW
    // =========================
    private void performLogin(String username, String password) {

        waitForPageLoad();

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        enterText(emailField, username);
        enterText(passwordField, password);

        // ⚠ CAPTCHA must be handled BEFORE login click
        handleCaptchaIfPresent();

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        click(loginButton);

        validateLogin();
    }

    // =========================
    // CAPTCHA HANDLING (SAFE)
    // =========================
    private void handleCaptchaIfPresent() {

        if (isCaptchaPresent()) {

            System.out.println("⚠ CAPTCHA detected - waiting for manual solve...");

            try {
                WebDriverWait captchaWait = new WebDriverWait(driver, Duration.ofSeconds(300));

                captchaWait.until(d -> {

                    try {
                        boolean captchaGone = !isCaptchaPresent();
                        boolean loginEnabled = d.findElement(loginButton).isEnabled();
                        boolean stillLoginPage = d.getCurrentUrl().contains("login");

                        return captchaGone && loginEnabled && stillLoginPage;

                    } catch (Exception e) {
                        return false;
                    }
                });

                System.out.println("✅ CAPTCHA solved");

            } catch (TimeoutException e) {
                throw new RuntimeException("❌ CAPTCHA NOT SOLVED - TIMEOUT");
            }
        }
    }

    // =========================
    // CAPTCHA DETECTION
    // =========================
    private boolean isCaptchaPresent() {
        return !driver.findElements(captchaFrame).isEmpty()
                || !driver.findElements(captchaCheckbox).isEmpty()
                || driver.getPageSource().toLowerCase().contains("recaptcha");
    }

    // =========================
    // LOGIN VALIDATION
    // =========================
    private void validateLogin() {

        try {
            WebDriverWait validationWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            validationWait.until(d -> {

                try {
                    if (!d.getCurrentUrl().contains("login")) return true;
                    if (!d.findElements(dashboardElement).isEmpty()) return true;
                    if (!d.findElements(pageHeader).isEmpty()) return true;

                    if (!d.findElements(loginError).isEmpty()) {
                        throw new RuntimeException("❌ LOGIN FAILED: CAPTCHA or invalid credentials");
                    }

                    return false;

                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });

        } catch (TimeoutException e) {
            throw new RuntimeException("❌ LOGIN FAILED: Dashboard not loaded", e);
        }
    }

    // =========================
    // UTIL METHODS
    // =========================
    private void enterText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    private void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    private void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState")
                .equals("complete"));
    }
}