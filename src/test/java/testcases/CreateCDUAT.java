package testcases;

import com.aventstack.extentreports.ExtentTest;
import base.BaseTest1;
import listeners.TestListener;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Listeners(TestListener.class)
public class CreateCDUAT extends BaseTest1 {

    @Override
    protected String getBaseUrl() {
        return "https://uat-promotion.frt.vn/manager-promotion-list";
    }

    @Test(priority = 1, description = "FLOW - Tạo chiến dịch")
    public void testCreateCampaignFlow() {

        /* =========================
         * TC01 - LOGIN
         * ========================= */
        ExtentTest tc01 = test.createNode("TC01 - Login");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.name("LoginInput.UserNameOrEmailAddress")))
                .sendKeys("giant");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.name("LoginInput.Password")))
                .sendKeys("********");

        driver.findElement(By.id("kt_login_signin_submit")).click();

        wait.until(ExpectedConditions.urlContains("manager"));
        Assert.assertTrue(driver.getCurrentUrl().contains("manager"));
        tc01.pass("Login OK");

        /* =========================
         * TC02 - MENU CHIẾN DỊCH
         * ========================= */
        ExtentTest tc02 = test.createNode("TC02 - Vào menu Chiến dịch");

        By menuCampaign = By.xpath(
                "//li[contains(@class,'ant-menu-item') and contains(@data-menu-id,'manager-campaign')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(menuCampaign)).click();
        tc02.pass("Vào menu Chiến dịch");

        /* =========================
         * TC03 - TẠO CHIẾN DỊCH
         * ========================= */
        ExtentTest tc03 = test.createNode("TC03 - Tạo chiến dịch");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'actionHeader')]")
        )).click();

        WebElement txtName = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("name"))
        );
        txtName.sendKeys("Automation Test");
        tc03.pass("Nhập tên chiến dịch");

        /* =========================
         * TC04 - CHỌN NGÀY
         * ========================= */
        ExtentTest tc04 = test.createNode("TC04 - Chọn ngày");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // ===== START DATE =====
        LocalDate startDate = LocalDate.of(2026, 1, 16);
        String start = startDate.format(fmt);

        WebElement startInput = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@placeholder='Ngày bắt đầu']")
                )
        );

        startInput.click();
        startInput.sendKeys(Keys.CONTROL + "a");
        startInput.sendKeys(start);
        startInput.sendKeys(Keys.ENTER); // START DATE OK với Enter

        // ===== END DATE (BẮT BUỘC > START) =====
        LocalDate endDate = startDate.plusDays(1);
        String end = endDate.format(fmt);

        WebElement endInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("toDate"))
        );

        endInput.click();
        endInput.sendKeys(Keys.CONTROL + "a");
        endInput.sendKeys(end);

        // 🔥 QUAN TRỌNG: Ant Design cần ESC để COMMIT
        endInput.sendKeys(Keys.ESCAPE);

        // verify end date đã set
        Assert.assertEquals(endInput.getAttribute("value"), end);

        tc04.pass("Chọn ngày bắt đầu & kết thúc thành công");

        /* =========================
         * TC05 - SUBMIT
         * ========================= */
        ExtentTest tc05 = test.createNode("TC05 - Submit");

        WebElement btnSubmit = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class,'ant-btn-primary')]")
                )
        );
        btnSubmit.click();

        tc05.pass("Tạo chiến dịch thành công");
    }
}
