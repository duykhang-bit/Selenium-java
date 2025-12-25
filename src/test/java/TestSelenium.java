import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestSelenium {

    private WebDriver driver;

    // Khởi tạo WebDriver trước mỗi test case
    @BeforeMethod
    public void setUp() {
        // Tự động tải ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Cấu hình ChromeOptions để fake User-Agent
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("--disable-blink-features=AutomationControlled"); // Ẩn Selenium khỏi bị phát hiện
        options.addArguments("--incognito"); // Mở ẩn danh tránh lưu cache

        // Mở Chrome với cấu hình trên
        driver = new ChromeDriver(options);
    }

    // Test case 1: Tìm kiếm trên Google
    @Test
    public void testGoogleSearch() throws InterruptedException {
        // Điều hướng đến Google
        driver.get("https://www.google.com");

        // Tìm ô tìm kiếm và nhập "Selenium WebDriver"
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");

        // Nhấn Enter
        searchBox.submit();

        // Đợi 3 giây
        Thread.sleep(3000);
    }

    // Test case 2: Đăng nhập vào Facebook
    @Test
    public void testFacebookLogin() throws InterruptedException {
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
    }

    // Đóng WebDriver sau mỗi test case
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
