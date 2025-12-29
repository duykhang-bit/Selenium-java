package testcases;

import base.BaseTest1;
import listeners.TestListener;
import org.openqa.selenium.By;
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

    // ===================== TC01 LOGIN =====================
    @Test(priority = 1)
    public void testLogin() {

        test.info("📝 Bước 1: Nhập username");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")))
                .sendKeys("giant");

        test.info("📝 Bước 2: Nhập password");
        driver.findElement(By.name("LoginInput.Password"))
                .sendKeys("********");

        test.info("📝 Bước 3: Click nút đăng nhập");
        driver.findElement(By.id("kt_login_signin_submit")).click();

        test.info("📝 Bước 4: Chờ chuyển trang");
        wait.until(ExpectedConditions.urlContains("manager"));

        test.info("📝 Bước 5: Verify đăng nhập thành công");
        Assert.assertTrue(driver.getCurrentUrl().contains("manager"));
    }

    // ===================== TC02 CREATE CTKM =====================
    @Test(priority = 2, dependsOnMethods = "testLogin")
    public void testCreateCTKM() {

        test.info("📝 Bước 6: Bấm button tạo CTKM");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'actionHeader')]")))
                .click();

        test.info("📝 Bước 7: Nhập tên CTKM");
        driver.findElement(By.id("promotiongeneralinfor_name"))
                .sendKeys("Automation Test team Noti");

        test.info("📝 Bước 8: Nhập ghi chú");
        driver.findElement(By.id("promotiongeneralinfor_remark"))
                .sendKeys("Automation Test team Noti1");
    }

    // ===================== TC03 SELECT TIME =====================
    @Test(priority = 3, dependsOnMethods = "testCreateCTKM")
    public void testSelectTime() {

        test.info("📝 Bước 9: Chọn thời gian");
        driver.findElement(By.xpath("//div[contains(@class,'ant-picker-range')]"))
                .click();

        test.info("🕒 Chọn ngày bắt đầu");
        driver.findElement(By.xpath("//td[@title='2025-12-26']")).click();

        test.info("🕒 Chọn ngày kết thúc");
        driver.findElement(By.xpath("//td[@title='2025-12-31']")).click();
    }

    // ===================== TC04 SELECT METHOD =====================
    @Test(priority = 4, dependsOnMethods = "testSelectTime")
    public void testSelectMethod() {

        test.info("📝 Bước 10: Chọn phương thức gửi thông báo");
        driver.findElement(By.xpath("//div[contains(@class,'ant-select-selector')]"))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Zalo')]")))
                .click();
    }

    // ===================== TC05 SELECT CAMPAIGN =====================
    @Test(priority = 5, dependsOnMethods = "testSelectMethod")
    public void testSelectCampaign() {

        test.info("📝 Bước 11: Chọn thuộc chiến dịch");
        driver.findElement(By.id("promotiongeneralinfor_campaignId")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'CD-1225-059')]")))
                .click();
    }

    // ===================== TC06 NEXT =====================
    @Test(priority = 6, dependsOnMethods = "testSelectCampaign")
    public void testClickNext() {

        test.info("📝 Bước 12: Click button Tiếp theo");
        driver.findElement(By.xpath("//span[contains(text(),'Tiếp theo')]"))
                .click();
    }
}
