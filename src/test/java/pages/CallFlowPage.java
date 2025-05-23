package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CallFlowPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public CallFlowPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void performCallFlow() throws InterruptedException {
        WebElement sdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập số điện thoại']")));
        sdtBox.sendKeys("0835089254");
        WebElement call1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call1.click();
        WebElement HoldOncall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
        HoldOncall.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        WebElement Continuecall = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        Continuecall.click();
        // Ghi nội dung note
        WebElement Note = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='C2-NOTE-BTN']")));
        Note.click();
        WebElement Ghichu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//textarea[@placeholder='Nhập ghi chú']")));
        Ghichu.click();
        Ghichu.sendKeys("Automation");
        WebElement Luunote = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()='Xong']]")));
        Luunote.click();
        test.info("Call flow completed successfully");
    }
} 