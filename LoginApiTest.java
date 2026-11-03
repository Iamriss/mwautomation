package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import org.testng.annotations.Test;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class LoginApiTest extends BaseRestAssuredTest {
    @Test
    public void login_api_success_returns_jwt() {
        String[] captcha = captcha();
        Map<String,Object> body = Map.of("username", TestConfig.ADMIN_USERNAME, "password", RsaHelper.encrypt(TestConfig.ADMIN_PASSWORD), "captchaAnswer", captcha[0], "captchaHash", captcha[1]);
        request().body(body).when().post("/api/v1/login").then().statusCode(200).body("status", equalTo("SUCCESS")).body("data", notNullValue());
    }

    @Test
    public void login_api_invalid_password_returns_error_code() {
        String[] captcha = captcha();
        Map<String,Object> body = Map.of("username", TestConfig.ADMIN_USERNAME, "password", RsaHelper.encrypt("WrongPass_123"), "captchaAnswer", captcha[0], "captchaHash", captcha[1]);
        request().body(body).when().post("/api/v1/login").then().statusCode(200).body("status", equalTo("ERROR")).body("error_code", not(isEmptyOrNullString()));
    }
}
