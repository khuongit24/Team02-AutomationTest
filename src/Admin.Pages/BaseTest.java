package com.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
    * Base test class cung cấp thiết lập WebDriver và các hàm hỗ trợ chung như đăng nhập, bypass 2FA của trường.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected HocKyPage hocKyPage;
    protected TroGiangPage troGiangPage;

    @BeforeClass
    public void setUp() {
        initializeWebDriver();
        configureDriver();
        initializePageObjects();
    }

    private void initializeWebDriver() {
        boolean localDriverConfigured = false;
        String configuredPath = TestConfig.CHROME_DRIVER_PATH;
        if (configuredPath != null && !configuredPath.isEmpty()) {
            java.nio.file.Path p = java.nio.file.Paths.get(configuredPath);
            if (java.nio.file.Files.exists(p)) {
                System.setProperty("webdriver.chrome.driver", p.toString());
                localDriverConfigured = true;
            }
        }
        if (!localDriverConfigured) {
            try { WebDriverManager.chromedriver().setup(); } catch (Exception e) { handleWebDriverManagerFailure(e); }
        }
        ChromeOptions options = createChromeOptions();
        driver = new ChromeDriver(options);
    }

    private ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--disable-dev-shm-usage","--no-sandbox","--disable-features=OptimizationHints,Translate","--disable-extensions","--no-default-browser-check","--no-first-run","--disable-blink-features=AutomationControlled");
        if (TestConfig.HEADLESS) {
            options.addArguments("--headless=new","--disable-gpu","--window-size=1920,1080");
        }
        return options;
    }

    private void handleWebDriverManagerFailure(Exception e) {
        if (TestConfig.CHROME_DRIVER_PATH != null && !TestConfig.CHROME_DRIVER_PATH.isEmpty()) {
            java.nio.file.Path driverPath = java.nio.file.Paths.get(TestConfig.CHROME_DRIVER_PATH);
            if (java.nio.file.Files.exists(driverPath)) {
                System.setProperty("webdriver.chrome.driver", driverPath.toString());
                return;
            }
        }
        throw new RuntimeException("Cannot initialize ChromeDriver: " + e.getMessage(), e);
    }

    private void configureDriver() {
        driver.manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(TestConfig.IMPLICIT_WAIT_SECONDS))
            .pageLoadTimeout(Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS))
            .scriptTimeout(Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
    }

    private void initializePageObjects() {
        hocKyPage = new HocKyPage(driver, TestConfig.EXPLICIT_WAIT_SECONDS);
        troGiangPage = new TroGiangPage(driver, TestConfig.EXPLICIT_WAIT_SECONDS);
    }

    protected void bypassInsecureInterstitialIfPresent() {
        try {
            String[] buttonSelectors = {"#details-button","#proceed-link","button#advancedButton","#invalidcert_continue","button#details-button"};
            for (String selector : buttonSelectors) {
                try {
                    WebElement element = driver.findElement(By.cssSelector(selector));
                    if (element.isDisplayed()) { element.click(); break; }
                } catch (Exception ignore) {}
            }
        } catch (Exception ignored) {}
        tryChromiumBypassTrick();
    }

    private void tryChromiumBypassTrick() {
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("if (document.body){'thisisunsafe'.split('').forEach(k=>document.body.dispatchEvent(new KeyboardEvent('keydown',{key:k})));}" );
            }
        } catch (Exception ignored) {}
    }

    protected void navigateAndLogin() throws InterruptedException {
        hocKyPage.openUrl(TestConfig.BASE_URL);
        bypassInsecureInterstitialIfPresent();
        waitForPageLoad();
        if (checkIfAlreadyLoggedIn()) { System.out.println("✓ Already logged in"); return; }
        System.out.println("→ Not logged in, starting Microsoft login flow...");
        clickLoginButtonWithRetry();
        performMicrosoftLogin();
        waitForManualMFA();
        waitForPageLoad();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
        try { wait.until(d -> checkIfAlreadyLoggedIn()); System.out.println("✓ Login completed after MFA"); }
        catch (Exception ex) { throw new RuntimeException("Không thể xác nhận trạng thái đăng nhập sau 2FA.", ex); }
    }

    private void performMicrosoftLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Math.max(60, TestConfig.PAGE_LOAD_TIMEOUT_SECONDS)));
        try {
            By emailBy = By.cssSelector("#i0116, input[name='loginfmt']");
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailBy));
            emailInput.clear(); emailInput.sendKeys(TestConfig.MS_EMAIL);
            By nextBtnBy = By.id("idSIButton9");
            wait.until(ExpectedConditions.elementToBeClickable(nextBtnBy)).click();
            By passBy = By.cssSelector("#i0118, input[name='passwd']");
            WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passBy));
            passInput.clear(); passInput.sendKeys(TestConfig.MS_PASSWORD);
            wait.until(ExpectedConditions.elementToBeClickable(nextBtnBy)).click();
            try { WebElement staySignedIn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(nextBtnBy)); staySignedIn.click(); } catch (Exception ignored) {}
        } catch (Exception e) { throw new RuntimeException("Lỗi đăng nhập Microsoft: " + e.getMessage(), e); }
    }

    private void waitForManualMFA() {
        int maxWaitSec = Math.max(5, TestConfig.MFA_WAIT_SECONDS);
        System.out.println("⏳ Đang đợi bạn xác thực 2FA (tối đa " + maxWaitSec + " giây)...");
        long deadline = System.currentTimeMillis() + maxWaitSec * 1000L; long lastTick = 0;
        while (System.currentTimeMillis() < deadline) {
            try { if (checkIfAlreadyLoggedIn()) { System.out.println("✓ Phát hiện đã đăng nhập – tiếp tục."); return; } } catch (Exception ignored) {}
            long now = System.currentTimeMillis(); if (now - lastTick >= 1000) { System.out.print("."); lastTick = now; }
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }
        System.out.println(); System.out.println("→ Hết thời gian chờ 2FA – tiếp tục.");
    }

    private boolean checkIfAlreadyLoggedIn() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.LOGIN_CHECK_TIMEOUT_SECONDS));
            try { By loginButton = By.xpath("//*[@id=\"submitlogin\"]"); boolean loginButtonPresent = driver.findElements(loginButton).size() > 0; if (!loginButtonPresent) return true; } catch (Exception e) {}
            try { By mainMenu = By.xpath("/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul"); shortWait.until(ExpectedConditions.presenceOfElementLocated(mainMenu)); return true; } catch (Exception e) { return false; }
        } catch (Exception e) { return false; }
    }

    private void waitForPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT_SECONDS));
            wait.until(webDriver -> {
                if (!(webDriver instanceof JavascriptExecutor)) return true;
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                return "complete".equals(js.executeScript("return document.readyState"));
            });
            Thread.sleep(1000);
        } catch (Exception e) { System.out.println("Warning: Page load wait timeout or interrupted"); }
    }

    private void clickLoginButtonWithRetry() {
        By loginButton = By.id("submitlogin"); long deadline = System.currentTimeMillis() + TestConfig.LOGIN_BUTTON_TIMEOUT_SECONDS * 1000L; int attempt = 0; String originalWindow = driver.getWindowHandle(); boolean success = false;
        while (System.currentTimeMillis() < deadline) {
            attempt++;
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                shortWait.until(ExpectedConditions.presenceOfElementLocated(loginButton));
                WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(loginButton));
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                }
                try { btn.click(); } catch (org.openqa.selenium.StaleElementReferenceException sere) { continue; } catch (org.openqa.selenium.ElementClickInterceptedException intercept) { if (driver instanceof JavascriptExecutor) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); } catch (Exception genericClick) { if (driver instanceof JavascriptExecutor) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); }
                success = waitForMicrosoftLoginStart(originalWindow);
                if (success) { System.out.println("✓ Login button activated (Microsoft login) after attempt " + attempt); break; }
                String href = null; try { href = btn.getAttribute("href"); } catch (Exception ignored) {}
                if (href != null && !href.isBlank() && !driver.getCurrentUrl().contains("login.microsoftonline.com")) {
                    System.out.println("[Attempt " + attempt + "] Direct navigate via href fallback: " + href);
                    driver.get(href); success = waitForMicrosoftLoginStart(originalWindow); if (success) { System.out.println("✓ Direct navigation triggered Microsoft login (attempt " + attempt + ")"); break; }
                }
            } catch (org.openqa.selenium.TimeoutException te) { System.out.println("[Attempt " + attempt + "] Timeout waiting for login button – retry..."); } catch (Exception e) { System.out.println("[Attempt " + attempt + "] Unexpected error: " + e.getMessage()); }
            try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        }
        if (!success) throw new RuntimeException("Failed to click login button: did not reach Microsoft login page");
    }

    private boolean waitForMicrosoftLoginStart(String originalWindow) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            for (int i = 0; i < 5; i++) {
                if (driver.getWindowHandles().size() > 1) {
                    for (String handle : driver.getWindowHandles()) { if (!handle.equals(originalWindow)) { driver.switchTo().window(handle); break; } }
                    break;
                }
                Thread.sleep(300);
            }
            boolean urlOk = driver.getCurrentUrl().toLowerCase().contains("login.microsoftonline.com");
            if (urlOk) return true;
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("#i0116, input[name='loginfmt']")),
                    ExpectedConditions.urlContains("login.microsoftonline.com")
                ));
                return true;
            } catch (Exception ignored) { return false; }
        } catch (Exception e) { return false; }
    }

    // ===== Helpers cho các test học kỳ hiện có =====
    protected void navigateToSemesterPage() throws InterruptedException {
        hocKyPage.openMenuHocKyNganhKhoa();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        hocKyPage.openSubmenuHocKy();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
    }

    // ===== Helpers cho flow TroGiang =====
    protected void navigateToTroGiangForm() throws InterruptedException {
        troGiangPage.openMenuTroGiang();
        shortSleep();
        troGiangPage.openSubmenuBieuMauDangKy();
        shortSleep();
    }

    protected void shortSleep() throws InterruptedException { Thread.sleep(TestConfig.SHORT_SLEEP_MS); }

    @AfterClass(alwaysRun = true)
    public void tearDown() { if (driver != null) { try { driver.quit(); } catch (Exception e) { System.err.println("Error during driver cleanup: " + e.getMessage()); } } }
}
