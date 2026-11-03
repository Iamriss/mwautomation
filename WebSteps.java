package id.co.juaracoding.cucumber.web;

import id.co.juaracoding.selenium.pages.*;
import id.co.juaracoding.util.TestConfig;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.util.UUID;

public class WebSteps {
    private WebDriver driver;
    private final WebDriverContext context;
    private LoginPage loginPage;
    private String resultText;
    public WebSteps(WebDriverContext context) { this.context = context; }

    @Given("user berada di halaman login web")
    public void user_on_login_page() { driver = context.getDriver(); loginPage = new LoginPage(driver).open(); }
    @When("user login web dengan kredensial valid")
    public void user_login_with_valid_credentials() { loginPage.login(TestConfig.ADMIN_USERNAME, TestConfig.ADMIN_PASSWORD); }
    @Then("user web berhasil masuk")
    public void user_web_is_authenticated() { Assert.assertFalse(driver.getCurrentUrl().contains("/login"), "Expected user to leave login page"); }

    @Given("user berada di halaman registrasi web")
    public void user_on_register_page() { driver = context.getDriver(); driver.get(TestConfig.BASE_URL + "/register"); }
    @When("user mengirim form registrasi web yang valid")
    public void submit_valid_web_registration() {
        String id = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        RegisterPage page = new RegisterPage(driver);
        page.register("bdd" + id, "QaTest_123", "BDD Automation", "bdd" + id + "@example.test", String.format("08%010d", Math.abs(System.nanoTime()) % 10_000_000_000L), "Jl. Automation Testing No. 123, Jakarta", "1998-01-15", "OTHER", "S1", "O", String.format("3174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), String.format("9174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), "12345");
        resultText = page.pageText();
    }
    @Then("aplikasi web memberikan respons registrasi")
    public void web_registration_responds() { Assert.assertNotNull(resultText); Assert.assertFalse(resultText.isBlank()); }

    @Given("user berada di halaman lupa password web")
    public void user_on_forgot_page() { driver = context.getDriver(); driver.get(TestConfig.BASE_URL + "/forgot-password"); }
    @When("user mengirim email terdaftar pada form lupa password web")
    public void submit_registered_email_web() {
        String email = System.getProperty("registered.email");
        if (email == null || email.isBlank()) {
            String id = UUID.randomUUID().toString().replace("-", "").substring(0,8);
            driver.get(TestConfig.BASE_URL + "/register");
            new RegisterPage(driver).register("bddforgot" + id, "QaTest_123", "BDD Forgot Tester", "bddforgot" + id + "@example.test", String.format("08%010d", Math.abs(System.nanoTime()) % 10_000_000_000L), "Jl. Automation Testing No. 123, Jakarta", "1998-01-15", "OTHER", "S1", "O", String.format("3174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), String.format("9174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), "12345");
            email = "bddforgot" + id + "@example.test";
        }
        ForgotPasswordPage page = new ForgotPasswordPage(driver); page.open(); page.submitEmail(email); resultText = page.pageText();
    }
    @Then("aplikasi web memberikan respons lupa password")
    public void forgot_password_responds() { Assert.assertNotNull(resultText); Assert.assertFalse(resultText.isBlank()); }
}
