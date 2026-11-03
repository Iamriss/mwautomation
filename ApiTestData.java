package id.co.juaracoding.restassured;

import id.co.juaracoding.restassured.util.RsaHelper;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

final class ApiTestData {
    private ApiTestData() {}
    static Map<String,Object> registrationPayload(String[] captcha) {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("username", "qa" + suffix);
        body.put("password", RsaHelper.encrypt("QaTest_123"));
        body.put("fullName", "Automation Tester");
        body.put("email", RsaHelper.encrypt("qa" + suffix + "@example.test"));
        String digits = String.format("%012d", Math.abs(System.nanoTime()) % 1_000_000_000_000L);
        body.put("phoneNumber", RsaHelper.encrypt("08" + digits.substring(0, 10)));
        body.put("address", "Jl. Automation Testing No. 123, Jakarta");
        body.put("birthDate", RsaHelper.encrypt("1998-01-15"));
        body.put("gender", "OTHER");
        body.put("lastEducation", "S1");
        body.put("bloodType", "O");
        body.put("idCardNumber", RsaHelper.encrypt("3174" + digits));
        body.put("taxIdNumber", RsaHelper.encrypt("9174" + digits));
        body.put("postalCode", "12345");
        body.put("captchaAnswer", captcha[0]);
        body.put("captchaHash", captcha[1]);
        body.put("_testEmail", "qa" + suffix + "@example.test");
        return body;
    }
}
