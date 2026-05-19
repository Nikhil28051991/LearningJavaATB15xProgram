package com.everycred.base;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;

    // ======================================
    // SETUP — one browser per test class
    // (lets us split a flow into multiple @Test methods
    //  while keeping a single browser session and login.)
    // ======================================

    @BeforeClass(alwaysRun = true)
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
        org.openqa.selenium.support.ui.WebDriverWait w =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(30));

        w.ignoring(org.openqa.selenium.NoSuchWindowException.class);

        w.until(webDriver -> {
            try {
                Object state = ((JavascriptExecutor) webDriver).executeScript("return document.readyState");
                return "complete".equals(String.valueOf(state));
            } catch (org.openqa.selenium.NoSuchWindowException e) {
                try {
                    for (String handle : webDriver.getWindowHandles()) {
                        webDriver.switchTo().window(handle);
                        break;
                    }
                } catch (Exception ignored) {
                }
                return false;
            }
        });
    }

    // ======================================
    // TEARDOWN — runs once after all @Test methods in the class
    // ======================================

    @AfterClass(alwaysRun = true)
    public void tearDown(ITestContext context) {

        System.out.println("===== TEST END =====");

        int passed  = context.getPassedTests().size();
        int failed  = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        System.out.println("Suite summary so far → Passed: " + passed
                + " | Failed: " + failed + " | Skipped: " + skipped);

        // Browser only closes when closeBrowser=true is explicitly set in config.properties.
        // By default (false) the browser stays open after both PASS and FAIL for inspection.
        boolean forceClose = Boolean.parseBoolean(prop.getProperty("closeBrowser", "false"));

        if (forceClose && driver != null) {
            driver.quit();
            System.out.println("Browser closed (closeBrowser=true)");
        } else if (driver != null) {
            String status = (failed == 0) ? "PASSED" : "FAILED";
            System.out.println("Test class " + status + " — browser kept open. Close it manually when done.");
        }
    }
}