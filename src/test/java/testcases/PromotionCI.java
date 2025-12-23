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
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("giant");
        test.pass("✅ Đã nhập username: giant");

        // Log bước 2: Nhập password
        test.info("📝 Bước 2: Nhập password");
        driver.findElement(By.name("LoginInput.Password"))
                .sendKeys("********");
        test.pass("✅ Đã nhập password");

        // Log bước 3: Click nút đăng nhập
        test.info("📝 Bước 3: Click nút đăng nhập");
        driver.findElement(By.id("kt_login_signin_submit")).click();
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
}
