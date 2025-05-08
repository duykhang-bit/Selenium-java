import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomAgentBCI {
    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    public void runFlow() {
        try {
            setupReport();     // tạo report
            setup();           // mở trình duyệt, cấu hình chrome
            test = extent.createTest("Agent BCI Answer Call Test");

            driver.get("https://ci-rsa-ecom.frt.vn/");
            loginToSystem("Hanhphm", "********"); // THAY bằng mật khẩu thật
            loginCallCenter();
            answerCall();

            Thread.sleep(10000); // đợi để mô phỏng thời gian gọi
            test.pass("Cuộc gọi được answer thành công.");
        } catch (Exception e) {
            String screenshotPath = takeScreenshot("Exception");
            test.fail("Flow gặp lỗi: " + e.getMessage()).addScreenCaptureFromPath(screenshotPath);
        } finally {
            teardown();
            flushReport();
        }
    }


    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport_AgentB.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    public void loginToSystem(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LoginInput.UserNameOrEmailAddress"))).sendKeys(username);
        driver.findElement(By.name("LoginInput.Password")).sendKeys(password);
        driver.findElement(By.id("kt_login_signin_submit")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));
        test.info("Đăng nhập hệ thống thành công.");
    }

    public void loginCallCenter() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-menu-submenu-title']"))).click();
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ISREADY"))).click();
        test.info("Đăng nhập Call Center thành công.");
    }

    public void answerCall() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("CALL-ACTION-BTN-ANSWER"))).click();
        test.info("Đã click ANSWER để nghe cuộc gọi.");
    }

    public String takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String path = "test-output/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest.getAbsolutePath();
    }

    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
