package testcases;

import org.testng.annotations.*;
import pages.LoginPage;
import pages.CallFlowPage;
import pages.TransferPage;

import base.BaseTest1;

public class RsaEcomLcCITest extends BaseTest1 {
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
