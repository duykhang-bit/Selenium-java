// Import các thư viện cần thiết
import io.github.bonigarcia.wdm.WebDriverManager; // Quản lý driver cho trình duyệt
import org.openqa.selenium.By;                     // Dùng để tìm element theo các loại locator
import org.openqa.selenium.WebDriver;             // Interface chính để điều khiển trình duyệt
import org.openqa.selenium.WebElement;            // Đại diện cho 1 phần tử trên web
import org.openqa.selenium.chrome.ChromeDriver;   // Điều khiển trình duyệt Chrome
import org.openqa.selenium.chrome.ChromeOptions;  // Tùy chỉnh khởi tạo Chrome
import org.openqa.selenium.support.ui.ExpectedConditions; // Điều kiện chờ element
import org.openqa.selenium.support.ui.WebDriverWait;      // Chờ đợi element xuất hiện

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomAgentB {
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

    // Hàm đóng trình duyệt sau khi test (tùy chọn)
    public void teardown() {
        if (driver != null) {
            driver.quit(); // <-- Bỏ comment nếu muốn đóng trình duyệt
        }
    }
}
