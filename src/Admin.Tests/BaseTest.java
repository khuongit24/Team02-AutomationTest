package com.admintest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.Pages.HocKyPage;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * BaseTest - Abstract base class cho tất cả test cases.
 * Centralize common setup/teardown và utility methods.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected HocKyPage hocKyPage;

    @BeforeClass
    public void setUp() {
        initializeWebDriver();
        configureDriver();
        initializePageObjects();
    }

    /**
     * Khởi tạo WebDriver với EdgeOptions tối ưu.
     * Sử dụng WebDriverManager để tự động quản lý driver version.
     */
    private void initializeWebDriver() {
        System.clearProperty("webdriver.edge.driver");
        
        // Try WebDriverManager first, fallback to local driver path
        try {
            WebDriverManager.edgedriver().setup();
        } catch (Exception e) {
            handleWebDriverManagerFailure(e);
        }

        EdgeOptions options = createEdgeOptions();
        driver = new EdgeDriver(options);
    }

    /**
     * Tạo EdgeOptions với các thiết lập tối ưu cho testing.
     */
    private EdgeOptions createEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        
        // Basic options
        options.addArguments("--start-maximized");
        options.setAcceptInsecureCerts(true);
        
        // Stability options
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-features=OptimizationHints,Translate");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--no-first-run");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Headless mode if configured
        if (TestConfig.HEADLESS) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        
        return options;
    }

    /**
     * Xử lý khi WebDriverManager fail - fallback về driver path local.
     */
    private void handleWebDriverManagerFailure(Exception e) {
        if (TestConfig.EDGE_DRIVER_PATH != null && !TestConfig.EDGE_DRIVER_PATH.isEmpty()) {
            java.nio.file.Path driverPath = java.nio.file.Paths.get(TestConfig.EDGE_DRIVER_PATH);
            if (java.nio.file.Files.exists(driverPath)) {
                System.setProperty("webdriver.edge.driver", driverPath.toString());
                return;
            }
        }
        throw new RuntimeException("Cannot initialize EdgeDriver: " + e.getMessage(), e);
    }

    /**
     * Cấu hình timeouts cho driver.
     */
    private void configureDriver() {
        driver.manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(TestConfig.IMPLICIT_WAIT_SECONDS))
            .pageLoadTimeout(Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS))
            .scriptTimeout(Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
    }

    /**
     * Khởi tạo Page Objects.
     */
    private void initializePageObjects() {
        hocKyPage = new HocKyPage(driver, TestConfig.EXPLICIT_WAIT_SECONDS);
    }

    /**
     * Bypass insecure certificate interstitial page (nếu có).
     * Sử dụng nhiều chiến lược để đảm bảo compatibility.
     */
    protected void bypassInsecureInterstitialIfPresent() {
        try {
            // Try clicking common interstitial buttons
            String[] buttonSelectors = {
                "#details-button",
                "#proceed-link",
                "button#advancedButton",
                "#invalidcert_continue",
                "button#details-button"
            };
            
            for (String selector : buttonSelectors) {
                try {
                    WebElement element = driver.findElement(By.cssSelector(selector));
                    if (element.isDisplayed()) {
                        element.click();
                        break;
                    }
                } catch (Exception ignore) {
                    // Continue to next selector
                }
            }
        } catch (Exception ignored) {
            // Interstitial not present or already bypassed
        }

        // Try Chromium's "thisisunsafe" trick
        tryChromiumBypassTrick();
    }

    /**
     * Thử trick "thisisunsafe" của Chromium để bypass certificate warning.
     */
    private void tryChromiumBypassTrick() {
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "if (document.body) {" +
                    "  const keys = 'thisisunsafe'.split('');" +
                    "  keys.forEach(key => {" +
                    "    document.body.dispatchEvent(new KeyboardEvent('keydown', {key: key}));" +
                    "  });" +
                    "}"
                );
            }
        } catch (Exception ignored) {
            // Trick didn't work or not needed
        }
    }

    /**
     * Navigate to application and perform initial login flow.
     * Sử dụng chiến lược đợi tối ưu để đảm bảo trang load hoàn toàn.
     */
    protected void navigateAndLogin() throws InterruptedException {
        // Navigate to URL
        hocKyPage.openUrl(TestConfig.BASE_URL);
        
        // Bypass certificate warning if present
        bypassInsecureInterstitialIfPresent();
        
        // Đợi trang load hoàn toàn trước khi tìm nút đăng nhập
        waitForPageLoad();
        
        // Đợi và click nút đăng nhập với timeout cao hơn
        clickLoginButtonWithRetry();
        
        // Đợi sau khi đăng nhập (Microsoft authentication flow)
        Thread.sleep(TestConfig.MS_LOGIN_WAIT_SECONDS * 1000L);
    }

    /**
     * Đợi trang web load hoàn toàn (document ready state).
     */
    private void waitForPageLoad() {
        try {
            org.openqa.selenium.support.ui.WebDriverWait wait = 
                new org.openqa.selenium.support.ui.WebDriverWait(driver, 
                    Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
            
            wait.until(webDriver -> {
                if (!(webDriver instanceof JavascriptExecutor)) {
                    return true;
                }
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                Object readyState = js.executeScript("return document.readyState");
                return "complete".equals(readyState);
            });
            
            // Đợi thêm một chút để các script async chạy xong
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Warning: Page load wait timeout or interrupted");
        }
    }

    /**
     * Click nút đăng nhập với retry mechanism và timeout cao hơn.
     * Sử dụng nhiều chiến lược để đảm bảo click thành công.
     */
    private void clickLoginButtonWithRetry() {
        org.openqa.selenium.support.ui.WebDriverWait longWait = 
            new org.openqa.selenium.support.ui.WebDriverWait(driver, 
                Duration.ofSeconds(TestConfig.LOGIN_BUTTON_TIMEOUT_SECONDS));
        
        try {
            // Chiến lược 1: Đợi element visible và clickable
            By loginButton = By.xpath("//*[@id=\"submitlogin\"]");
            WebElement button = longWait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(loginButton)
            );
            
            // Scroll element vào view nếu cần
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
                Thread.sleep(500);
            }
            
            // Thử click thông thường trước
            try {
                button.click();
                System.out.println("✓ Clicked login button successfully");
                return;
            } catch (Exception clickError) {
                System.out.println("Normal click failed, trying JavaScript click...");
            }
            
            // Chiến lược 2: JavaScript click nếu click thông thường fail
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", button);
                System.out.println("✓ Clicked login button via JavaScript");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR: Cannot click login button after " + 
                TestConfig.LOGIN_BUTTON_TIMEOUT_SECONDS + " seconds");
            throw new RuntimeException("Failed to click login button: " + e.getMessage(), e);
        }
    }

    /**
     * Navigate to semester management page.
     */
    protected void navigateToSemesterPage() throws InterruptedException {
        hocKyPage.openMenuHocKyNganhKhoa();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        hocKyPage.openSubmenuHocKy();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
    }

    /**
     * Utility method for short sleep between actions.
     */
    protected void shortSleep() throws InterruptedException {
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error during driver cleanup: " + e.getMessage());
            }
        }
    }
}
