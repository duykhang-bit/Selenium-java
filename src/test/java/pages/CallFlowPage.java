package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementNotInteractableException;

public class CallFlowPage {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public CallFlowPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void performCallFlow() throws InterruptedException {
        try {
            // Input phone number
            WebElement sdtBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='Nhập số điện thoại']")));
            wait.until(ExpectedConditions.elementToBeClickable(sdtBox));
            sdtBox.clear();
            sdtBox.sendKeys("0835089254");
            test.info("Đã nhập số điện thoại");

            // Click call button
            WebElement callBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
            callBtn.click();
            test.info("Đã click nút gọi");

            // Click hold button
            WebElement holdBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
            holdBtn.click();
            test.info("Đã click nút giữ máy");

            // Wait and click continue button
            Thread.sleep(1000);
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
            continueBtn.click();
            test.info("Đã click nút tiếp tục");

            // Click note button
            WebElement noteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='C2-NOTE-BTN']")));
            noteBtn.click();
            test.info("Đã click nút ghi chú");

            // Input note
            WebElement noteInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//textarea[@placeholder='Nhập ghi chú']")));
            noteInput.clear();
            noteInput.sendKeys("Automation");
            test.info("Đã nhập ghi chú");

        } catch (TimeoutException e) {
            test.fail("Timeout khi chờ element xuất hiện: " + e.getMessage());
            throw e;
        } catch (ElementNotInteractableException e) {
            test.fail("Không thể tương tác với element: " + e.getMessage());
            throw e;
        } catch (NoSuchElementException e) {
            test.fail("Không tìm thấy element: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Lỗi không xác định trong quy trình gọi: " + e.getMessage());
            throw e;
        }
    }
} 