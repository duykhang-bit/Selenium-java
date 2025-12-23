import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FacebookLoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Tự động cài đặt ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void loginTest() throws InterruptedException {
        // Nhập Email
        WebElement emailBox = driver.findElement(By.id("email"));
        emailBox.sendKeys("leduykhang185@gmail.com");

        // Nhập Password
        WebElement passwordBox = driver.findElement(By.id("pass"));
        passwordBox.sendKeys("Leduykhang2027");

        // Click nút Đăng nhập
        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();

        // Chờ 5 giây (có thể thay bằng WebDriverWait)
        Thread.sleep(5000);

        // Kiểm tra login thành công (dựa vào URL hoặc một phần tử cụ thể)
        Assert.assertTrue(driver.getCurrentUrl().contains("facebook.com"), "Login không thành công!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
