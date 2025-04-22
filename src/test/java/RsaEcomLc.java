import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomLc {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    @Test

    public void runFlowLC() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        //truy cập web RSA ECOM lc
        driver.get("https://ci-rsa-ecom.frt.vn/");
        // nhập user name
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("tinvt4");
        // nhập  passwword
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********");
        // Submit đăng nhập
        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));
        //Chọn menu
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();
        // DĂNG NHẬP HỆ THÔNG
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();
        // Nhập sdt vô để call out
        WebElement sdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập số điện thoại']")));
        sdtBox.sendKeys("0835089254");
        // nhân button call
        WebElement call1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call1.click();
        // nhấn button hold
        WebElement HoldOncall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
        HoldOncall.click();

        Thread.sleep(6000); // giữ máy
        // Chọn button tiếp tục
        WebElement Continuecall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        Continuecall.click();
        // Chọn button transfer
        WebElement TransferAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
        TransferAgentB.click();
        // nhập mã dể transfer tới RSA ECOM 30009
        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='TRANSFERTO']")));
        nhapsdtBox.sendKeys("30009");
        // chọn button tham vấn
        WebElement thamvan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@id='transferBtn']")));
        thamvan.click();

        System.out.println("Đang gọi Agent B nhận cuộc gọi...");


        // GỌI QUA CallCenterVac:
// Tạo object từ class CallCenterVac, class này chứa luồng xử lý Agent B nhận cuộc gọi ở hệ thống khác (VD: CSKH Vaccine)
// Gọi hàm runFlow() để chạy quy trình Agent B login + trả lời cuộc gọi
// Sau đó gọi teardown() để đóng trình duyệt sau khi hoàn tất
//        CallCenterVac VAC = new CallCenterVac();
//        VAC.runFlowVAC();   // Agent B answer
//        VAC.teardown();
//
//
//        // GỌI QUA CSKH
//        CallCenterCSKH CSKH = new CallCenterCSKH();
//        CSKH.runFlowCSKH();   // Agent B answer
//        CSKH.teardown();


        // gọi qua class GetTransferCall để mở tab mới trả lời cuộc gọi AgenT b
        RsaEcomAgentB agentB = new RsaEcomAgentB();
        agentB.runFlow();   // Agent B answer
        agentB.teardown();


        System.out.println("Tiếp tục chuyển cuộc gọi cho Agent B...");
        // Chuyển cuộc gọi cho Agent B hẳn
        WebElement transferB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        transferB.click();
        //End CALL  AgentB
        WebElement endcallAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-DROP']")));
        endcallAgentB.click();


    }

    @AfterMethod

    public void teardown() {
        if (driver != null) {
           // driver.quit();
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

