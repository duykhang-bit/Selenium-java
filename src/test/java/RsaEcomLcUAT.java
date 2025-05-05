import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomLcUAT {
    WebDriver driver;
    WebDriverWait wait;
    static ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
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

    @Test
    public void testLogin() {
        test = extent.createTest("testLogin");
        driver.get("https://uat-rsa-ecom.frt.vn/");
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("tinvt4");
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********");
        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));
        test.pass("Login successful");
    }

    @Test
    public void testLoginAndSystemLogin() {
        test = extent.createTest("testLoginAndSystemLogin");
        testLogin();
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();
        test.pass("System login successful");
    }

    @Test
    public void testCallFlow() throws InterruptedException {
        test = extent.createTest("testCallFlow");
        testLoginAndSystemLogin();
        WebElement sdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập số điện thoại']")));
        sdtBox.sendKeys("0835089254");
        WebElement call1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call1.click();
        WebElement HoldOncall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
        HoldOncall.click();
        Thread.sleep(6000);
        WebElement Continuecall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        Continuecall.click();
        test.pass("Call flow completed successfully");
    }

    @Test
    public void testTransferFlow() throws InterruptedException {
        test = extent.createTest("testTransferFlow");
        testCallFlow();
        WebElement TransferAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
        TransferAgentB.click();
        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='TRANSFERTO']")));
        nhapsdtBox.sendKeys("30007");
        WebElement thamvan = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        thamvan.click();

        // Gọi agent B xử lý cuộc gọi
        RsaEcomAgentBUAT agentB = new RsaEcomAgentBUAT();
        agentB.runFlow();
        agentB.teardown();

        WebElement transferB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        transferB.click();
        WebElement endcallAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-DROP']")));
        endcallAgentB.click();
        test.pass("Transfer flow completed successfully");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



//    public static void main(String[] args) {
//        RsaEcomLc app = new RsaEcomLc();
//        try {
//            app.runFlow();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            app.teardown();
//        }
//    }

//int mark = 85;
//
//if (mark >= 90) {
//        System.out.println("Xuất sắc");
//} else if (mark >= 75) {
//        System.out.println("Giỏi");
//} else if (mark >= 50) {
//        System.out.println("Trung bình");
//} else {
//        System.out.println("Yếu");
//}
////
//int mark = 85;
//if (mark >-90){
//    System.out.println("Suất sắc");
//        }
//else if (mark >= 75){
//    System.out.println("giỏi");
//
//        }
//else{
//    System.out.println("Yếu");
//                }

