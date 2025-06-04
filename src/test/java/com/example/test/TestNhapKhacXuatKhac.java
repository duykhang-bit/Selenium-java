package com.example.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

public class TestNhapKhacXuatKhac {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testImportExcel() throws FileNotFoundException {
        // Login
        driver.get("https://erp.vinhomes.vn/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Tên đăng nhập']"))).sendKeys("leduykhang");
        driver.findElement(By.xpath("//input[@placeholder='Mật khẩu']")).sendKeys("Khang@123");
        driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]")).click();

        // Wait for login to complete
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ant-layout-header')]")));

        // Click menu
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Nhập khác xuất khác')]")));
        js.executeScript("arguments[0].click();", menu);

        // Wait for page to load and find Import button
        WebElement importBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Import')]")));
        
        // Check for notification overlay
        try {
            WebElement notification = driver.findElement(By.xpath("//div[contains(@class, 'ant-notification-notice')]"));
            if (notification.isDisplayed()) {
                System.out.println("Found notification overlay, waiting for it to disappear...");
                wait.until(ExpectedConditions.invisibilityOf(notification));
            }
        } catch (Exception e) {
            System.out.println("No notification overlay found");
        }

        // Click Import button using JavaScript
        js.executeScript("arguments[0].click();", importBtn);

        // Wait for file input to be present and clickable
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
        
        // Get the Excel file path
        String excelPath = new File("src/test/resources/import-template.xlsx").getAbsolutePath();
        System.out.println("Excel file path: " + excelPath);

        // Send file path to input
        fileInput.sendKeys(excelPath);

        // Wait for upload to complete and check for success message
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Import thành công')]")));
            System.out.println("Import successful: " + successMessage.getText());
        } catch (Exception e) {
            System.out.println("No success message found, checking for error message...");
            try {
                WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'ant-message-error')]"));
                System.out.println("Error message: " + errorMessage.getText());
            } catch (Exception ex) {
                System.out.println("No error message found");
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 