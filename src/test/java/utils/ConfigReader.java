package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration from config.properties file
 * Follows Singleton pattern to ensure single instance
 */
public class ConfigReader {
    private static ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        loadProperties();
    }

    /**
     * Get singleton instance of ConfigReader
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    /**
     * Load properties from config.properties file
     */
    private void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Error loading config.properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get property value by key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default value if not found
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get property as integer
     */
    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Get property as integer with default value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    /**
     * Get property as long
     */
    public long getLongProperty(String key) {
        return Long.parseLong(getProperty(key));
    }

    /**
     * Get property as long with default value
     */
    public long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        return value != null ? Long.parseLong(value) : defaultValue;
    }

    /**
     * Get property as boolean
     */
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Get property as boolean with default value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // ============================================
    // Convenience methods for common properties
    // ============================================

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public long getExplicitWait() {
        return getLongProperty("explicit.wait", 90);
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public String getCiUrl() {
        return getProperty("url.ci");
    }

    public String getUatUrl() {
        return getProperty("url.uat");
    }

    public String getVaccineUrl() {
        return getProperty("url.vaccine");
    }

    public String getCskhUrl() {
        return getProperty("url.cskh");
    }

    public String getCiUsername() {
        return getProperty("ci.username");
    }

    public String getCiPassword() {
        return getProperty("ci.password");
    }

    public String getUatUsername() {
        return getProperty("uat.username");
    }

    public String getUatPassword() {
        return getProperty("uat.password");
    }

    public String getTestPhoneNumber() {
        return getProperty("test.phone.number");
    }

    public String getTestTransferExtension() {
        return getProperty("test.transfer.extension");
    }

    public String getTestNoteText() {
        return getProperty("test.note.text");
    }

    public String getReportPath() {
        return getProperty("report.path", "test-output/ExtentReport.html");
    }

    public String getScreenshotPath() {
        return getProperty("screenshot.path", "test-output/screenshots");
    }
}


