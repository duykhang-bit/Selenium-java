import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class TestNhapKhacXuatKhac {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Bỏ comment nếu muốn chạy không giao diện
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ✅ Test login và upload file trong 1 test
    @Test
    public void testLoginAndImportFile() {
        // 👉 Truy cập trang đăng nhập
        driver.get("https://ci-eho-web.frt.vn/");

        // 👉 Nhập tài khoản
        WebElement taikhoanbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@formcontrolname='employee_code']")));
        taikhoanbox.sendKeys("Ngocdtm3");

        // 👉 Nhập mật khẩu
        WebElement passwordbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@formcontrolname='password']")));
        passwordbox.sendKeys("1234567");

        // 👉 Click đăng nhập
        WebElement dangnhapbox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='kt_login_signin_submit']")));
        dangnhapbox.click();

        // 👉 Chọn menu "Nhập khác xuất khác"
        WebElement menuNhapXuatKhac = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Nhập khác xuất khác']/parent::li")));
        menuNhapXuatKhac.click();

        // 👉 Click nút Import
        WebElement importButton = driver.findElement(By.xpath("//button[span[text()='Import']]"));
        importButton.click();

//        WebElement importBtn = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(., 'Import') and not(@disabled)]")));



        // 👉 Scroll tới nút Import nếu bị khuất
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", importButton);
        importButton.click();

        // 👉 Lấy đường dẫn tuyệt đối tới file Excel
        File file = new File("src/test/resources/import-template.xlsx");
        String fullPath = file.getAbsolutePath();

        // 👉 Gửi file vào input
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@type='file']")));
        fileInput.sendKeys(fullPath);

        // 👉 Nếu có nút xác nhận thì thêm đoạn này:
        // WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
        //     By.xpath("//button[span[contains(text(),'Xác nhận')]]")));
        // confirmBtn.click();
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
          //  driver.quit();
        }
    }
}
