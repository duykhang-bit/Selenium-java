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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Listeners(TestListener.class)
public class CreatePMHUAT extends BaseTest1 {

    @Override
    protected String getBaseUrl() {
        return "https://uat-promotion.frt.vn/manager-promotion-list";
    }

    @Test(priority = 1, description = "FLOW - Tạo Voucher CI")
    public void testCreatePMHFlowCI() {

        /* =========================
         * TC01 - LOGIN
         * ========================= */
        ExtentTest tc01 = test.createNode("TC01 - Login");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.name("LoginInput.UserNameOrEmailAddress"))).sendKeys("giant");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.name("LoginInput.Password"))).sendKeys("********");

        driver.findElement(By.id("kt_login_signin_submit")).click();

        wait.until(ExpectedConditions.urlContains("manager"));
        Assert.assertTrue(driver.getCurrentUrl().contains("manager"));
        tc01.pass("Login thành công");

        /* =========================
         * TC02 - MENU VOUCHER
         * ========================= */
        ExtentTest tc02 = test.createNode("TC02 - Vào menu Voucher");

        By voucherMenu = By.xpath(
                "//span[normalize-space()='Voucher']/ancestor::li[contains(@class,'ant-menu-submenu')]");

        wait.until(ExpectedConditions.elementToBeClickable(voucherMenu)).click();

        By maUuDaiMenu = By.xpath(
                "//li[contains(@data-menu-id,'manager-voucher-with-price')]");

        wait.until(ExpectedConditions.elementToBeClickable(maUuDaiMenu)).click();
        tc02.pass("Vào màn Mã ưu đãi");

        /* =========================
         * TC03 - TẠO VOUCHER
         * ========================= */
        ExtentTest tc03 = test.createNode("TC03 - Tạo voucher");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'actionHeader')]"))).click();

        String voucherName = "Automation test_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("inputID"))).sendKeys(voucherName);

        tc03.pass("Nhập tên voucher: " + voucherName);

        /* =========================
         * TC04 - LOẠI CHƯƠNG TRÌNH
         * ========================= */
        ExtentTest tc04 = test.createNode("TC04 - Loại chương trình");

        openAntDropdown(By.xpath(
                "//label[contains(text(),'Loại chương trình')]" +
                        "/ancestor::div[contains(@class,'ant-form-item')]" +
                        "//div[contains(@class,'ant-select-selector')]"));

        clickAntOptionByContains("FamilyPackage");
        tc04.pass("Chọn loại chương trình");

        /* =========================
         * TC05 - THỜI GIAN
         * ========================= */
        ExtentTest tc05 = test.createNode("TC05 - Thời gian");

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-picker-range')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + today + "']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@title='" + today + "']"))).click();

        tc05.pass("Chọn thời gian OK");

        /* =========================
         * TC06 - CHI PHÍ PHÒNG BAN
         * ========================= */
        ExtentTest tc06 = test.createNode("TC06 - Chi phí phòng ban");

        openAntDropdown(By.xpath(
                "//label[contains(text(),'Chi phí phòng ban')]" +
                        "/ancestor::div[contains(@class,'ant-form-item')]" +
                        "//div[contains(@class,'ant-select-selector')]"));

        clickAntOptionByContains("Ban Tổng Giám Đốc");
        tc06.pass("Chọn chi phí phòng ban");

        /* =========================
         * TC07 - SỐ LƯỢNG
         * ========================= */
        ExtentTest tc07 = test.createNode("TC07 - Số lượng");

        WebElement soLuong = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Số lượng')]" +
                        "/ancestor::div[contains(@class,'ant-form-item')]//input")));

        soLuong.clear();
        soLuong.sendKeys("3");
        tc07.pass("Nhập số lượng");

        /* =========================
         * TC08 - CÔNG TY
         * ========================= */
        ExtentTest tc08 = test.createNode("TC08 - Công ty");

        openAntDropdown(By.xpath(
                "//label[contains(text(),'Công ty')]" +
                        "/ancestor::div[contains(@class,'ant-form-item')]" +
                        "//div[contains(@class,'ant-select-selector')]"));

        clickAntOptionByTitle("Utop");
        tc08.pass("Chọn công ty Utop");


       
         /* =========================
         * TC09 - MÃ SẢN PHẨM ✅ FIX
         * ========================= */
         ExtentTest tc09 = test.createNode("TC09 - Mã sản phẩm");

         /* 1️⃣ Click vào INPUT search của Ant Select */
         By maSanPhamInput = By.id("itemVoucherId");
         
         WebElement input = wait.until(
                 ExpectedConditions.elementToBeClickable(maSanPhamInput)
         );
         input.click();
         
         /* 2️⃣ Gõ mã sản phẩm */
         input.sendKeys("14579413");
         
         /* 3️⃣ Đợi dropdown render option rồi click */
         By option = By.xpath(
                 "//div[contains(@class,'ant-select-item-option-content')" +
                 " and contains(normalize-space(),'14579413')]"
         );
         
         WebElement optionEl = wait.until(
                 ExpectedConditions.elementToBeClickable(option)
         );
         
         optionEl.click();
         
         tc09.pass("Chọn mã sản phẩm 14579413");
         
 
         /* =========================
          * TC10 - TÀI KHOẢN LONG CHÂU
          * ========================= */

        

      // Tài khoản long chau
        ExtentTest tc10 = test.createNode("TC10 - Tài khoản Long Châu");

        openAntDropdown(By.xpath(
                "//label[@title='Tài khoản long châu']" +
                        "/ancestor::div[contains(@class,'ant-form-item')]" +
                        "//div[contains(@class,'ant-select-selector')]"));

        clickAntOptionByContains("FS_FPT");
        tc10.pass("Chọn tài khoản Long Châu");

        /* =========================
         * TC11 - TÀI KHOẢN FPT
         * ========================= */
        ExtentTest tc11 = test.createNode("TC11 - Tài khoản FPT");

        openAntDropdown(By.id("accountCompanyHOId"));
        clickAntOptionByContains("1 - Chi phí bán hàng khác FS_FPT");
        tc11.pass("Chọn tài khoản FPT");

        /* =========================
         * TC12 - XÁC NHẬN
         * ========================= */
        ExtentTest tc12 = test.createNode("TC12 - Xác nhận");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Xác nhận']]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Đồng ý']]"))).click();

        tc12.pass("Tạo voucher thành công");
    }
}