package id.co.juaracoding.cucumber.api;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.*;

public class ApiSteps {
    private Response response;
    private String registeredEmail;
    public ApiSteps() { RestAssured.baseURI = TestConfig.BASE_API_URL; }
    private String[] captcha() {
        Response r=RestAssured.given().header("X-API-KEY",TestConfig.X_API_KEY).get("/api/v1/captcha");
        Assert.assertEquals(r.statusCode(),200); return new String[]{r.jsonPath().getString("data.captchaValue"),r.jsonPath().getString("data.captchaHash")};
    }
    private io.restassured.specification.RequestSpecification request(){return RestAssured.given().header("X-API-KEY",TestConfig.X_API_KEY).contentType("application/json").accept("application/json");}
    @Given("API client siap digunakan") public void api_client_ready() {}
    @When("client mengirim login API dengan kredensial valid") public void api_login_valid(){String[] c=captcha(); response=request().body(Map.of("username",TestConfig.ADMIN_USERNAME,"password",RsaHelper.encrypt(TestConfig.ADMIN_PASSWORD),"captchaAnswer",c[0],"captchaHash",c[1])).post("/api/v1/login");}
    @When("client membuat akun baru melalui API") public void api_register_valid(){String[] c=captcha();String s=String.valueOf(Math.abs(System.nanoTime())).substring(0,10);registeredEmail="bdd"+s+"@example.test";Map<String,Object>b=new LinkedHashMap<>();b.put("username","bdd"+s);b.put("password",RsaHelper.encrypt("QaTest_123"));b.put("fullName","BDD API Tester");b.put("email",RsaHelper.encrypt(registeredEmail));String digits=String.format("%012d",Math.abs(System.nanoTime())%1_000_000_000_000L);b.put("phoneNumber",RsaHelper.encrypt("08"+digits.substring(0,10)));b.put("address","Jl. Automation Testing No. 123, Jakarta");b.put("birthDate",RsaHelper.encrypt("1998-01-15"));b.put("gender","OTHER");b.put("lastEducation","S1");b.put("bloodType","O");b.put("idCardNumber",RsaHelper.encrypt("3174"+digits));b.put("taxIdNumber",RsaHelper.encrypt("9174"+digits));b.put("postalCode","12345");b.put("captchaAnswer",c[0]);b.put("captchaHash",c[1]);response=request().body(b).post("/api/v1/register");}
    @When("client meminta reset password untuk email akun baru") public void api_forgot_registered_email(){if(registeredEmail==null)api_register_valid();String magic=response.jsonPath().getString("data.magicLink");if(magic!=null&&magic.contains("token=")){String token=magic.substring(magic.indexOf("token=")+6).split("[&#]",2)[0];try{token=java.net.URLDecoder.decode(token,java.nio.charset.StandardCharsets.UTF_8);}catch(Exception ignored){}request().body(Map.of("token",token)).post("/api/v1/activate");}String[]c=captcha();response=request().body(Map.of("email",registeredEmail,"captchaAnswer",c[0],"captchaHash",c[1])).post("/api/v1/forgot-password");}
    @Then("response API menunjukkan status sukses") public void api_response_success(){Assert.assertEquals(response.statusCode(),200);Assert.assertEquals(response.jsonPath().getString("status"),"SUCCESS",response.asString());}
}
