import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class GetTransferCall {
    WebDriver driver;
    WebDriverWait wait;

    public void runFlow() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        driver.get("https://ci-rsa-ecom.frt.vn/");

        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("Thuct5");

        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********"); // thay bằng pass thật

        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));

        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();

        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();

        WebElement readybtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='ISREADY']")));
        readybtn.click();

        Thread.sleep(3000); // cho READY ổn

        WebElement answerbtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-ANSWER']")));
        answerbtn.click();

        System.out.println("Agent B đã answer cuộc gọi.");
        Thread.sleep(10000); // nghe máy 10s
    }

    public void teardown() {
        if (driver != null) {
           // driver.quit();
        }
    }
}
