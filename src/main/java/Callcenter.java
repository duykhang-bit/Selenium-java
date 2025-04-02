import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Callcenter {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testCallCenter() throws InterruptedException {
        driver.get("https://ci-rsa-ecom.frt.vn/");

        // Nhập tài khoản
        Thread.sleep(2000);
        WebElement userNameBox = driver.findElement(By.name("LoginInput.UserNameOrEmailAddress"));
        userNameBox.sendKeys("tinvt4");

        // Nhập mật khẩu
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("<AQWERT>");

        // Click nút đăng nhập
        WebElement loginButton = driver.findElement(By.id("kt_login_signin_submit"));
        loginButton.click();

        // Đợi sau khi đăng nhập thành công
        Thread.sleep(3000);

        // Click vào menu
        WebElement menu = driver.findElement(By.xpath("//div[@class='ant-menu-submenu-title']"));
        menu.click();

        Thread.sleep(1000); // Chờ menu mở ra

        // Click vào "Lịch sử cuộc gọi"
        WebElement historyCall = driver.findElement(By.xpath("//span[contains(text(), 'Lịch sử cuộc gọi')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", historyCall);

        Thread.sleep(3000); // Đợi để kiểm tra kết quả
        // 🛠 Kiểm tra nếu đã vào được trang "Lịch sử cuộc gọi"
        WebElement pageTitle = driver.findElement(By.xpath("//h1[contains(text(), 'Lịch sử cuộc gọi')]"));
        Assert.assertTrue(pageTitle.isDisplayed(), "Không tìm thấy tiêu đề trang Lịch sử cuộc gọi");
        System.out.println("✅ Đã vào trang Lịch sử cuộc gọi thành công!");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
    }


    }
}


