package testcases;

import base.BaseTest1;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.AgentBCIPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomAgentBCITest extends BaseTest1 {
    @Test
    public void runFlow() throws InterruptedException {
        test = extent.createTest("Agent BCI Answer Call Test");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("Hanhphm", "********");
        AgentBCIPage agentBCIPage = new AgentBCIPage(driver, wait, test);
        agentBCIPage.answerCall();
        Thread.sleep(10000);
        test.pass("Cuộc gọi được answer thành công.");
    }

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport_AgentB.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
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

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}

// ... giữ nguyên code của bạn
