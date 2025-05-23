package testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setDocumentTitle("Test Automation Report");
        spark.config().setReportName("Selenium Test Results");
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
        // Send report via email and Workplace
        utils.ReportSender.sendReport(extent);
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);
        driver.get("https://ci-rsa-ecom.frt.vn/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(90));
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                // Tạo thư mục screenshots nếu chưa tồn tại
                File screenshotDir = new File("test-output/screenshots");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs();
                }

                // Tạo tên file screenshot dựa trên tên test case và timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String testName = result.getName();
                String screenshotName = testName + "_" + timestamp + ".png";
                File screenshotFile = new File(screenshotDir, screenshotName);

                // Chụp màn hình
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(source, screenshotFile);

                // Thêm thông tin screenshot vào report
                test.fail("Test failed. Screenshot captured at: " + screenshotFile.getAbsolutePath());
                test.addScreenCaptureFromPath(screenshotFile.getAbsolutePath());
            } catch (Exception e) {
                test.fail("Failed to capture screenshot: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
