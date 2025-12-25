package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Capture screenshot and save to test-output/screenshots directory
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @return Path to the saved screenshot (relative path from report location)
     */
    public static String capture(WebDriver driver, String testName) {
        try {
            if (driver == null) {
                return null;
            }
            
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            // Tạo thư mục nếu chưa tồn tại
            String screenshotDir = "test-output/screenshots";
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Tạo tên file với timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String path = screenshotDir + File.separator + fileName;
            
            File dest = new File(path);
            Files.copy(src.toPath(), dest.toPath());
            
            // Trả về relative path từ report location (test-output/)
            // Report ở test-output/ExtentReport.html, screenshot ở test-output/screenshots/
            // Dùng forward slash để đảm bảo hoạt động trên mọi OS
            return "screenshots/" + fileName;
        } catch (Exception e) {
            System.err.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Capture screenshot with custom filename
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param stepName Name of the step
     * @return Path to the saved screenshot (relative path from report location)
     */
    public static String capture(WebDriver driver, String testName, String stepName) {
        try {
            if (driver == null) {
                return null;
            }
            
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String screenshotDir = "test-output/screenshots";
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + stepName + "_" + timestamp + ".png";
            String path = screenshotDir + File.separator + fileName;
            
            File dest = new File(path);
            Files.copy(src.toPath(), dest.toPath());
            
            // Trả về relative path từ report location
            // Dùng forward slash để đảm bảo hoạt động trên mọi OS
            return "screenshots/" + fileName;
        } catch (Exception e) {
            System.err.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
