package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import utils.ConfigReader;

import java.io.File;
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

    // ===== FIX QUAN TRỌNG =====
    public WebDriver getDriver() {
        return driver;
    }
    // =========================

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

    @BeforeMethod
    public void setup(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        
        // Thêm thông tin test
        test.info("🚀 Starting test: " + result.getMethod().getMethodName());
        test.info("Test Class: " + result.getTestClass().getName());
        test.info("Base URL: " + getBaseUrl());

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        test.info("🌐 Browser initialized: Chrome");
        test.info("📱 Navigating to: " + getBaseUrl());
        driver.get(getBaseUrl());
        test.info("✓ Page loaded successfully");
        test.info("📄 Page Title: " + driver.getTitle());
        test.info("🔗 Current URL: " + driver.getCurrentUrl());

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected String getBaseUrl() {
        return "https://ci-promotion.frt.vn/manager-promotion-list";
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        // Log kết quả test
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.pass("✅ Test completed successfully");
                // Capture screenshot cho test thành công
                try {
                    if (driver != null) {
                        File src = ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.FILE);

                        String screenshotDir = "test-output/screenshots";
                        File dir = new File(screenshotDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        
                        String fileName = result.getName() + "_SUCCESS_"
                                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                + ".png";
                        String fullPath = screenshotDir + File.separator + fileName;

                        FileUtils.copyFile(src, new File(fullPath));
                        // Dùng relative path từ report location (dùng forward slash)
                        String relativePath = "screenshots/" + fileName;
                        test.pass("📸 Screenshot captured - Test Passed Successfully")
                            .addScreenCaptureFromPath(relativePath);
                    }
                } catch (Exception e) {
                    test.warning("Could not capture screenshot: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ITestResult.FAILURE:
                test.fail("❌ Test failed");
                if (result.getThrowable() != null) {
                    test.fail("Error: " + result.getThrowable().getMessage());
                }
                // Capture screenshot cho test thất bại
                try {
                    if (driver != null) {
                        File src = ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.FILE);

                        String screenshotDir = "test-output/screenshots";
                        File dir = new File(screenshotDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        
                        String fileName = result.getName() + "_FAILURE_"
                                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                + ".png";
                        String fullPath = screenshotDir + File.separator + fileName;

                        FileUtils.copyFile(src, new File(fullPath));
                        // Dùng relative path từ report location (dùng forward slash)
                        String relativePath = "screenshots/" + fileName;
                        test.fail("📸 Screenshot captured - Test Failed")
                            .addScreenCaptureFromPath(relativePath);
                    }
                } catch (Exception e) {
                    test.warning("Could not capture screenshot: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ITestResult.SKIP:
                test.skip("⏭️ Test was skipped");
                break;
        }
        
        // Log thông tin cuối cùng
        if (driver != null) {
            try {
                test.info("🔗 Final URL: " + driver.getCurrentUrl());
                test.info("📄 Final Page Title: " + driver.getTitle());
            } catch (Exception e) {
                // Ignore if driver is closed
            }
        }
        
        test.info("🏁 Test execution completed");

        if (driver != null) {
            driver.quit();
            test.info("🔒 Browser closed");
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}
