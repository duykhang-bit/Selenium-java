package testcases;

import base.BaseTest1;
import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import utils.ExtentManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    public void onTestSuccess(ITestResult result) {
        test.get().pass("TEST PASSED");
    }

    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());

        WebDriver driver =
                ((BaseTest1) result.getInstance()).getDriver();

        String path = ScreenshotUtil.capture(driver, result.getName());
        if (path != null) {
            test.get().addScreenCaptureFromPath(path);
        }
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
