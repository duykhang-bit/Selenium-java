package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("reports/ExtentReport.html");

            spark.config().setReportName("Selenium Automation Report");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "CI");
        }
        return extent;
    }
}
