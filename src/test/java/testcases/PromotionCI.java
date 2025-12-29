package testcases;

import base.BaseTest1;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class PromotionCI extends BaseTest1 {

    @Override
    protected String getBaseUrl() {
        return "https://ci-promotion.frt.vn/manager-promotion-list";
    }

    @Test(description = "Test đăng nhập vào hệ thống Promotion CI")
    public void testLogin() {
        // Log bước 1: Nhập username
        test.info("📝 Bước 1: Nhập username");
        WebElement userNameBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("LoginInput.UserNameOrEmailAddress")));
        wait.until(ExpectedConditions.elementToBeClickable(userNameBox));
        userNameBox.clear();
        userNameBox.sendKeys("giant");
        test.pass("✅ Đã nhập username: giant");

        // Log bước 2: Nhập password
        test.info("📝 Bước 2: Nhập password");
        WebElement passwordBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("LoginInput.Password")));
        wait.until(ExpectedConditions.elementToBeClickable(passwordBox));
        passwordBox.clear();
        passwordBox.sendKeys("********");
        test.pass("✅ Đã nhập password");

        // Log bước 3: Click nút đăng nhập
        test.info("📝 Bước 3: Click nút đăng nhập");
        WebElement loginBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("kt_login_signin_submit")));
        loginBtn.click();
        test.pass("✅ Đã click nút đăng nhập");

        // Log bước 4: Chờ chuyển trang
        test.info("📝 Bước 4: Chờ chuyển đến trang manager");
        wait.until(ExpectedConditions.urlContains("manager"));
        test.pass("✅ Đã chuyển đến trang manager");

        // Log bước 5: Verify đăng nhập thành công
        test.info("📝 Bước 5: Verify đăng nhập thành công");
        String currentUrl = driver.getCurrentUrl();
        test.info("🔗 Current URL: " + currentUrl);
        test.info("📄 Page Title: " + driver.getTitle());
        
        Assert.assertTrue(
                currentUrl.contains("manager"),
                "Login FAILED"
        );
        test.pass("✅ Đăng nhập thành công - URL chứa 'manager'");
    }

    @Test(description = "Test tạo CTKM")
    public void testTaoCTKM() {
        testLogin();
        
        // bấm button tạo CTKM
        test.info("📝 Bước 6: Bấm button tạo CTKM");
        WebElement createPromotionBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class,'actionHeader')]")));
        createPromotionBtn.click();
        test.pass("✅ Đã bấm button tạo CTKM");

        // Tên CTKM
        test.info("📝 Bước 7: Nhập tên CTKM");
        WebElement nameCTKMBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("promotiongeneralinfor_name")));
        nameCTKMBox.clear();
        nameCTKMBox.sendKeys("Automation Test team Noti");
        test.pass("✅ Đã nhập tên CTKM");

        // Ghi chú
        test.info("📝 Bước 8: Nhập ghi chú");
        WebElement noteCTKMBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("promotiongeneralinfor_remark")));
        noteCTKMBox.clear();
        noteCTKMBox.sendKeys("Automation Test team Noti1");
        test.pass("✅ Đã nhập ghi chú");
        
        // Chọn Thời gian
        test.info("📝 Bước 9: Nhập thời gian");
        WebElement timeCTKMBox = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='ant-picker ant-picker-range ant-picker-middle custom-control']")));
        timeCTKMBox.click();
        
        // time start
        String date = "2025-12-29";
        WebElement timeStartBox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + date + "']")));
        timeStartBox.click();
        test.pass("✅ Đã nhập thời gian start");
        
        // time end
        String date1 = "2025-12-31";
        WebElement timeEndBox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + date1 + "']")));
        timeEndBox.click();
        test.pass("✅ Đã nhập thời gian end");
        
        // Phương thức gửi thông tin
        test.info("📝 Bước 10: Chọn phương thức gửi thông báo");
        WebElement methodCTKMBox = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class,'ant-select-selector')]")));
        methodCTKMBox.click();
        WebElement zaloPTGTT = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(text(),'Zalo')]")));
        zaloPTGTT.click();
        test.pass("✅ Đã chọn phương thức gửi thông báo");
    }

    @Test(description = "Test chọn thuộc chiến dịch")
    public void testChonThuocChienDich() throws InterruptedException {
        testTaoCTKM();
        
        // Chọn thuộc chiến dịch
        test.info("📝 Bước 11: Chọn thuộc chiến dịch");
        
        // Click vào dropdown để mở danh sách
        WebElement campaignCTKMBox = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("promotiongeneralinfor_campaignId")));
        campaignCTKMBox.click();
        
        // Đợi dropdown mở ra (đợi list options xuất hiện)
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]")));
        
        // Gõ text để tìm kiếm
        campaignCTKMBox.clear();
        campaignCTKMBox.sendKeys("CD-1225-059");
        
        // Đợi option xuất hiện sau khi filter (đợi 1-2 giây để dropdown filter)
        Thread.sleep(1500);
        
        // Click vào option - TÌM ĐÚNG OPTION TRONG DROPDOWN LIST (ant-select-item-option)
        WebElement campaignOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class,'ant-select-item-option') and contains(.,'CD-1225-059')]")));
        campaignOption.click();
        
        test.pass("✅ Đã chọn thuộc chiến dịch: CD-1225-059");

        // button tiếp theo
        test.info("📝 Bước 12: Click button tiếp theo");
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(),'Tiếp theo')]")));
        nextBtn.click();
        test.pass("✅ Đã click button tiếp theo");
    }
}


//thứ tự run test
//1. @BeforeSuite (setupReport)
//   ↓
//           2. @BeforeMethod (setup) → Mở browser, navigate
//   ↓
//           3. TestListener.onTestStart()
//   ↓
//           4. @Test (testLogin) → Chạy test
//   ↓
//           5. TestListener.onTestSuccess/Failure()
//   ↓
//           6. @AfterMethod (teardown) → Screenshot, đóng browser
//   ↓
//           7. @AfterSuite (flushReport) → Ghi report
