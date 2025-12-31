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

                /*
                 * =========================
                 * TC01 - LOGIN
                 * =========================
                 */
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

                /*
                 * =========================
                 * TC02 - TẠO CTKM
                 * =========================
                 */
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

                /*
                 * =========================
                 * TC03 - THỜI GIAN + PHƯƠNG THỨC
                 * =========================
                 */
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

                /*
                 * =========================
                 * TC04 - THUỘC CHIẾN DỊCH
                 * =========================
                 */
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

                /*
                 * =========================
                 * TC05 - NHÓM CTKM
                 * =========================
                 */
                ExtentTest tc05 = test.createNode("TC05 - Nhóm CTKM");

                WebElement nhom = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.id("promotionClassId")));
                nhom.sendKeys("Sản Phẩm");

                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Sản phẩm')]")))
                                .click();

                tc05.pass("Chọn nhóm CTKM OK");

                /*
                 * =========================
                 * TC06 - LOẠI CTKM
                 * =========================
                 */
                ExtentTest tc06 = test.createNode("TC06 - Loại CTKM");

                WebElement loai = wait.until(ExpectedConditions.elementToBeClickable(
                                By.id("promotionTypeID")));
                loai.click();
                loai.sendKeys("Giảm giá sản phẩm");

                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Giảm giá sản phẩm')]")))
                                .click();

                tc06.pass("Chọn loại CTKM OK");

                /*
                 * =========================
                 * TC07 - KHU VỰC HIỂN THỊ
                 * =========================
                 */
                ExtentTest tc07 = test.createNode("TC07 - Khu vực hiển thị khuyến mãi");
                // cách bắt ant design element dropdown

                By displayAreaDropdownBy = By.xpath(
                                "//label[contains(text(),'Khu vực hiển thị khuyến mãi')]" +
                                                "/ancestor::div[contains(@class,'ant-form-item')]" +
                                                "//div[contains(@class,'ant-select-selector')]");

                WebElement displayAreaDropdown = wait.until(
                                ExpectedConditions.elementToBeClickable(displayAreaDropdownBy));

                displayAreaDropdown.click();

                // ✅ CLICK OPTION SAU KHI MỞ DROPDOWN
                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(@class,'ant-select-item-option-content') and contains(.,'Khuyến mãi sản phẩm chính')]")))
                                .click();

                tc07.pass("Chọn khu vực hiển thị khuyến mãi OK");

                ExtentTest tc08 = test.createNode("TC08 - Chi phí phòng ban");

                // 1️⃣ CLICK DROPDOWN
                By dropdownCPPB = By.xpath(
                                "//label[contains(text(),'Chi phí phòng ban')]" +
                                                "/ancestor::div[contains(@class,'ant-form-item')]" +
                                                "//div[contains(@class,'ant-select-selector')]");

                WebElement dropdownElement = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownCPPB));
                dropdownElement.click();

                // 2️⃣ CLICK OPTION
                By optionCPPB = By.xpath(
                                "//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]"
                                                +
                                                "//div[contains(@class,'ant-select-item-option-content') and " +
                                                "normalize-space()='Phòng kiểm soát nghành hàng / KSNH']");

                wait.until(ExpectedConditions.elementToBeClickable(optionCPPB)).click();

                tc08.pass("Chọn Chi phí phòng ban OK");

                // 3️⃣ NEXT
                wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//span[contains(text(),'Tiếp theo')]"))).click();

                // Step 3
                ExtentTest tc09 = test.createNode("Loại đầu vào");
                By dropdownLDV = By.xpath(
                                "//div[contains(@class,'ant-col ant-col-3')]" +
                                                "//div[contains(@class,'ant-select-selector')]");
                WebElement dropdownElement1 = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownLDV));
                dropdownElement1.click();

                By optionLDVBy = By.xpath(
                                "//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]"
                                                +
                                                "//div[contains(@class,'ant-select-item-option-content') and " +
                                                "normalize-space()='Mã Ngành hàng']");

                wait.until(ExpectedConditions.elementToBeClickable(optionLDVBy)).click();

                tc09.pass("Loại đầu vào");

                ExtentTest tc10 = test.createNode("Phép toán");
                By dropdownPT = By.xpath(
                                "//div[contains(@class,'ant-col ant-col-2')]" +
                                                "//div[contains(@class,'ant-select-selector')]");
                WebElement dropdownElement2 = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownPT));
                dropdownElement2.click();

                By optionPTBy = By.xpath(
                                "//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]"
                                                +
                                                "//div[contains(@class,'ant-select-item-option-content') and " +
                                                "normalize-space()='Bằng']");

                wait.until(ExpectedConditions.elementToBeClickable(optionPTBy)).click();

                tc10.pass("Phép toán  OK");

                ExtentTest tc11 = test.createNode("Giá trị");

                // CLICK DROPDOWN
                By dropdownGT = By.id(
                                "inputID"// div[contains(@class,'ant-select')]"
                );

                WebElement dropdownElement4 = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownGT));
                dropdownElement4.click();

                // CHỌN OPTION TRONG TABLE
                By optionGT = By.xpath(
                                "//td[contains(@class,'ant-table-cell') and contains(@class,'ant-table-selection-column')]");

                wait.until(ExpectedConditions.elementToBeClickable(optionGT)).click();

                // CLICK OK
                WebElement btnOK = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//button//span[text()='OK']")));
                btnOK.click();

                tc11.pass("Gía tri OK");

                ExtentTest tc12 = test.createNode("Loại đầu ra");
                By dropdownLDR = By.id("includeInput_0_groupCode_0_outputQualifierId");
                WebElement dropdownElement5 = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownLDR));
                dropdownElement5.click();

                By optionLDR = By.xpath(
                                "//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]"
                                                +
                                                "//div[contains(@class,'ant-select-item-option-content') and " +
                                                "normalize-space()='Giảm theo giá cố định']");

                wait.until(ExpectedConditions.elementToBeClickable(optionLDR)).click();

                tc12.pass("Loại đầu ra  OK");

                ExtentTest tc13 = test.createNode("TC11 - Nhập giá trị");

                By inputGiaTriBy = By.xpath(
                        "//input[@placeholder='Nhập giảm theo giá cố định']"
                    );
                    
                WebElement inputGiaTri = wait.until(
                                ExpectedConditions.elementToBeClickable(inputGiaTriBy));
                
                inputGiaTri.sendKeys("20000");

                tc13.pass("Nhập giá trị thành công");

                // CLICK XÁC NHẬN
                WebElement btnXACNHAN = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//button[.//span[normalize-space()='Xác nhận']]")));
                btnXACNHAN.click();

                
                By btnDongYBy = By.xpath(
                        "//div[contains(@class,'ant-modal-footer')]//button[.//span[normalize-space()='Đồng ý']]"
                    );
                    
                    WebElement btnDongY = wait.until(
                        ExpectedConditions.elementToBeClickable(btnDongYBy)
                    );
                    btnDongY.click();
                    

        }

}

// displayArea

// thứ tự run test
// 1. @BeforeSuite (setupReport)
// ↓
// 2. @BeforeMethod (setup) → Mở browser, navigate
// ↓
// 3. TestListener.onTestStart()
// ↓
// 4. @Test (testLogin) → Chạy test
// ↓
// 5. TestListener.onTestSuccess/Failure()
// ↓
// 6. @AfterMethod (teardown) → Screenshot, đóng browser
// ↓
// 7. @AfterSuite (flushReport) → Ghi report
