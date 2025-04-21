import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CallCenterCSKH {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    @Test

    public void runFlowCSKH() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--use-fake-ui-for-media-stream");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        driver.get("https://ci-ob.fptshop.com.vn/AccountAdmin/LogOn?url=~/Pharmacy/Agent");

        WebElement insideBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("UserName")));
        insideBox.sendKeys("33402");

        WebElement passwordBox = driver.findElement(By.name("Password"));
        passwordBox.sendKeys("********"); // <-- nhớ thay pass thật

        WebElement loginBtn1 = driver.findElement(By.xpath("//button[@type='submit']"));
        loginBtn1.click();
        // Chọn CSKH
        WebElement outboundCSKH = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Outbound CSKH')]")));

        outboundCSKH.click();
        // DĂNG NHẬP HỆ THÔNG
        WebElement loginBtn3 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='log-in-mpt' and contains(@class,'logaccount-mpt')]")));
        loginBtn3.click();


        // nhập sdt để call out
        WebElement inputsdtbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Số điện thoại']")));
        inputsdtbox.sendKeys("0835089254");
        //call
        WebElement call = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call.click();


//
//
//        WebElement readybtn = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[@id='ISREADY']")));
//        readybtn.click();
//
//        Thread.sleep(3000);
//
//        WebElement answerbtn = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[@id='CALL-ACTION-BTN-ANSWER']")));
//        answerbtn.click();
//
//        System.out.println("Agent B đã answer cuộc gọi.");
//        Thread.sleep(10000);
    }

    @AfterMethod

    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

    // Chạy riêng nếu muốn test class này độc lập
//    public static void main(String[] args) {
//        CallCenterCSKH app = new CallCenterCSKH();
//        try {
//            app.runFlow();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            app.teardown();
//        }
//    }
//}
