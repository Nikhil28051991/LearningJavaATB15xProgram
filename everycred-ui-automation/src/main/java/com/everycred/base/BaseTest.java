package com.everycred.base;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;

    // ======================================
    // SETUP
    // ======================================

    @BeforeMethod
    public void setUp() throws Exception {

        System.out.println("===== TEST START =====");

        loadProperties();
        initializeDriver();
        launchApplication();
        waitForPageLoad();
    }

    // ======================================
    // LOAD CONFIG
    // ======================================

    private void loadProperties() throws Exception {

        prop = new Properties();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("config/config.properties")) {

            if (is == null) {
                throw new RuntimeException("config/config.properties not found in classpath");
            }

            prop.load(is);
        }
    }

    // ======================================
    // DRIVER INIT (SCALABLE)
    // ======================================

    private void initializeDriver() {

        String browser = prop.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();

        // ❌ No implicit wait (best practice)
    }

    // ======================================
    // OPEN URL
    // ======================================

    private void launchApplication() {

        String url = prop.getProperty("url");

        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException("URL is missing in config.properties");
        }

        driver.get(url);

        System.out.println("Browser launched → " + url);
    }

    // ======================================
    // WAIT FOR PAGE LOAD
    // ======================================

    protected void waitForPageLoad() {

        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(30))
                .until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }

    // ======================================
    // TEARDOWN
    // ======================================

    @AfterMethod
    public void tearDown() {

        System.out.println("===== TEST END =====");

        boolean closeBrowser =
                Boolean.parseBoolean(prop.getProperty("closeBrowser", "false"));

        if (closeBrowser && driver != null) {

            // driver.quit();   // ❌ intentionally commented to keep browser open
            System.out.println("Browser quit skipped (debug mode)");

        } else {

            System.out.println("Browser kept open for debugging");
        }
    }
}