import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Callcenter {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testCallCenter() {
        driver.get("https://ci-rsa-ecom.frt.vn/");

        // Nhập tài khoản
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("tinvt4");

        // Nhập mật khẩu
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********");  // <-- Bạn thay lại mật khẩu thật khi test

        // Click nút đăng nhập
        WebElement loginButton = driver.findElement(By.id("kt_login_signin_submit"));
        loginButton.click();

        // Đợi sau khi đăng nhập thành công
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));

        // Click vào menu
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();

        driver.get("https://ci-rsa-ecom.frt.vn/call-center/call-history");
         //Xác minh text "Danh sách cuộc gọi" có hiển thị tại màn hình danh sách cuộc gọi
        WebElement title = driver.findElement(By.xpath("//div[contains(@class, 'CAL02-head-title')]"));
        Assert.assertEquals(title.getText(), "Danh sách cuộc gọi");

        assert title.isDisplayed();
        System.out.println("✅ Xác nhận hiển thị: 'Danh sách cuộc gọi'");


//
//// Đợi menu xổ xuống và click vào "Lịch sử cuộc gọi"
//        WebElement callHistoryOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//span[text()='Lịch sử cuộc gọi']")
//        ));
//        callHistoryOption.click();


//        // Click vào "Lịch sử cuộc gọi"
//        WebElement historyCall = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Lịch sử cuộc gọi')]")));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", historyCall);
//
//        // Xác nhận tiêu đề trang "Lịch sử cuộc gọi"
//        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Lịch sử cuộc gọi')]")));
//        assert pageTitle.isDisplayed();
//        System.out.println("✅ Đã vào trang Lịch sử cuộc gọi thành công!");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
           // driver.quit();  // Đóng trình duyệt sau khi test xong
        }
    }
}
