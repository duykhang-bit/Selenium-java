package testcases;

import base.BaseTest1;
import pages.AgentBCIPage;
import pages.LoginPage;

public class RsaEcomAgentBCITest extends BaseTest1 {
    public void runFlow() {
        // Use BaseTest1's reporting if available; fall back to console logging here
        // test = extent.createTest("Agent BCI Answer Call Test");
        System.out.println("Starting Agent BCI Answer Call Test");

        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("Hanhphm", "********");
        AgentBCIPage agentBCIPage = new AgentBCIPage(driver, wait, test);
        agentBCIPage.answerCall();

        // Prefer BaseTest1 to attach pass/fail; also print to console
        System.out.println("Cuộc gọi được answer thành công.");
        // if (test != null) test.pass("Cuộc gọi được answer thành công.");
    }

    // Lifecycle (report/browser) and utilities are provided by BaseTest1
}
