
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.checkerframework.checker.units.qual.Prefix.milli;

public class Callcenter {
    public static void main(String[] args) {
        // Cài dặt ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://ci-rsa-ecom.frt.vn/");
            // Tìm ô nhập tài khoản và mật khẩU
            Thread.sleep(3000);
            WebElement UserNameBox = driver.findElement(By.name("LoginInput.UserNameOrEmailAddress"));
            UserNameBox.sendKeys("tinvt4");
            Thread.sleep(3000);
            // tÌM ô Nhập Password
            WebElement PasswordBox = driver.findElement(By.name("LoginInput.Password"));
            PasswordBox.sendKeys("<AQWERT>");
            Thread.sleep(3000);
            // Click nút Đăng nhập
            WebElement loginButton = driver.findElement(By.id("kt_login_signin_submit"));
            loginButton.click();
            // Đợi 5 giây để xem kết quả
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // đóng trình duyệt
            driver.quit();
        }
    }

    public void runTest() {

    }
}