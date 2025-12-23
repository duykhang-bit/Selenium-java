package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String path = "reports/screenshots/" + testName + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs();

            Files.copy(src.toPath(), dest.toPath());
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}
