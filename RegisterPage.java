package id.co.juaracoding.selenium.pages;
import org.openqa.selenium.*;
public class RegisterPage extends BasePage {
 public RegisterPage(WebDriver driver){super(driver);}
 public void register(String username,String password,String fullName,String email,String phone,String address,String birthDate,String gender,String education,String bloodType,String nik,String npwp,String postalCode){
  fill("reg-username",username);fill("reg-password",password);fill("reg-full-name",fullName);fill("reg-email",email);fill("reg-phone",phone);fill("reg-address",address);type(By.id("reg-birth-date"),birthDate);
  click(By.id("reg-gender"));String genderLabel = gender.equals("OTHER") ? "Lainnya" : gender;click(By.xpath("//*[@role='option' and normalize-space()='"+genderLabel+"']"));
  fill("reg-ktp",nik);fill("reg-npwp",npwp);fill("reg-postal",postalCode);
  type(By.cssSelector("#reg-captcha,[data-testid='register-captcha-input']"),captchaValue("register-captcha-value"));click(By.cssSelector("[data-testid='register-submit-button']"));
 }
 private void fill(String id,String value){type(By.id(id),value);}
 public String pageText(){return driver.findElement(By.tagName("body")).getText();}
}
