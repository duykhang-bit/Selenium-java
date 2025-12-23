package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            // Cấu hình report
            spark.config().setReportName("Selenium Automation Test Report");
            spark.config().setDocumentTitle("Test Execution Report - " + timestamp);
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            spark.config().setEncoding("UTF-8");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System Information chi tiết
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("OS Architecture", System.getProperty("os.arch"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Java Vendor", System.getProperty("java.vendor"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "CI/UAT");
            extent.setSystemInfo("Framework", "Selenium WebDriver + TestNG");
            extent.setSystemInfo("Report Generated", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
        return extent;
    }
}
