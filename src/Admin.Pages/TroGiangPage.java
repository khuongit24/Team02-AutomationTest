package com.Pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class TroGiangPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

   // Menu cha "Trợ giảng"
    private static final String XPATH_MENU_TRO_GIANG = "/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul/li[3]/a"; // ví dụ: //nav//a[normalize-space()='Trợ giảng']
    // Submenu "Biểu mẫu đăng ký"
    private static final String XPATH_SUBMENU_BIEU_MAU_DANG_KY = "/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul/li[3]/div/ul/li[1]/a"; // ví dụ: //nav//a[normalize-space()='Biểu mẫu đăng ký']
    // Nút "Đăng ký"
    private static final String XPATH_BUTTON_DANG_KY = "/html/body/div[2]/main/section/div[1]/div/div/div[2]/a"; // ví dụ: //button[.='Đăng ký']
    // Ô chọn ngành (dropdown hiển thị danh sách ngành)
    private static final String XPATH_DROPDOWN_NGANH = "/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[2]/div/select"; // ví dụ: //div[label[contains(.,'Ngành')]]//div[contains(@class,'select')]
    // Option ngành "Công Nghệ Thông Tin"
    private static final String XPATH_OPTION_CNTT = "/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[2]/div/select/option[2]"; // ví dụ: //div[@role='option' and normalize-space()='Công Nghệ Thông Tin']
    // Ô "Ngày mở" (click mở popup lịch)
    private static final String XPATH_INPUT_NGAY_MO = "/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[3]/div/input"; // ví dụ: //input[@placeholder='Ngày mở']
    // Ô "Ngày đóng" (click mở popup lịch)
    private static final String XPATH_INPUT_NGAY_DONG = "/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[4]/div/input"; // ví dụ: //input[@placeholder='Ngày đóng']
    // Ngày chọn trong popup lịch cho Ngày mở (bạn sẽ điền trực tiếp xpath ô ngày cụ thể)
    private static final String XPATH_CELL_NGAY_MO = "/html/body/div[4]/div[2]/div/div[2]/div/span[21]"; // ví dụ: //div[contains(@class,'day') and text()='15']
    // Ngày chọn trong popup lịch cho Ngày đóng
    private static final String XPATH_CELL_NGAY_DONG = "/html/body/div[5]/div[2]/div/div[2]/div/span[25]"; // ví dụ: //div[contains(@class,'day') and text()='30']
    // Nút "Lưu thông tin"
    private static final String XPATH_BUTTON_LUU_THONG_TIN = "/html/body/div[2]/main/section/div[3]/div/div/div[3]/button[2]"; // ví dụ: //button[.='Lưu thông tin']

    public TroGiangPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    private WebElement waitClickable(By by) { return wait.until(ExpectedConditions.elementToBeClickable(by)); }
    private WebElement waitVisible(By by) { return wait.until(ExpectedConditions.visibilityOfElementLocated(by)); }
    private By by(String xpath) { return By.xpath(xpath); }
    private void scrollIntoView(WebElement el) { try { if (driver instanceof JavascriptExecutor) { ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el); } } catch (Exception ignored) {} }
// hành động trên trang
    public void openMenuTroGiang() { waitClickable(by(XPATH_MENU_TRO_GIANG)).click(); }
    public void openSubmenuBieuMauDangKy() { waitClickable(by(XPATH_SUBMENU_BIEU_MAU_DANG_KY)).click(); }
    public void clickButtonDangKy() { waitClickable(by(XPATH_BUTTON_DANG_KY)).click(); }
    public void openDropdownNganh() { waitClickable(by(XPATH_DROPDOWN_NGANH)).click(); }
    public void chonNganhCongNgheThongTin() { waitClickable(by(XPATH_OPTION_CNTT)).click(); }
    public void openNgayMoPopup() { waitClickable(by(XPATH_INPUT_NGAY_MO)).click(); }
    public void selectNgayMoTheoXpath() { WebElement el = waitClickable(by(XPATH_CELL_NGAY_MO)); scrollIntoView(el); el.click(); }
    public void openNgayDongPopup() { waitClickable(by(XPATH_INPUT_NGAY_DONG)).click(); }
    public void selectNgayDongTheoXpath() { WebElement el = waitClickable(by(XPATH_CELL_NGAY_DONG)); scrollIntoView(el); el.click(); }
    public void clickLuuThongTin() { waitClickable(by(XPATH_BUTTON_LUU_THONG_TIN)).click(); }
//flow chính
    public void thucHienFlowDangKyTroGiang() throws InterruptedException {
        openMenuTroGiang();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        openSubmenuBieuMauDangKy();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        clickButtonDangKy();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        openDropdownNganh();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        chonNganhCongNgheThongTin();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        openNgayMoPopup();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        selectNgayMoTheoXpath();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        openNgayDongPopup();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        selectNgayDongTheoXpath();
        Thread.sleep(TestConfig.SHORT_SLEEP_MS);
        clickLuuThongTin();
    }
}
