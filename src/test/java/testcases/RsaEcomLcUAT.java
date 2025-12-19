package testcases;
import base.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;


public class RsaEcomLcUAT extends BaseTest {
    
    @Override
    protected String getBaseUrl() {
        return "https://UAT-rsa-ecom.frt.vn/";
    }

    @Override
    protected long getWaitTimeout() {
        return 50;
    }

    @Test
    public void testLogin() {
        WebElement userNameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LoginInput.UserNameOrEmailAddress")));
        userNameBox.sendKeys("tinvt4");
        WebElement passwordBox = driver.findElement(By.name("LoginInput.Password"));
        passwordBox.sendKeys("********");
        WebElement loginBtn1 = driver.findElement(By.id("kt_login_signin_submit"));
        loginBtn1.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("kt_login_signin_submit")));
        test.pass("Login successful");
    }

    @Test
    public void testLoginAndSystemLogin() {
        testLogin();
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-menu-submenu-title']")));
        menu.click();
        WebElement loginBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='A-SIGNIN-BTN']")));
        loginBtn2.click();
        test.pass("System login successful");
    }

    @Test
    public void testCallFlow() throws InterruptedException {
        testLoginAndSystemLogin();
        WebElement sdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập số điện thoại']")));
        sdtBox.sendKeys("0835089254");
        WebElement call1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-CALL']")));
        call1.click();
        WebElement HoldOncall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-HOLD']")));
        HoldOncall.click();
        Thread.sleep(1000);
        WebElement Continuecall = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-RETRIEVE']")));
        Continuecall.click();
        test.pass("Call flow completed successfully");

// Ghi nội dung note
        WebElement Note = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='C2-NOTE-BTN']")));
        Note.click();

// Sửa lại để tìm đúng phần tử textarea thay vì input
        WebElement Ghichu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//textarea[@placeholder='Nhập ghi chú']")));
        Ghichu.click();
        Ghichu.sendKeys("Automation");



// Lưu ghi chú
        WebElement Luunote = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()='Xong']]")));
        Luunote.click();



    }

    @Test
    public void testTransferFlow1() throws InterruptedException {
        testCallFlow();
        WebElement TransferAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-TRANSFER']")));
        TransferAgentB.click();
        WebElement nhapsdtBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='TRANSFERTO']")));
        nhapsdtBox.sendKeys("30007");
        WebElement thamvan = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        thamvan.click();

        // Gọi agent B xử lý cuộc gọi
        RsaEcomAgentBUAT agentB = new RsaEcomAgentBUAT();
        agentB.runFlowUAT();




        WebElement transferB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='transferBtn']")));
        transferB.click();
        WebElement endcallAgentB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='CALL-ACTION-BTN-DROP']")));
        endcallAgentB.click();
        test.pass("Transfer flow completed successfully");
    }
}


//test

//    public static void main(String[] args) {
//        RsaEcomLc app = new RsaEcomLc();
//        try {
//            app.runFlow();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            app.teardown();
//        }
//    }

//int mark = 85;
//
//if (mark >= 90) {
//        System.out.println("Xuất sắc");
//} else if (mark >= 75) {
//        System.out.println("Giỏi");
//} else if (mark >= 50) {
//        System.out.println("Trung bình");
//} else {
//        System.out.println("Yếu");
//}
////
//int mark = 85;
//if (mark >-90){
//    System.out.println("Suất sắc");
//        }
//else if (mark >= 75){
//    System.out.println("giỏi");
//
//        }
//else{
//    System.out.println("Yếu");
//
//
//
//name: Run Selenium Tests
//
//on:
//  push:
//    branches:
//      - master
//  pull_request:
//    branches:
//      - master
//
//jobs:
//  build:
//    runs-on: ubuntu-latest
//
//    steps:
//    - name: Checkout code
//      uses: actions/checkout@v3
//
//    - name: Set up Java
//      uses: actions/setup-java@v3
//      with:
//        java-version: '17'
//        distribution: 'temurin'
//
//    - name: Build with Maven
//      run: mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml}
