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

    // =========================
    // TEST CASE 1: LOGIN
    // =========================
    @Test(description = "Test đăng nhập vào hệ thống Promotion CI")
    public void testLogin() {

        test.info("📝 Bước 1: Nhập username");
        WebElement userNameBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("giant");
        test.pass("✅ Đã nhập username");

        test.info("📝 Bước 2: Nhập password");
        driver.findElement(By.name("LoginInput.Password"))
                .sendKeys("********");
        test.pass("✅ Đã nhập password");

        test.info("📝 Bước 3: Click nút đăng nhập");
        driver.findElement(By.id("kt_login_signin_submit")).click();

        test.info("📝 Bước 4: Chờ chuyển trang");
        wait.until(ExpectedConditions.urlContains("manager"));

        test.info("📝 Bước 5: Verify đăng nhập thành công");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("manager"),
                "Login FAILED"
        );
        test.pass("✅ Login thành công");
    }

    // =========================
    // TEST CASE 2: THÔNG TIN CHUNG CTKM
    // =========================
    @Test(description = "Nhập thông tin chung CTKM", dependsOnMethods = "testLogin")
    public void testGeneralInformation() {

        test.info("📝 Bước 6: Bấm button tạo CTKM");
        WebElement createPromotionBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'actionHeader')]")));
        createPromotionBtn.click();
        test.pass("✅ Đã bấm tạo CTKM");

        test.info("📝 Bước 7: Nhập tên CTKM");
        WebElement nameCTKMBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("promotiongeneralinfor_name")));
        nameCTKMBox.sendKeys("Automation Test team Noti");
        test.pass("✅ Đã nhập tên CTKM");

        test.info("📝 Bước 8: Nhập ghi chú");
        WebElement noteCTKMBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("promotiongeneralinfor_remark")));
        noteCTKMBox.sendKeys("Automation Test team Noti1");
        test.pass("✅ Đã nhập ghi chú");

        test.info("📝 Bước 9: Chọn thời gian");
        WebElement timeBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'ant-picker-range')]")));
        timeBox.click();

        String startDate = "2025-12-26";
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + startDate + "']")))
                .click();
        test.pass("✅ Đã chọn ngày bắt đầu");

        String endDate = "2025-12-31";
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + endDate + "']")))
                .click();
        test.pass("✅ Đã chọn ngày kết thúc");

        test.info("📝 Bước 10: Chọn phương thức gửi thông báo");
        WebElement methodBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'ant-select-selector')]")));
        methodBox.click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and text()='Zalo']")))
                .click();
        test.pass("✅ Đã chọn Zalo");
    }

    // =========================
    // TEST CASE 3: CHỌN CHIẾN DỊCH
    // =========================
    @Test(description = "Chọn thuộc chiến dịch", dependsOnMethods = "testGeneralInformation")
    public void testSelectCampaign() {

        test.info("📝 Bước 11: Chọn thuộc chiến dịch");
        WebElement campaignBox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("promotiongeneralinfor_campaignId")));
        campaignBox.click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(text(),'CD-1225-059')]")))
                .click();
        test.pass("✅ Đã chọn chiến dịch");
    }

    // =========================
    // TEST CASE 4: TIẾP THEO
    // =========================
    @Test(description = "Click button Tiếp theo", dependsOnMethods = "testSelectCampaign")
    public void testClickNext() {

        test.info("📝 Bước 12: Click button Tiếp theo");
        WebElement nextBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'Tiếp theo')]")));
        nextBtn.click();
        test.pass("✅ Đã click Tiếp theo");
    }
}
