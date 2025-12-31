package testcases;
import com.aventstack.extentreports.ExtentTest;


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

    // =================================================
    // FULL FLOW - TẠO CTKM (TC01 → TC06)
    // =================================================
    @Test(description = "FULL FLOW - Tạo CTKM Promotion CI")
    public void testCreatePromotionFlow() {

        /* =========================
         * TC01 - LOGIN
         * ========================= */
        ExtentTest tc01 = test.createNode("TC01 - Login hệ thống");

        tc01.info("Nhập username");
        WebElement userNameBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.clear();
        userNameBox.sendKeys("giant");

        tc01.info("Nhập password");
        WebElement passwordBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("LoginInput.Password")));
        passwordBox.clear();
        passwordBox.sendKeys("********");

        tc01.info("Click đăng nhập");
        driver.findElement(By.id("kt_login_signin_submit")).click();

        wait.until(ExpectedConditions.urlContains("manager"));
        Assert.assertTrue(driver.getCurrentUrl().contains("manager"),
                "Login FAILED");

        tc01.pass("Login thành công");

        /* =========================
         * TC02 - TẠO CTKM
         * ========================= */
        ExtentTest tc02 = test.createNode("TC02 - Tạo CTKM");

        tc02.info("Click button tạo CTKM");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'actionHeader')]"))).click();

        tc02.info("Nhập tên CTKM");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("promotiongeneralinfor_name")))
                .sendKeys("Automation Test");

        tc02.info("Nhập ghi chú");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("promotiongeneralinfor_remark")))
                .sendKeys("Automation Test team Noti1");

        tc02.pass("Tạo CTKM OK");

        /* =========================
         * TC03 - THỜI GIAN + PHƯƠNG THỨC
         * ========================= */
        ExtentTest tc03 = test.createNode("TC03 - Chọn thời gian & phương thức");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-picker-range')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='2025-12-31']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='2025-12-31']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-selector')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Zalo')]"))).click();

        tc03.pass("Chọn thời gian & phương thức OK");

        /* =========================
         * TC04 - THUỘC CHIẾN DỊCH
         * ========================= */
        ExtentTest tc04 = test.createNode("TC04 - Thuộc chiến dịch");

        WebElement campaignBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("promotiongeneralinfor_campaignId")));
        campaignBox.sendKeys("CD-1225-059");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option') and contains(.,'CD-1225-059')]")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Tiếp theo')]"))).click();

        tc04.pass("Hoàn tất màn 1");

        /* =========================
         * TC05 - NHÓM CTKM
         * ========================= */
        ExtentTest tc05 = test.createNode("TC05 - Nhóm CTKM");

        WebElement nhom = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("promotionClassId")));
        nhom.sendKeys("Sản Phẩm");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Sản phẩm')]")))
                .click();

        tc05.pass("Chọn nhóm CTKM OK");

        /* =========================
         * TC06 - LOẠI CTKM
         * ========================= */
        ExtentTest tc06 = test.createNode("TC06 - Loại CTKM");

        WebElement loai = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("promotionTypeID")));
        loai.click();
        loai.sendKeys("Giảm giá sản phẩm");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Giảm giá sản phẩm')]")))
                .click();

        tc06.pass("Chọn loại CTKM OK");
    
/* =========================
 * TC07 - KHU VỰC HIỂN THỊ
 * ========================= */
        ExtentTest tc07 = test.createNode("TC07 - Khu vực hiển thị khuyến mãi");
// cách bắt ant design element
        By displayAreaDropdownBy = By.xpath(
        "//label[contains(text(),'Khu vực hiển thị khuyến mãi')]" +
        "/ancestor::div[contains(@class,'ant-form-item')]" +
        "//div[contains(@class,'ant-select-selector')]"
        );

        WebElement displayAreaDropdown = wait.until(
        ExpectedConditions.elementToBeClickable(displayAreaDropdownBy)
        );

        displayAreaDropdown.click();

        //✅ CLICK OPTION SAU KHI MỞ DROPDOWN
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Khuyến mãi sản phẩm chính')]")))
                .click();

    
            tc07.pass("Chọn khu vực hiển thị khuyến mãi OK");
        }            
}

    //displayArea



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
