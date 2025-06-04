package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementNotInteractableException;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public LoginPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void login(String username, String password) {
        try {
            driver.get("https://ci-rsa-ecom.frt.vn/");
            
            // Wait for and input username
            WebElement userNameBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.name("LoginInput.UserNameOrEmailAddress")));
            wait.until(ExpectedConditions.elementToBeClickable(userNameBox));
            userNameBox.clear();
            userNameBox.sendKeys(username);
            test.info("Đã nhập username: " + username);

            // Wait for and input password
            WebElement passwordBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.name("LoginInput.Password")));
            wait.until(ExpectedConditions.elementToBeClickable(passwordBox));
            passwordBox.clear();
            passwordBox.sendKeys(password);
            test.info("Đã nhập password");

            // Click login button and wait for it to disappear
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("kt_login_signin_submit")));
            loginBtn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));
            test.info("Đăng nhập hệ thống thành công.");
            
        } catch (TimeoutException e) {
            test.fail("Timeout khi chờ element xuất hiện: " + e.getMessage());
            throw e;
        } catch (ElementNotInteractableException e) {
            test.fail("Không thể tương tác với element: " + e.getMessage());
            throw e;
        } catch (NoSuchElementException e) {
            test.fail("Không tìm thấy element: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Lỗi không xác định khi đăng nhập: " + e.getMessage());
            throw e;
        }
    }

    public void systemLogin() {
        try {
            // Click menu
            WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='ant-menu-submenu-title']")));
            menu.click();
            test.info("Đã click vào menu");

            // Click login button
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='A-SIGNIN-BTN']")));
            loginBtn.click();
            test.info("Đăng nhập Call Center thành công.");
            
        } catch (TimeoutException e) {
            test.fail("Timeout khi chờ element xuất hiện: " + e.getMessage());
            throw e;
        } catch (ElementNotInteractableException e) {
            test.fail("Không thể tương tác với element: " + e.getMessage());
            throw e;
        } catch (NoSuchElementException e) {
            test.fail("Không tìm thấy element: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("Lỗi không xác định khi đăng nhập hệ thống: " + e.getMessage());
            throw e;
        }
    }
} 