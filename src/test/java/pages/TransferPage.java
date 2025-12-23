package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferPage {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public TransferPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void performTransfer() throws InterruptedException {
        WebElement TransferAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
        TransferAgentB.click();
        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='TRANSFERTO']")));
        nhapsdtBox.sendKeys("30009");
        WebElement thamvan = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        thamvan.click();
        // Agent B actions would be handled in a separate test or page object
        WebElement transferB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        transferB.click();
        WebElement endcallAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-DROP']")));
        endcallAgentB.click();
        test.info("Transfer flow completed successfully");
    }
} 