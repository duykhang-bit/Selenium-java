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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

@Listeners(TestListener.class)
public class VoucherCI extends BaseTest1 {

        @Override
        protected String getBaseUrl() {
                return "https://ci-promotion.frt.vn/manager-promotion-list";
        }

        // =================================================
        // FLOW - TẠO VOUCHER
        // =================================================
        @Test(priority = 1, description = "FLOW - Tạo Voucher CI")
        public void testCreateVoucherFlow() {

                /*
                 * =========================
                 * TC01 - LOGIN
                 * =========================
                 */
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

                /*
                 * =========================
                 * TC02 - MENU VOUCHER
                 * =========================
                 */
                ExtentTest tc02 = test.createNode("TC02 - Vào menu Voucher");

                // Click menu Voucher (CHA)
                By voucherMenu = By.xpath(
                                "//span[normalize-space()='Voucher']/ancestor::li[contains(@class,'ant-menu-submenu')]");
                wait.until(ExpectedConditions.elementToBeClickable(voucherMenu)).click();

                // Chờ submenu render
                By maUuDaiMenu = By.xpath(
                                "//li[contains(@data-menu-id,'manager-voucher-without-price')]");
                WebElement maUuDai = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(maUuDaiMenu));
                maUuDai.click();

                tc02.pass("Vào màn Mã ưu đãi");

                /*
                 * =========================
                 * TC03 - TẠO VOUCHER
                 * =========================
                 */
                ExtentTest tc03 = test.createNode("TC03 - Tạo voucher");

                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'actionHeader')]"))).click();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
                String voucherName = "AT_" + LocalDateTime.now().format(formatter);

                wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.id("inputID"))).sendKeys(voucherName);

                tc03.pass("Nhập tên voucher: " + voucherName);

                /*
                 * =========================
                 * TC04 - LOẠI CHƯƠNG TRÌNH
                 * =========================
                 */
                ExtentTest tc04 = test.createNode("TC04 - Loại chương trình");

                By loaiCT = By.xpath(
                                "//label[contains(text(),'Loại chương trình')]" +
                                                "/ancestor::div[contains(@class,'ant-form-item')]" +
                                                "//div[contains(@class,'ant-select-selector')]");

                wait.until(ExpectedConditions.elementToBeClickable(loaiCT)).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'ant-select-item-option-content') and normalize-space()='1-FLC']")))
                                .click();

                tc04.pass("Chọn loại chương trình OK");




                /// time
                ///  // Lấy ngày hiện tại
                String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                ExtentTest tc11 = test.createNode("TC03 - Chọn thời gian & phương thức");
                
                // Click range picker
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'ant-picker-range')]"))).click();
                
                // Click ngày bắt đầu
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//td[@title='" + today + "']"))).click();
                
                // Click ngày kết thúc (cùng ngày)
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//td[@title='" + today + "']"))).click();
                
               
                tc11.pass("Chọn thời gian & phương thức OK");

                /*
                 * =========================
                 * TC05 - CHI PHÍ PHÒNG BAN
                 * =========================
                 */
                ExtentTest tc05 = test.createNode("TC05 - Chi phí phòng ban");

                By dropdownCPPB = By.xpath(
                                "//label[contains(text(),'Chi phí phòng ban')]" +
                                                "/ancestor::div[contains(@class,'ant-form-item')]" +
                                                "//div[contains(@class,'ant-select-selector')]");

                wait.until(ExpectedConditions.elementToBeClickable(dropdownCPPB)).click();

                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'ant-select-item-option-content') and normalize-space()='11000-Ban Tổng Giám Đốc / BOD']")))
                                .click();

                tc05.pass("Chọn chi phí phòng ban OK");

                WebElement soluong = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath(
                                                                "//label[contains(text(),'Số lượng')]" +
                                                                                "/ancestor::div[contains(@class,'ant-form-item')]"
                                                                                +
                                                                                "//input")));
                soluong.sendKeys("1");

                // GROUP CODE
                WebElement groupcode = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//input[contains(@id,'voucher_groupId')]")));
                groupcode.clear();
                groupcode.sendKeys("7212");

                By optionGroupCode = By.xpath(
                                "//div[contains(@class,'ant-select-item ant-select-item-option') and (contains(.,'7212 - AutomationTest'))]");

                wait.until(ExpectedConditions.elementToBeClickable(optionGroupCode)).click();

                // Tài khoản long châu
               // Click vào box select
wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//label[@title='Tài khoản long châu']/ancestor::div[contains(@class,'ant-form-item')]//div[contains(@class,'ant-select')]")
    )).click();

    wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(@class,'ant-select-item-option') and @title='Chi phí bán hàng khác FS_FPT']")
    )).click();
    

                // Tài khoản FPT
                WebElement TKFPT = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//input[contains(@id,'voucher_accountCompanyHOId')]")));
                TKFPT.click();

                By TKfpt = By.xpath(
                                "//div[contains(@class,'ant-select-item ant-select-item-option') and (contains(.,'Chi phí bán hàng khác FRT'))]");

                wait.until(ExpectedConditions.elementToBeClickable(TKfpt)).click();
                // CLICK XÁC NHẬN
                WebElement btnXACNHAN = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//button[.//span[normalize-space()='Xác nhận']]")));
                btnXACNHAN.click();

                // Pop_up đồng ý
                By btnDongYBy = By.xpath(
                                "//div[contains(@class,'ant-modal-footer')]//button[.//span[normalize-space()='Đồng ý']]");

                WebElement btnDongY = wait.until(
                                ExpectedConditions.elementToBeClickable(btnDongYBy));
                btnDongY.click();

        }
}
