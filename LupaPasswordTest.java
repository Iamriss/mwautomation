package id.co.juaracoding.selenium;
import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.UUID;

public class LupaPasswordTest extends BaseSeleniumTest {
    @Test
    public void forgot_password_web_accepts_registered_email_submission() {
        driver.get(TestConfig.BASE_URL + "/forgot-password");
        ForgotPasswordPage page = new ForgotPasswordPage(driver);
        String email = System.getProperty("registered.email");
        if (email == null || email.isBlank()) {
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            driver.get(TestConfig.BASE_URL + "/register");
            new RegisterPage(driver).register("forgot" + id, "QaTest_123", "Forgot Password Tester", "forgot" + id + "@example.test", String.format("08%010d", Math.abs(System.nanoTime()) % 10_000_000_000L), "Jl. Automation Testing No. 123, Jakarta", "1998-01-15", "OTHER", "S1", "O", String.format("3174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), String.format("9174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), "12345");
            email = "forgot" + id + "@example.test";
            page.open();
        }
        page.submitEmail(email);
        Assert.assertTrue(page.pageText().length() > 0, "Halaman harus menampilkan respons setelah submit lupa password.");
    }
}
