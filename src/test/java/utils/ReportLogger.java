package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Utility class để log chi tiết vào ExtentReport
 */
public class ReportLogger {
    
    private ExtentTest test;
    private WebDriver driver;
    
    public ReportLogger(ExtentTest test, WebDriver driver) {
        this.test = test;
        this.driver = driver;
    }
    
    /**
     * Log thông tin bước test
     */
    public void logStep(String stepDescription) {
        test.log(Status.INFO, "📝 " + stepDescription);
    }
    
    /**
     * Log thông tin bước test với screenshot
     */
    public void logStepWithScreenshot(String stepDescription, String testName, String stepName) {
        test.log(Status.INFO, "📝 " + stepDescription);
        try {
            String screenshotPath = ScreenshotUtil.capture(driver, testName, stepName);
            if (screenshotPath != null) {
                test.log(Status.INFO, "Screenshot captured").addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            test.warning("Could not capture screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Log khi click vào element
     */
    public void logClick(String elementDescription) {
        test.log(Status.INFO, "🖱️ Clicked on: " + elementDescription);
    }
    
    /**
     * Log khi nhập text
     */
    public void logInput(String fieldDescription, String value) {
        test.log(Status.INFO, "⌨️ Entered text in " + fieldDescription + ": " + 
                (value.length() > 20 ? value.substring(0, 20) + "..." : value));
    }
    
    /**
     * Log khi verify/assert
     */
    public void logVerify(String verificationDescription, boolean passed) {
        if (passed) {
            test.log(Status.PASS, "✅ Verified: " + verificationDescription);
        } else {
            test.log(Status.FAIL, "❌ Verification failed: " + verificationDescription);
        }
    }
    
    /**
     * Log thông tin về page
     */
    public void logPageInfo() {
        try {
            if (driver != null) {
                test.log(Status.INFO, "🌐 Current URL: " + driver.getCurrentUrl());
                test.log(Status.INFO, "📄 Page Title: " + driver.getTitle());
            }
        } catch (Exception e) {
            test.warning("Could not get page info: " + e.getMessage());
        }
    }
    
    /**
     * Log warning
     */
    public void logWarning(String warningMessage) {
        test.log(Status.WARNING, "⚠️ " + warningMessage);
    }
    
    /**
     * Log error
     */
    public void logError(String errorMessage) {
        test.log(Status.FAIL, "❌ " + errorMessage);
    }
    
    /**
     * Log success
     */
    public void logSuccess(String successMessage) {
        test.log(Status.PASS, "✅ " + successMessage);
    }
    
    /**
     * Log khi chờ element xuất hiện
     */
    public void logWait(String elementDescription) {
        test.log(Status.INFO, "⏳ Waiting for: " + elementDescription);
    }
    
    /**
     * Log khi element được tìm thấy
     */
    public void logElementFound(String elementDescription) {
        test.log(Status.INFO, "✓ Element found: " + elementDescription);
    }
    
    /**
     * Log thông tin về WebElement
     */
    public void logElementInfo(WebElement element, String description) {
        try {
            if (element != null) {
                test.log(Status.INFO, "📋 " + description + 
                    " - Tag: " + element.getTagName() + 
                    ", Displayed: " + element.isDisplayed() + 
                    ", Enabled: " + element.isEnabled());
            }
        } catch (Exception e) {
            test.warning("Could not get element info: " + e.getMessage());
        }
    }
}

