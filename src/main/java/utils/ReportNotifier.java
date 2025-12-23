package utils;

import java.io.File;

public class ReportNotifier {
    private static final String REPO_URL = "https://duykhang-bit.github.io/Selenium-java/";
    
    public static String getReportUrl() {
        // Lấy tên file report mới nhất
        File reportDir = new File("test-output");
        File[] files = reportDir.listFiles((dir, name) -> name.endsWith(".html"));
        
        if (files != null && files.length > 0) {
            File latestReport = files[0];
            for (File file : files) {
                if (file.lastModified() > latestReport.lastModified()) {
                    latestReport = file;
                }
            }
            // Trả về URL GitHub Pages
            return REPO_URL + latestReport.getName();
        }
        return REPO_URL;
    }
    
    public static void showReportLink() {
        String reportUrl = getReportUrl();
        System.out.println("\n=== TEST REPORT ===");
        System.out.println("Report URL: " + reportUrl);
        System.out.println("Share this URL to view the report");
        System.out.println("==================\n");
    }
} 