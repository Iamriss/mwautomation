package id.co.juaracoding.selenium.pages;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.*;
public class ForgotPasswordPage extends BasePage {
 public ForgotPasswordPage(WebDriver driver){super(driver);}
 public void open(){driver.get(TestConfig.BASE_URL+"/forgot-password");}
 public void submitEmail(String email){type(By.cssSelector("#forgot-email,[data-testid='forgot-password-email-input']"),email);type(By.cssSelector("#forgot-captcha,[data-testid='forgot-password-captcha-input']"),captchaValue("forgot-password-captcha-value"));click(By.cssSelector("[data-testid='forgot-password-submit-button']"));}
 public String pageText(){return driver.findElement(By.tagName("body")).getText();}
}
