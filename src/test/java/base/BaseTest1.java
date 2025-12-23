package base;

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
import utils.ConfigReader;
import utils.ReportSender;

import java.io.File;
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
    protected ConfigReader config;

    @BeforeSuite
    public void setupReport() {
        config = ConfigReader.getInstance();
        
        String reportPath = config.getReportPath();
        String reportTitle = config.getProperty("report.title", "Test Automation Report");
        String reportName = config.getProperty("report.name", "Selenium Test Results");
        String theme = config.getProperty("report.theme", "DARK");
        
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle(reportTitle);
        spark.config().setReportName(reportName);
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.valueOf(theme));
        
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Browser", config.getBrowser().toUpperCase());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
        // Send report via email and Workplace
        utils.ReportSender.sendReport(extent);
    }

    /**
     * Override this method to provide custom base URL
     * Default is CI environment from config
     */
    protected String getBaseUrl() {
        if (config == null) {
            config = ConfigReader.getInstance();
        }
        return config.getCiUrl();
    }

    /**
     * Override this method to provide custom wait timeout in seconds
     * Default from config file
     */
    protected long getWaitTimeout() {
        if (config == null) {
            config = ConfigReader.getInstance();
        }
        return config.getExplicitWait();
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        if (config == null) {
            config = ConfigReader.getInstance();
        }
        
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        
        // Setup ChromeDriver
        WebDriverManager.chromedriver().clearDriverCache().setup();
        
        // Configure Chrome Options from config
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        
        if (config.getBooleanProperty("chrome.media.stream.mic", true)) {
            prefs.put("profile.default_content_setting_values.media_stream_mic", 
                    config.getIntProperty("chrome.media.stream.mic", 1));
        }
        options.setExperimentalOption("prefs", prefs);
        
        // Add Chrome arguments from config
        if (config.getBooleanProperty("chrome.use.fake.ui.for.media.stream", true)) {
            options.addArguments("--use-fake-ui-for-media-stream");
        }
        if (config.getBooleanProperty("chrome.remote.allow.origins", true)) {
            options.addArguments("--remote-allow-origins=*");
        }
        if (config.getBooleanProperty("chrome.no.sandbox", true)) {
            options.addArguments("--no-sandbox");
        }
        if (config.getBooleanProperty("chrome.disable.dev.shm.usage", true)) {
            options.addArguments("--disable-dev-shm-usage");
        }
        if (config.getBooleanProperty("chrome.disable.gpu", true)) {
            options.addArguments("--disable-gpu");
        }
        if (config.getBooleanProperty("chrome.disable.extensions", true)) {
            options.addArguments("--disable-extensions");
        }
        if (config.getBooleanProperty("chrome.disable.popup.blocking", true)) {
            options.addArguments("--disable-popup-blocking");
        }
        if (config.getBooleanProperty("chrome.start.maximized", true)) {
            options.addArguments("--start-maximized");
        }
        if (config.getBooleanProperty("chrome.disable.blink.features", true)) {
            options.addArguments("--disable-blink-features=AutomationControlled");
        }
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        // Initialize driver
        driver = new ChromeDriver(options);
        driver.get(getBaseUrl());
        driver.manage().window().maximize();
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        
        // Set explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(getWaitTimeout()));
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if (config == null) {
            config = ConfigReader.getInstance();
        }
        
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                // Tạo thư mục screenshots nếu chưa tồn tại
                String screenshotPath = config.getScreenshotPath();
                File screenshotDir = new File(screenshotPath);
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
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed.");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped.");
        }

        if (driver != null) {
            driver.quit();
        }
    }
}

