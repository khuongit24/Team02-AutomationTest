package com.admintest;

/**
 * File config chung cho các test.
 * Tối ưu hóa: loại bỏ các config về profile không cần thiết.
 */
public final class TestConfig {
    // ====== WebDriver Configuration ======
    /**
     * Đường dẫn đến Edge WebDriver (fallback khi WebDriverManager offline)
     */
    public static final String EDGE_DRIVER_PATH = "T:\\Edge-Webdriver\\msedgedriver.exe";
    
    // ====== Application URLs ======
    /**
     * Base URL của ứng dụng test
     */
    public static final String BASE_URL = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";
    
    /**
     * Semester management page URL (relative)
     */
    public static final String SEMESTER_PAGE_PATH = "/term/semester";
    
    // ====== Authentication ======
    /**
     * Microsoft Email để đăng nhập
     */
    public static final String MS_EMAIL = "khuong.2274802010440@vanlanguni.vn";
    
    /**
     * Microsoft Password để đăng nhập
     */
    public static final String MS_PASSWORD = "Khuong@2512";
    
    // ====== Wait Times ======
    /**
     * Implicit wait timeout (seconds)
     */
    public static final int IMPLICIT_WAIT_SECONDS = 8;
    
    /**
     * Explicit wait timeout (seconds)
     */
    public static final int EXPLICIT_WAIT_SECONDS = 12;
    
    /**
     * Page load timeout - thời gian đợi trang load hoàn toàn (seconds)
     */
    public static final int PAGE_LOAD_TIMEOUT_SECONDS = 30;
    
    /**
     * Login button timeout - thời gian đợi nút đăng nhập xuất hiện (seconds)
     */
    public static final int LOGIN_BUTTON_TIMEOUT_SECONDS = 30;
    
    /**
     * Thời gian chờ sau khi click đăng nhập Microsoft (seconds)
     */
    public static final int MS_LOGIN_WAIT_SECONDS = 15;
    
    /**
     * Thời gian sleep ngắn giữa các thao tác (milliseconds)
     */
    public static final int SHORT_SLEEP_MS = 350;
    
    // ====== Test Data ======
    /**
     * Mã học kỳ mặc định (ví dụ: "2" cho học kỳ 2)
     */
    public static final String DEFAULT_SEMESTER_CODE = "2";
    
    /**
     * Năm bắt đầu mặc định
     */
    public static final String DEFAULT_START_YEAR = "2025";
    
    /**
     * Năm kết thúc mặc định
     */
    public static final String DEFAULT_END_YEAR = "2026";
    
    // ====== Browser Configuration ======
    /**
     * Chạy headless để phù hợp môi trường CI hoặc máy không có GUI.
     * Đặt false nếu muốn thấy trình duyệt.
     */
    public static final boolean HEADLESS = false;
    
    private TestConfig() {
        // Prevent instantiation
    }
}
