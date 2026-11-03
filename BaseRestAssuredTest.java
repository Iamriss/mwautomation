package id.co.juaracoding.restassured;

import id.co.juaracoding.util.TestConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseRestAssuredTest {
    @BeforeClass(alwaysRun = true)
    public void configureRestAssured() { RestAssured.baseURI = TestConfig.BASE_API_URL; }
    protected RequestSpecification request() {
        return RestAssured.given().header("X-API-KEY", TestConfig.X_API_KEY).contentType("application/json").accept("application/json");
    }
    protected String[] captcha() {
        var response = request().get("/api/v1/captcha");
        response.then().statusCode(200);
        String value = response.jsonPath().getString("data.captchaValue");
        String hash = response.jsonPath().getString("data.captchaHash");
        if (value == null || hash == null) throw new AssertionError("Captcha value/hash tidak tersedia. Periksa response GET /api/v1/captcha.");
        return new String[]{value, hash};
    }
}
