// Import các thư viện cần thiết

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class CallCenterVac {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeMethod
    @Test

    public void runFlowVAC() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); // Tự động cài đặt/chạy ChromeDriver phù hợp

        ChromeOptions options = new ChromeOptions(); // Cấu hình trình duyệt
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1); // Cho phép dùng mic
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream"); // Giả lập bật mic (không hiện popup)

        driver = new ChromeDriver(options); // Khởi tạo trình duyệt Chrome với cấu hình trên
        driver.manage().window().maximize(); // Phóng to trình duyệt
        wait = new WebDriverWait(driver, Duration.ofSeconds(50)); // Chờ tối đa 50s để load element

        driver.get("https://ci-vaccine-ecom.frt.vn/call-center"); // Mở URL hệ thống cần test

        // Nhập username
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("dungttt13");

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
                By.xpath("//span[text()='Đăng nhập (F1)']")));
        loginBtn2.click();

        // Click nút READY để bắt đầu nhận cuộc gọi
        WebElement readybtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='ant-switch-inner']")));
        readybtn.click();

        Thread.sleep(3000); // Chờ trạng thái READY ổn định

        // Click nút ANSWER để nghe máy
        WebElement answerbtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-ANSWER']")));
        answerbtn.click();

        System.out.println("Agent B đã answer cuộc gọi.");
        Thread.sleep(10000); // Giữ cuộc gọi trong 10s để mô phỏng đang nghe
    }
    @AfterMethod

    // Hàm đóng trình duyệt sau khi test (tùy chọn)
    public void teardown() {
        if (driver != null) {
            // driver.quit(); // <-- Bỏ comment nếu muốn đóng trình duyệt
        }
    }
}
