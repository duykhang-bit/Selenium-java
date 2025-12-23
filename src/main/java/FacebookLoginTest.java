
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FacebookLoginTest {
    public static void main(String[] args) {
        // Cài đặt ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Mở trang Facebook
            driver.get("https://www.facebook.com/");

            // Tìm ô nhập Email và nhập tài khoản
            WebElement emailBox = driver.findElement(By.id("email"));
            emailBox.sendKeys("leduykhang185@gmail.com");

            // Tìm ô nhập Password và nhập mật khẩu
            WebElement passwordBox = driver.findElement(By.id("pass"));
            passwordBox.sendKeys("Leduykhang2027");

            // Click nút Đăng nhập
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();

            // Đợi 5 giây để xem kết quả
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    public void runTest() {
    }
}
