package id.co.juaracoding.cucumber.web;
import id.co.juaracoding.util.TestConfig;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebHooks {
    private final WebDriverContext context;
    public WebHooks(WebDriverContext context) { this.context = context; }
    @Before public void startBrowser() {
        ChromeOptions options = new ChromeOptions(); options.addArguments("--start-maximized", "--disable-notifications");
        if (TestConfig.HEADLESS) options.addArguments("--headless=new", "--window-size=1440,1000");
        WebDriver driver = new ChromeDriver(options); context.setDriver(driver); driver.get(TestConfig.BASE_URL);
        if (driver.getCurrentUrl().matches(".*\\/(license|env|cloudinary).*")) { driver.quit(); context.setDriver(null); throw new IllegalStateException("Selesaikan gerbang lisensi/.env/Cloudinary sebelum Cucumber Web."); }
    }
    @After public void stopBrowser(Scenario scenario) { WebDriver d=context.getDriver(); if(d!=null){ if(scenario.isFailed()){ try { scenario.attach(((org.openqa.selenium.TakesScreenshot)d).getScreenshotAs(org.openqa.selenium.OutputType.BYTES),"image/png","failure"); } catch(Exception ignored){} } d.quit(); context.setDriver(null); } }
}
