package id.co.juaracoding.selenium;
import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.UUID;

public class RegistrasiTest extends BaseSeleniumTest {
    @Test
    public void register_web_valid_data_submits_registration() {
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        driver.get(TestConfig.BASE_URL + "/register");
        RegisterPage page = new RegisterPage(driver);
        page.register("webqa" + id, "QaTest_123", "Automation Tester", "webqa" + id + "@example.test", String.format("08%010d", Math.abs(System.nanoTime()) % 10_000_000_000L), "Jl. Automation Testing No. 123, Jakarta", "1998-01-15", "OTHER", "S1", "O", String.format("3174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), String.format("9174%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L), "12345");
        Assert.assertTrue(page.pageText().length() > 0, "Halaman harus memberikan respons setelah submit registrasi.");
    }
}
