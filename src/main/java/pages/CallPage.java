package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CallPage {
    WebDriver driver;
    WebDriverWait wait;

    public CallPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
    }

    public void loginCallCenter(ExtentTest test) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-menu-submenu-title']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("A-SIGNIN-BTN"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ISREADY"))).click();
        test.info("Đăng nhập Call Center thành công.");
    }

    public void answerCall(ExtentTest test) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("CALL-ACTION-BTN-ANSWER"))).click();
        test.info("Đã click ANSWER để nghe cuộc gọi.");
    }
}
