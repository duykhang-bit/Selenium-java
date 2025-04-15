import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LoginCallcenter {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        // Cấu hình tắt popup microphone
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1); // 1 = allow, 2 = block
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    @Test
    public void LoginCallcenter() throws InterruptedException {
        driver.get("https://ci-rsa-ecom.frt.vn/");

        // Nhập tài khoản
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("tinvt4");

        // Nhập mật khẩu
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********"); // <-- Thay mật khẩu thật vào khi test

        // Click nút đăng nhập
        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();

        // Đợi nút đăng nhập biến mất
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));

        // Click vào menu Call Center
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();

        // Click vào nút đăng nhập trong menu Callcenter
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();

        // Nhập số điện thoại
        WebElement sdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập số điện thoại']")));
        sdtBox.sendKeys("0835089254");

        //Click button call
        WebElement call1 = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call1.click();
        //Thread.sleep(1000);
        //Click button end call
//        WebElement endcall = wait.until(ExpectedConditions.elementToBeClickable
//                (By.xpath("//button[@id='CALL-ACTION-BTN-DROP']")));
//        endcall.click();
        // HOLD ON CALL
        WebElement HoldOncall = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
        HoldOncall.click();
        // giữu máy 5 s
        Thread.sleep(6000);
        //Tiếp tục cuộc gọi
        WebElement Continuecall = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        Continuecall.click();
        //Chuyển cuộc gọi

        WebElement TransferAgentB = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
        TransferAgentB.click();


        // input transfer 30009
        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='TRANSFERTO']")));
        nhapsdtBox.sendKeys("30009");
        // Tham vấn
        WebElement thamvan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@id='transferBtn']")));
        thamvan.click();

// Gọi agent B thực hiện answer
        System.out.println("Đang gọi Agent B nhận cuộc gọi...");

        GetTransferCall agentB = new GetTransferCall();
        agentB.runAnswerFlow(); // Gọi flow Agent B
        Thread.sleep(30000);

// Tiếp tục sau khi Agent B answer
        System.out.println("Tiếp tục chuyển cuộc gọi cho Agent B...");

// Chuyển cuộc gọi cho Agent B
        WebElement transferB = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//dic[@class='ant-btn ant-btn-primary ant-btn-lg sc-aXZVg knHWgd my-4']")));
        transferB.click();

        //Thread.sleep(30000);\




    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
             //driver.quit(); // Bỏ comment nếu bạn muốn đóng browser sau khi chạy test
        }
    }
}
