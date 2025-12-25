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
import java.io.FileNotFoundException;
import java.time.Duration;

public class TestNhapKhacXuatKhac {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Bỏ comment nếu muốn chạy không giao diện
        options.addArguments("--disable-notifications"); // Disable notifications
        options.addArguments("--start-maximized"); // Start maximized
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    // ✅ Test login và upload file trong 1 test
    @Test
    public void testLoginAndImportFile() throws FileNotFoundException, InterruptedException {
        try {
            // 👉 Truy cập trang đăng nhập
            System.out.println("Đang truy cập trang đăng nhập...");
            driver.get("https://ci-eho-web.frt.vn/");
            System.out.println("URL hiện tại: " + driver.getCurrentUrl());

            // Chụp màn hình sau khi load trang
            takeScreenshot("after_page_load");

            // 👉 Nhập tài khoản
            System.out.println("Đang tìm ô nhập tài khoản...");
            WebElement taikhoanbox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@formcontrolname='employee_code']")));
            taikhoanbox.clear();
            taikhoanbox.sendKeys("Ngocdtm3");
            System.out.println("Đã nhập tài khoản");

            // 👉 Nhập mật khẩu
            System.out.println("Đang tìm ô nhập mật khẩu...");
            WebElement passwordbox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@formcontrolname='password']")));
            passwordbox.clear();
            passwordbox.sendKeys("1234567");
            System.out.println("Đã nhập mật khẩu");

            // 👉 Click đăng nhập
            System.out.println("Đang tìm nút đăng nhập...");
            WebElement dangnhapbox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='kt_login_signin_submit']")));
            dangnhapbox.click();
            System.out.println("Đã click nút đăng nhập");

            // Thêm chờ sau khi đăng nhập thành công
            System.out.println("Đang chờ header xuất hiện sau đăng nhập...");
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//header[contains(@class, 'ant-layout-header')]")));
            System.out.println("Đã chờ header xuất hiện sau đăng nhập.");

            // Chụp màn hình sau khi đăng nhập
            takeScreenshot("after_login");

            // 👉 Chọn menu "Nhập khác xuất khác"
            System.out.println("Đang tìm menu Nhập khác xuất khác...");
            WebElement menuNhapXuatKhac = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(text(),'Nhập khác xuất khác')]/parent::li")));
            menuNhapXuatKhac.click();
            System.out.println("Đã click menu Nhập khác xuất khác");

            // Chụp màn hình sau khi click menu
            takeScreenshot("after_menu_click");

            // Chờ cho một element đặc trưng của trang Nhập khác xuất khác xuất hiện
            System.out.println("Bắt đầu chờ ô tìm kiếm Mã phiếu...");
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@placeholder,'Tìm kiếm')]")));
                System.out.println("Đã chờ ô tìm kiếm xuất hiện và có thể click.");
            } catch (TimeoutException e) {
                System.out.println("Không tìm thấy ô tìm kiếm, chụp màn hình để debug...");
                takeScreenshot("search_input_not_found");
                throw e;
            }

            // Đợi và xử lý thông báo nếu có
            try {
                WebElement notification = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'ant-notification')]")));
                System.out.println("Tìm thấy thông báo: " + notification.getText());
                wait.until(ExpectedConditions.invisibilityOf(notification));
                System.out.println("Đã xử lý thông báo.");
            } catch (TimeoutException e) {
                System.out.println("Không có thông báo nào xuất hiện sau khi tải trang.");
            }

            // 👉 Click nút Import
            System.out.println("Bắt đầu chờ nút Import...");
            try {
                WebElement importButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[contains(text(),'Import')]]"))); 
                System.out.println("Đã chờ nút Import xuất hiện và có thể click.");
                
                // Scroll tới nút Import nếu bị khuất và click bằng JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", importButton);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", importButton);
                System.out.println("Đã click nút Import.");
            } catch (TimeoutException e) {
                System.out.println("Không tìm thấy nút Import, chụp màn hình để debug...");
                takeScreenshot("import_button_not_found");
                throw e;
            }

            // Chụp màn hình sau khi click Import
            takeScreenshot("after_import_click");

            // 👉 Đợi và tìm input file trong popup
            System.out.println("Bắt đầu chờ input file popup...");
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='file']")));
            System.out.println("Đã tìm thấy input file popup.");
            
            // 👉 Lấy đường dẫn tuyệt đối tới file Excel
            File file = new File("src/test/resources/import-template.xlsx");
            if (!file.exists()) {
                throw new FileNotFoundException("Không tìm thấy file Excel tại: " + file.getAbsolutePath());
            }
            String fullPath = file.getAbsolutePath();
            System.out.println("Đường dẫn file: " + fullPath);

            // 👉 Gửi file vào input
            System.out.println("Trước khi sendKeys, value input file: " + fileInput.getAttribute("value"));
            fileInput.sendKeys(fullPath);
            System.out.println("Sau khi sendKeys, value input file: " + fileInput.getAttribute("value"));

            // 👉 Trigger sự kiện change cho input file
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }))", fileInput);
            System.out.println("Đã trigger sự kiện change.");

            // Chụp màn hình sau khi upload
            takeScreenshot("after_file_upload");

            // 👉 Đợi và click nút xác nhận nếu có
            System.out.println("Bắt đầu chờ nút Xác nhận (nếu có)...");
            try {
                WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[contains(text(),'Xác nhận')]]")));
                confirmBtn.click();
                System.out.println("Đã click nút Xác nhận.");
            } catch (TimeoutException e) {
                System.out.println("Không tìm thấy nút xác nhận, có thể không cần thiết");
            }

            // 👉 Đợi thông báo thành công hoặc lỗi
            System.out.println("Bắt đầu chờ thông báo kết quả...");
            try {
                WebElement resultMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'success') or contains(@class,'error')]")));
                System.out.println("Kết quả upload: " + resultMessage.getText());
            } catch (TimeoutException e) {
                System.out.println("Không tìm thấy thông báo kết quả");
            }

            // Chụp màn hình cuối cùng
            takeScreenshot("final_state");

        } catch (Exception e) {
            System.out.println("Lỗi khi thực hiện test: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("error_state");
            throw e;
        }
    }

    private void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot)driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            File destFile = new File("screenshot_" + name + ".png");
            org.openqa.selenium.io.FileHandler.copy(screenshot, destFile);
            System.out.println("Đã chụp màn hình: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Không thể chụp màn hình: " + e.getMessage());
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
