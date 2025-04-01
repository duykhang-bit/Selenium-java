import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class test {
    public static void main(String[] args) {
        // Tự động tải ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Cấu hình ChromeOptions để fake User-Agent
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("--disable-blink-features=AutomationControlled"); // Ẩn Selenium khỏi bị phát hiện
        options.addArguments("--incognito"); // Mở ẩn danh tránh lưu cache

        // Mở Chrome với cấu hình trên
        WebDriver driver = new ChromeDriver(options);

        // Điều hướng đến Google
        driver.get("https://www.google.com");

        // Tìm ô tìm kiếm và nhập "Selenium WebDriver"
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");

        // Nhấn Enter
        searchBox.submit();

        // Đợi 3 giây rồi đóng trình duyệt
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        //driver.quit();
    }
}

