package testcases;

import base.BaseTest1;
import org.testng.annotations.Test;
import pages.AgentBCIPage;
import pages.LoginPage;

public class RsaEcomAgentBCITest extends BaseTest1 {
    @Test
    public void runFlow() {
        test = extent.createTest("Agent BCI Answer Call Test");
        LoginPage loginPage = new LoginPage(driver, wait, test);
        loginPage.login("Hanhphm", "********");
        AgentBCIPage agentBCIPage = new AgentBCIPage(driver, wait, test);
        agentBCIPage.answerCall();
        test.pass("Cuộc gọi được answer thành công.");
    }

    // Lifecycle (report/browser) and utilities are provided by BaseTest1
}
