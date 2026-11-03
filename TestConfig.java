package id.co.juaracoding.util;

public final class TestConfig {
    private TestConfig() {}
    public static final String BASE_URL = System.getProperty("base.url", "http://localhost:8080");
    public static final String BASE_API_URL = BASE_URL;
    // Default license key from the supplied Simple Apps README. Override with -Dapi.key=... if changed.
    public static final String X_API_KEY = System.getProperty("api.key", "d461265dd7323fef9755bb3257275d67");
    public static final String ADMIN_USERNAME = System.getProperty("admin.username", "admin1");
    public static final String ADMIN_PASSWORD = System.getProperty("admin.password", "Admin1@123");
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "false"));
    public static final int WAIT_SECONDS = Integer.parseInt(System.getProperty("wait.seconds", "15"));
}
