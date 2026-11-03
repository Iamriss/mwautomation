package id.co.juaracoding.selenium.pages;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
public abstract class BasePage {
 protected final WebDriver driver; protected final WebDriverWait wait;
 protected BasePage(WebDriver driver){this.driver=driver;this.wait=new WebDriverWait(driver,Duration.ofSeconds(TestConfig.WAIT_SECONDS));}
 protected WebElement visible(By by){return wait.until(ExpectedConditions.visibilityOfElementLocated(by));}
 protected void type(By by,String value){WebElement e=visible(by);e.clear();e.sendKeys(value);}
 protected void click(By by){wait.until(ExpectedConditions.elementToBeClickable(by)).click();}
 protected String captchaValue(String testId){String value=visible(By.cssSelector("[data-testid='"+testId+"']")).getText().trim();if(value.isBlank())throw new IllegalStateException("Captcha testing hint tidak ditemukan: "+testId);return value;}
}
