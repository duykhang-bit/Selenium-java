package testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.CallFlowPage;
import pages.TransferPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RsaEcomLcCITest extends BaseTest {
    @Test
    public void testLogin() {
        test = extent.createTest("testLogin");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("tinvt4", "********");
        test.pass("Login successful");
    }

    @Test
    public void testLoginAndSystemLogin() {
        test = extent.createTest("testLoginAndSystemLogin");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("tinvt4", "********");
        loginPage.systemLogin();
        test.pass("System login successful");
    }

    @Test
    public void testCallFlow() throws InterruptedException {
        test = extent.createTest("testCallFlow");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("tinvt4", "********");
        loginPage.systemLogin();
        CallFlowPage callFlowPage = new CallFlowPage(driver, wait, test);
        callFlowPage.performCallFlow();
        test.pass("Call flow completed successfully");
    }

    @Test
    public void testTransferFlow1() throws InterruptedException {
        test = extent.createTest("testTransferFlow");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("tinvt4", "********");
        loginPage.systemLogin();
        CallFlowPage callFlowPage = new CallFlowPage(driver, wait, test);
        callFlowPage.performCallFlow();
        TransferPage transferPage = new TransferPage(driver, wait, test);
        transferPage.performTransfer();
        test.pass("Transfer flow completed successfully");
    }
}




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
//                }

// ... giữ nguyên code của bạn
