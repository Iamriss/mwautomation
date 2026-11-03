package id.co.juaracoding.restassured;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class ForgotPasswordApiTest extends BaseRestAssuredTest {
    @Test
    public void forgot_password_registered_email_returns_success() {
        String[] registrationCaptcha = captcha();
        Map<String,Object> registration = ApiTestData.registrationPayload(registrationCaptcha);
        String email = (String) registration.get("_testEmail"); registration.remove("_testEmail");
        Response registerResponse = request().body(registration).post("/api/v1/register");
        registerResponse.then().statusCode(200).body("status", equalTo("SUCCESS"));
        String magicLink = registerResponse.jsonPath().getString("data.magicLink");
        if (magicLink != null && magicLink.contains("token=")) {
            String token = magicLink.substring(magicLink.indexOf("token=") + 6).split("[&#]", 2)[0];
            token = URLDecoder.decode(token, StandardCharsets.UTF_8);
            request().body(Map.of("token", token)).post("/api/v1/activate").then().statusCode(200).body("status", equalTo("SUCCESS"));
        }
        String[] forgotCaptcha = captcha();
        Map<String,Object> body = Map.of("email", email, "captchaAnswer", forgotCaptcha[0], "captchaHash", forgotCaptcha[1]);
        request().body(body).post("/api/v1/forgot-password").then().statusCode(200).body("status", equalTo("SUCCESS"));
    }

    @Test
    public void forgot_password_unknown_email_returns_error_code() {
        String[] captcha = captcha();
        Map<String,Object> body = Map.of("email", "not-registered-" + System.nanoTime() + "@example.test", "captchaAnswer", captcha[0], "captchaHash", captcha[1]);
        request().body(body).post("/api/v1/forgot-password").then().statusCode(200).body("status", equalTo("ERROR")).body("error_code", not(isEmptyOrNullString()));
    }
}
