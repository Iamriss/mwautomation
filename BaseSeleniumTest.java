package id.co.juaracoding.selenium;

import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseSeleniumTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUpBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications", "--remote-allow-origins=*");
        if (TestConfig.HEADLESS) options.addArguments("--headless=new", "--window-size=1440,1000");
        driver = new ChromeDriver(options); // Selenium Manager resolves the matching driver.
        driver.get(TestConfig.BASE_URL);
        if (driver.getCurrentUrl().contains("/license") || driver.getCurrentUrl().contains("/env") || driver.getCurrentUrl().contains("/cloudinary")) {
            throw new IllegalStateException("Aplikasi masih berada di gerbang konfigurasi. Selesaikan Lisensi, .env, dan Cloudinary sekali melalui browser biasa, lalu jalankan ulang test.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        if (driver != null) driver.quit();
    }
}
