package listeners;

import base.BaseTest1;
import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        startTime.set(System.currentTimeMillis());
        
        // Lấy test method name
        String testName = result.getMethod().getMethodName();
        
        // Tạo ExtentTest với description
        ExtentTest extentTest = extent.createTest(testName);
        
        // Thêm description nếu có
        String description = result.getMethod().getDescription();
        if (description != null && !description.isEmpty()) {
            extentTest.info("Test Description: " + description);
        }
        
        // Thêm test class name
        extentTest.assignCategory(result.getTestClass().getName());
        
        // Thêm parameters nếu có
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            StringBuilder params = new StringBuilder("Test Parameters: ");
            for (int i = 0; i < parameters.length; i++) {
                params.append("Param").append(i + 1).append("=").append(parameters[i]);
                if (i < parameters.length - 1) {
                    params.append(", ");
                }
            }
            extentTest.info(params.toString());
        }
        
        // Thêm thông tin test
        extentTest.info("Test Started at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        extentTest.info("Test Class: " + result.getTestClass().getName());
        
        test.set(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        long duration = System.currentTimeMillis() - startTime.get();
        double durationSeconds = duration / 1000.0;
        
        ExtentTest extentTest = test.get();
        
        // Thêm thông tin chi tiết
        extentTest.pass("✅ TEST PASSED SUCCESSFULLY");
        extentTest.info("Execution Time: " + String.format("%.2f", durationSeconds) + " seconds");
        extentTest.info("Test Completed at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        
        // Thêm screenshot cho test thành công
        try {
            WebDriver driver = ((BaseTest1) result.getInstance()).getDriver();
            if (driver != null) {
                String path = ScreenshotUtil.capture(driver, result.getName() + "_SUCCESS");
                if (path != null) {
                    extentTest.pass("📸 Screenshot captured - Test Passed Successfully")
                        .addScreenCaptureFromPath(path);
                }
            }
        } catch (Exception e) {
            extentTest.warning("Could not capture screenshot: " + e.getMessage());
        }
        
        // Thêm log về URL hiện tại
        try {
            WebDriver driver = ((BaseTest1) result.getInstance()).getDriver();
            if (driver != null) {
                extentTest.info("Final URL: " + driver.getCurrentUrl());
                extentTest.info("Page Title: " + driver.getTitle());
            }
        } catch (Exception e) {
            // Ignore if driver is not available
        }
    }

    public void onTestFailure(ITestResult result) {
        long duration = System.currentTimeMillis() - startTime.get();
        double durationSeconds = duration / 1000.0;
        
        ExtentTest extentTest = test.get();
        
        // Thêm thông tin lỗi chi tiết
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            extentTest.fail("❌ TEST FAILED");
            extentTest.fail("Error Message: " + throwable.getMessage());
            
            // Thêm stack trace
            StringBuilder stackTrace = new StringBuilder("Stack Trace:\n");
            for (StackTraceElement element : throwable.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            extentTest.fail(stackTrace.toString());
        }
        
        extentTest.info("Execution Time: " + String.format("%.2f", durationSeconds) + " seconds");
        extentTest.info("Test Failed at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        
        // Thêm screenshot cho test thất bại
        try {
            WebDriver driver = ((BaseTest1) result.getInstance()).getDriver();
            if (driver != null) {
                String path = ScreenshotUtil.capture(driver, result.getName() + "_FAILURE");
                if (path != null) {
                    extentTest.fail("📸 Screenshot captured - Test Failed")
                        .addScreenCaptureFromPath(path);
                }
                
                // Thêm thông tin về URL và page title khi fail
                extentTest.info("URL at failure: " + driver.getCurrentUrl());
                extentTest.info("Page Title at failure: " + driver.getTitle());
            }
        } catch (Exception e) {
            extentTest.warning("Could not capture screenshot: " + e.getMessage());
        }
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.skip("⏭️ TEST SKIPPED");
        extentTest.skip("Skip Reason: " + (result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown"));
        extentTest.info("Test Skipped at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    public void onStart(ITestContext context) {
        // Thông tin về test suite
        extent.setSystemInfo("Test Suite Name", context.getSuite().getName());
        extent.setSystemInfo("Total Tests", String.valueOf(context.getSuite().getAllMethods().size()));
    }

    public void onFinish(ITestContext context) {
        // Thống kê tổng kết
        extent.setSystemInfo("Total Tests", String.valueOf(context.getAllTestMethods().length));
        extent.setSystemInfo("Passed Tests", String.valueOf(context.getPassedTests().size()));
        extent.setSystemInfo("Failed Tests", String.valueOf(context.getFailedTests().size()));
        extent.setSystemInfo("Skipped Tests", String.valueOf(context.getSkippedTests().size()));
        
        extent.flush();
    }
}
