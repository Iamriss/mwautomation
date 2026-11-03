package id.co.juaracoding.selenium;
import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseSeleniumTest {
    @Test
    public void login_web_valid_credentials_reaches_authenticated_page() {
        LoginPage page = new LoginPage(driver).open();
        page.login(TestConfig.ADMIN_USERNAME, TestConfig.ADMIN_PASSWORD);
        Assert.assertFalse(page.currentUrl().contains("/login"), "User seharusnya meninggalkan halaman login setelah autentikasi berhasil.");
    }
    @Test
    public void login_web_invalid_password_stays_unauthenticated() {
        LoginPage page = new LoginPage(driver).open();
        page.login(TestConfig.ADMIN_USERNAME, "WrongPass_123");
        Assert.assertTrue(page.currentUrl().contains("login") || page.pageText().toLowerCase().contains("password") || page.pageText().toLowerCase().contains("gagal"), "Login invalid tidak boleh masuk ke halaman terautentikasi.");
    }
}
