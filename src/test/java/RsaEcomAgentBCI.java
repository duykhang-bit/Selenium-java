// Import các thư viện cần thiết

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomAgentBCI {
    WebDriver driver;
    WebDriverWait wait;

    public void runFlow() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); // Tự động cài đặt/chạy ChromeDriver phù hợp

        ChromeOptions options = new ChromeOptions(); // Cấu hình trình duyệt
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1); // Cho phép dùng mic
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream"); // Giả lập bật mic (không hiện popup)

        driver = new ChromeDriver(options); // Khởi tạo trình duyệt Chrome với cấu hình trên
        driver.manage().window().maximize(); // Phóng to trình duyệt
        wait = new WebDriverWait(driver, Duration.ofSeconds(50)); // Chờ tối đa 50s để load element

        driver.get("https://ci-rsa-ecom.frt.vn/"); // Mở URL hệ thống cần test

        // Nhập username
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("Thuct5");

        // Nhập password
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********"); // <-- THAY bằng mật khẩu thật

        // Click nút Đăng nhập
        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();

        // Chờ đến khi nút đăng nhập biến mất (đăng nhập thành công)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));

        // Click vào menu Call Center
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();

        // Click nút Login Call Center (đăng nhập Call Center nội bộ)
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();

        // Click nút READY để bắt đầu nhận cuộc gọi
        WebElement readybtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='ISREADY']")));
        readybtn.click();

        Thread.sleep(3000); // Chờ trạng thái READY ổn định

        // Click nút ANSWER để nghe máy
        WebElement answerbtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-ANSWER']")));
        answerbtn.click();

        System.out.println("Agent B đã answer cuộc gọi.");
        Thread.sleep(10000); // Giữ cuộc gọi trong 10s để mô phỏng đang nghe
    }
//    @BeforeMethod
//    @Test
//    public void Transfertocskh() throws InterruptedException{
//        // TRANSFER TO CSKH
//        WebElement TransferCSKH = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
//        TransferCSKH.click();
//        // nhập mã dể transfer tới CSKH 30015
//        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[@id='TRANSFERTO']")));
//        nhapsdtBox.sendKeys("30015");
//        // chọn button tham vấn
//        WebElement thamvan = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//button[@id='transferBtn']")));
//        thamvan.click();
//
//        System.out.println("Đang gọi Agent B nhận cuộc gọi...");
//        //login vô CSKH CALL IN
//
//        driver.get("https://ci-ob.fptshop.com.vn/Pharmacy/Agent");
//
//        WebElement insideBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.name("UserName")));
//        insideBox.sendKeys("33402");
//
//        WebElement passwordBox = driver.findElement(By.name("Password"));
//        passwordBox.sendKeys("********"); // <-- nhớ thay pass thật
//
//        WebElement loginBtn1 = driver.findElement(By.xpath("//button[@type='submit']"));
//        loginBtn1.click();
//        // Chọn CSKH
//        WebElement outboundCSKH = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//a[contains(text(),'Outbound CSKH')]")));
//
//        outboundCSKH.click();
//        // DĂNG NHẬP HỆ THÔNG
//        WebElement loginBtn3 = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[@id='log-in-mpt' and contains(@class,'logaccount-mpt')]")));
//        loginBtn3.click();

   // }
    @AfterMethod

    // Hàm đóng trình duyệt sau khi test (tùy chọn)
    public void teardown() {
        if (driver != null) {
            driver.quit(); // <-- Bỏ comment nếu muốn đóng trình duyệt
        }
    }
}
