package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgentBCIPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public AgentBCIPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void answerCall() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ISREADY"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("CALL-ACTION-BTN-ANSWER"))).click();
        test.info("Đã click ANSWER để nghe cuộc gọi.");
    }
} 