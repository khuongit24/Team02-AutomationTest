package com.Pages;

/**
 * Test config cho test
 */
public final class TestConfig {
    // ====== WebDriver Configuration ======
    public static final String EDGE_DRIVER_PATH = "T:\\Edge-Webdriver\\msedgedriver.exe";
    public static final String CHROME_DRIVER_PATH = "T:\\ChromeDriver\\chromedriver.exe";

    // ====== Application URLs ======
    public static final String BASE_URL = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";
    public static final String SEMESTER_PAGE_PATH = "/term/semester"; //hết xài rồi mà giữ lại cho chắc

    // ====== Authentication ======
    public static final String MS_EMAIL = "khuong.2274802010440@vanlanguni.vn";
    public static final String MS_PASSWORD = "Khuong@2512";

    // ====== Wait Times ======
    public static final int IMPLICIT_WAIT_SECONDS = 8;
    public static final int EXPLICIT_WAIT_SECONDS = 12;
    public static final int PAGE_LOAD_TIMEOUT_SECONDS = 30;
    public static final int LOGIN_BUTTON_TIMEOUT_SECONDS = 30;
    public static final int MS_LOGIN_WAIT_SECONDS = 15; // (legacy)
    public static final int MFA_WAIT_SECONDS = 20;
    public static final int SHORT_SLEEP_MS = 350;

    // ====== Test Data ======
    public static final String DEFAULT_SEMESTER_CODE = "2";
    public static final String DEFAULT_START_YEAR = "2025";
    public static final String DEFAULT_END_YEAR = "2026";

    // ====== Browser Configuration ======
    public static final boolean HEADLESS = false;

    // ====== Timeout/Checks ======
    public static final int LOGIN_CHECK_TIMEOUT_SECONDS = 5;

    private TestConfig() {}
}
