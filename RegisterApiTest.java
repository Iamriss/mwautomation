package id.co.juaracoding.restassured;

import org.testng.annotations.Test;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class RegisterApiTest extends BaseRestAssuredTest {
    @Test
    public void register_api_success_creates_new_account() {
        Map<String,Object> body = ApiTestData.registrationPayload(captcha());
        body.remove("_testEmail");
        request().body(body).when().post("/api/v1/register").then().statusCode(200).body("status", equalTo("SUCCESS")).body("data", notNullValue());
    }

    @Test
    public void register_api_invalid_identity_number_returns_error_code() {
        Map<String,Object> body = ApiTestData.registrationPayload(captcha());
        body.put("idCardNumber", id.co.juaracoding.restassured.util.RsaHelper.encrypt("123")); // Encrypt the invalid value; the API validates the decrypted field.
        body.remove("_testEmail");
        request().body(body).when().post("/api/v1/register").then().statusCode(200).body("status", equalTo("ERROR")).body("error_code", not(isEmptyOrNullString()));
    }
}
