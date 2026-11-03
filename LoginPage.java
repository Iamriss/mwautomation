package id.co.juaracoding.selenium.pages;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.*;
public class LoginPage extends BasePage {
 private final By username=By.cssSelector("#login-username,[data-testid='login-username-input']");
 private final By password=By.cssSelector("#login-password,[data-testid='login-password-input']");
 public LoginPage(WebDriver driver){super(driver);}
 public LoginPage open(){driver.get(TestConfig.BASE_URL+"/login");return this;}
 public void login(String user,String pass){type(username,user);type(password,pass);type(By.cssSelector("#login-captcha,[data-testid='login-captcha-input']"),captchaValue("login-captcha-value"));click(By.cssSelector("[data-testid='login-submit-button']"));}
 public boolean isLoginVisible(){return !driver.findElements(username).isEmpty();}
 public String currentUrl(){return driver.getCurrentUrl();}
 public String pageText(){return driver.findElement(By.tagName("body")).getText();}
}
