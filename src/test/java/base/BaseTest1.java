package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseTest1 {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected ConfigReader config;

    /* =========================
     * DRIVER
     * ========================= */
    public WebDriver getDriver() {
        return driver;
    }

    /* =========================
     * REPORT
     * ========================= */
    @BeforeSuite
    public void setupReport() {
        config = ConfigReader.getInstance();

        ExtentSparkReporter spark =
                new ExtentSparkReporter(config.getReportPath());

        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Selenium Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    /* =========================
     * SETUP
     * ========================= */
    @BeforeMethod
    public void setup(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get(getBaseUrl());

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected String getBaseUrl() {
        return "https://ci-promotion.frt.vn/manager-promotion-list";
    }

    /* =========================
     * TEARDOWN
     * ========================= */
    @AfterMethod
    public void teardown(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            attachScreenshot("✅ Test PASSED");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot("❌ Test FAILED");
            if (result.getThrowable() != null) {
                test.fail(result.getThrowable());
            }
        } else {
            test.skip("⏭️ Test SKIPPED");
        }

        if (driver != null) {
          //  driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }

    /* =====================================================
     * SCREENSHOT
     * ===================================================== */
    protected void attachScreenshot(String message) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String dir = "test-output/screenshots";
            new File(dir).mkdirs();

            String fileName = System.currentTimeMillis() + ".png";
            File dest = new File(dir + "/" + fileName);
            FileUtils.copyFile(src, dest);

            test.info(message,
                    MediaEntityBuilder.createScreenCaptureFromPath(
                            "screenshots/" + fileName).build());

        } catch (Exception e) {
            test.warning("Cannot attach screenshot: " + e.getMessage());
        }
    }

    /* =====================================================
     * COMMON METHODS - ANT DESIGN SELECT 🔥
     * ===================================================== */

    /**
     * Mở dropdown Ant Design Select
     */
    protected void openAntDropdown(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.click();
    }

    /**
     * Click option Ant Select theo text (contains)
     */
    protected void clickAntOptionByContains(String text) {
        By option = By.xpath(
                "//div[contains(@class,'ant-select-item-option-content')" +
                " and contains(normalize-space(),'" + text + "')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    /**
     * Click option Ant Select theo title
     */
    protected void clickAntOptionByTitle(String title) {
        By option = By.xpath("//div[@title='" + title + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }
}
