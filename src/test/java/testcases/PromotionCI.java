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

    @Test
    public void testLogin() {
        WebElement userNameBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("giant");

        driver.findElement(By.name("LoginInput.Password"))
                .sendKeys("********");

        driver.findElement(By.id("kt_login_signin_submit")).click();

        wait.until(ExpectedConditions.urlContains("manager"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("manager"),
                "Login FAILED"
        );
    }
}
