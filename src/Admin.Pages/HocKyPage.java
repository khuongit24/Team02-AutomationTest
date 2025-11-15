package com.Pages;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HocKyPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Trang đầu: nút "Đăng nhập bằng Email VLU"
    private final By btnDangNhapEmailVLU = By.xpath("//*[@id=\"submitlogin\"]");

    // Menu: "Học kỳ, Ngành & Khoa"
    private final By menuHocKyNganhKhoa = By.xpath("/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul/li[5]/a");

    // Submenu: "Học kỳ"
    private final By submenuHocKy = By.xpath("/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul/li[5]/div/ul/li[1]/a");

    // Nút "Thêm mới" trên trang Học kỳ
    private final By btnThemMoi = By.xpath("/html/body/div[2]/main/section/div[1]/div/div/div[2]/a");

    // Nút "Cập nhật" trên trang Học kỳ (ví dụ nằm ở mỗi dòng bảng) – để placeholder
    // Hãy thay thế XPath bên dưới bằng locator thật sự (ví dụ: nút cập nhật của dòng đầu tiên)
    private final By btnCapNhat = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[2]/div/table/tbody/tr[1]/td[6]/a/span/i");

    // Ô nhập "Học kỳ"
    private final By inputHocKy = By.xpath("/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[1]/div/input");

    // Dropdown chọn "Năm bắt đầu"
    private final By dropdownNamBatDau = By.xpath("/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[2]/div/select");

    // TEMPLATE để chọn một option trong dropdown theo năm (không còn dùng click thuần; dùng Select thay thế)
    private static final String XPATH_OPTION_NAM_TEMPLATE = "//select[/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[2]/div/select/option[11]]";

    // Datepicker: ô chọn ngày bắt đầu (click để mở lịch)
    // Cho phép gán ID thật (vd: "startDate") hoặc để nguyên XPATH. Nếu chuỗi bắt đầu bằng '/' coi là XPath.
    private static final String LOCATOR_INPUT_NGAY_BAT_DAU = "/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[4]/div/input"; // thay bằng id nếu có, ví dụ: "startDate"
    private final By inputNgayBatDau =
            (LOCATOR_INPUT_NGAY_BAT_DAU != null && !LOCATOR_INPUT_NGAY_BAT_DAU.isEmpty())
                ? (LOCATOR_INPUT_NGAY_BAT_DAU.startsWith("/") ? By.xpath(LOCATOR_INPUT_NGAY_BAT_DAU) : By.id(LOCATOR_INPUT_NGAY_BAT_DAU))
                : By.xpath("/html/body/div[2]/main/section/div[3]/div/div/div[2]/div/div[4]/div/input");

    private static final String LOCATOR_DATEPICKER_MONTH_SELECT = "/html/body/div[4]/div[1]/div/div/select/option[8]"; 
    private static final String LOCATOR_DATEPICKER_YEAR_INPUT  = "/html/body/div[4]/div[1]/div/div/div/input"; 

    // Trường hợp cần chọn ngày theo ID cụ thể của từng ô ngày.
    // Nếu hệ thống gắn id cho từng ô ngày theo template (vd: "day-8"), hãy điền "%s" vào template.
    private static final String ID_DATEPICKER_DAY_TEMPLATE = "day-%s"; // nếu trang dùng id day-<n>. Nếu không có pattern, để rỗng.

    // Fallback (nếu không dùng ID): các locator theo Flatpickr 
    private final By fpCalendarOpen = By.cssSelector(".flatpickr-calendar.open, .flatpickr-calendar.inline");
    private final By fpMonthSelect = By.cssSelector("select.flatpickr-monthDropdown-months");
    private final By fpYearInput  = By.cssSelector("input.cur-year");
    private final By fpDayCells   = By.cssSelector("span.flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)");

    private static final String XPATH_O_NGAY_TRONG_LICH_TEMPLATE = "//span[contains(@class,'flatpickr-day') and text()='%s']";

    public static String DATEPICKER_DAY_ID = "";     // ví dụ: "day-2"
    public static String DATEPICKER_DAY_XPATH = "";  // ví dụ: "//span[@aria-label='September 2, 2025']"

    // Nút "Lưu thông tin"
    private final By btnLuuThongTin = By.xpath("//*[@id=\"btnSubmit\"]");

    private final By toastThanhCong = By.xpath("/html/body/div[7]/div");

    private final By btnAnHocKy = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[2]/div/table/tbody/tr[1]/td[5]/div/input"); // Placeholder
    private final By thongBaoAnThanhCong = By.xpath("/html/body/div[6]/div/h2"); // Placeholder

    private final By inputTimKiemHocKy = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[1]/div[2]/div/label/input"); 
    // Bảng kết quả (container) – có thể là <table> hoặc <div> grid; dùng để khoanh vùng khi lấy dữ liệu
    private final By bangHocKyContainer = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[2]/div"); 
    // Locator cho các dòng trong bảng – tuỳ UI, có thể là //table/tbody/tr hoặc //div[contains(@class,'row')]
    private final By hangTrongBangHocKy = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[2]/div/table/tbody/tr/td[1]"); 
    // Locator cho các ô (cell) trong một dòng – ví dụ .//td hoặc .//div[contains(@class,'cell')]
    private final By oTrongHangHocKy = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div/div/div/div[2]/div/table/tbody/tr/td[1]"); 
    /** Click vào ô tìm kiếm học kỳ */
    public void clickOtimKiemHocKy() {
        waitClickable(inputTimKiemHocKy).click();
    }

    /** Nhập nội dung vào ô tìm kiếm học kỳ  */
    public void nhapNoiDungTimKiemHocKy(String semesterCode) {
        WebElement input = waitVisible(inputTimKiemHocKy);
        input.click();
        try {
            input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            input.sendKeys(Keys.DELETE);
        } catch (Exception ignored) {}
        input.sendKeys(semesterCode);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    public java.util.List<String> getDanhSachTextBangHocKy() {
        java.util.List<String> result = new java.util.ArrayList<>();
        try {
            WebElement container = waitVisible(bangHocKyContainer);
            java.util.List<WebElement> rows = container.findElements(hangTrongBangHocKy);
            for (WebElement row : rows) {
                java.util.List<WebElement> cells = row.findElements(oTrongHangHocKy);
                if (cells.isEmpty()) {
                    result.add(row.getText().trim());
                } else {
                    for (WebElement c : cells) {
                        String txt = c.getText();
                        if (txt != null && !txt.trim().isEmpty()) {
                            result.add(txt.trim());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Nếu container chưa điền locator đúng, trả danh sách rỗng để test tự xử lý
        }
        return result;
    }

    /** Lấy text của toàn bộ bảng (container) để debug nhanh */
    public String getToanBoTextBangHocKy() {
        try {
            return waitVisible(bangHocKyContainer).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public HocKyPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    // ====== Helpers ======
    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ====== Actions ======
    /** Mở bất kỳ URL nào (test sẽ truyền BASE_URL vào) */
    public void openUrl(String url) {
        driver.get(url);
    }

    /** Click vào nút "Đăng nhập bằng Email VLU" trên trang đầu */
    public void clickDangNhapEmailVLU() {
        waitClickable(btnDangNhapEmailVLU).click();
    }

    /** Click menu "Học kỳ, Ngành & Khoa" */
    public void openMenuHocKyNganhKhoa() {
        waitClickable(menuHocKyNganhKhoa).click();
    }

    /** Click submenu "Học kỳ" */
    public void openSubmenuHocKy() {
        waitClickable(submenuHocKy).click();
    }

    /** Click nút "Thêm mới" tại trang Học kỳ */
    public void clickThemMoi() {
        waitClickable(btnThemMoi).click();
    }

    /** Click nút "Cập nhật" tại trang Học kỳ (bạn cần cập nhật XPath cho btnCapNhat) */
    public void clickCapNhat() {
        waitClickable(btnCapNhat).click();
    }


    public void nhapHocKy(String hocKy) {
        WebElement e = waitVisible(inputHocKy);
        e.click();

        String desiredLastDigit;
        if (hocKy != null && hocKy.length() >= 1) {
            desiredLastDigit = String.valueOf(hocKy.charAt(hocKy.length() - 1));
        } else {
            throw new IllegalArgumentException("Giá trị học kỳ không hợp lệ");
        }
        if (!desiredLastDigit.matches("[1-3]")) {
            throw new IllegalArgumentException("Số cuối học kỳ chỉ cho phép 1-3, nhận: " + desiredLastDigit);
        }

        // Không cố gắng xoá tiền tố vì hệ thống khoá; chỉ nhập số cuối.
        // Tuy nhiên nếu đã có sẵn đúng số cuối thì bỏ qua.
        String current = "";
        try { current = e.getAttribute("value"); } catch (Exception ignored) { }
        if (current == null) current = "";

        if (!current.endsWith(desiredLastDigit)) {
            // Thử các cách phổ biến để đảm bảo con trỏ ở cuối và nhập 1 ký tự
            try { e.sendKeys(Keys.END); } catch (Exception ignored) { }
            e.sendKeys(desiredLastDigit);
        }

        // Xác nhận: đợi giá trị kết thúc bằng số mong muốn và có độ dài >= 3 (vd: "251")
        try {
            wait.until(d -> {
                String v = e.getAttribute("value");
                return v != null && v.endsWith(desiredLastDigit) && v.length() >= 3;
            });
        } catch (Exception ignored) {
            // Fallback nhẹ: nếu vẫn chưa đúng thì dùng JS để nhập số cuối (không thay đổi tiền tố)
            try {
                if (driver instanceof JavascriptExecutor) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript(
                        "arguments[0].value = (arguments[0].value || '').replace(/\\d$/, '') + arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                        e, desiredLastDigit
                    );
                }
            } catch (Exception ignore2) { }
        }
    }

    /** Chọn "Năm bắt đầu" trong dropdown. Yêu cầu cấu hình XPATH_OPTION_NAM_TEMPLATE. */
    public void chonNamBatDau(String nam) {
        // Dùng Select cho <select> là ổn định nhất
        WebElement selectEl = waitVisible(dropdownNamBatDau);
        try {
            Select select = new Select(selectEl);
            select.selectByVisibleText(nam);
        } catch (Exception e) {
            // Nếu không phải <select>, fallback sang click theo option XPath template
            try {
                waitClickable(dropdownNamBatDau).click();
                By optionNam = By.xpath(String.format(XPATH_OPTION_NAM_TEMPLATE, nam));
                waitClickable(optionNam).click();
            } catch (Exception ignore) { }
        }
    }

    /**
     * Chọn ngày bắt đầu bằng lịch (datepicker).
     *
     * Mặc định thuật toán:
     * - Click ô ngày để mở lịch
     * - (Tuỳ chọn) Điều hướng tới đúng tháng/năm bằng hai nút điều hướng nếu cần
     * - Click vào ngày tương ứng
     *
     * Cần điền đúng các locator datepicker*, và TEMPLATE XPATH_O_NGAY_TRONG_LICH_TEMPLATE.
     */
    public void chonNgayBatDau(LocalDate date) {
        // 1) Mở lịch
        waitClickable(inputNgayBatDau).click();

        // 2) Chọn tháng qua dropdown (ưu tiên ID, fallback theo Flatpickr)
        int targetMonthIndex = date.getMonthValue() - 1; // 0..11
        boolean monthSet = false;
        if (LOCATOR_DATEPICKER_MONTH_SELECT != null && !LOCATOR_DATEPICKER_MONTH_SELECT.isEmpty()) {
            try {
                WebElement monthSelEl = waitVisible(
                        LOCATOR_DATEPICKER_MONTH_SELECT.startsWith("/") ? By.xpath(LOCATOR_DATEPICKER_MONTH_SELECT) : By.id(LOCATOR_DATEPICKER_MONTH_SELECT));
                new Select(monthSelEl).selectByIndex(targetMonthIndex);
                monthSet = true;
            } catch (Exception ignore) { /* fallback below */ }
        }
        if (!monthSet) {
            try {
                WebElement cal = waitVisible(fpCalendarOpen);
                WebElement monthSelEl = cal.findElement(fpMonthSelect);
                new Select(monthSelEl).selectByIndex(targetMonthIndex);
                monthSet = true;
            } catch (Exception ignore) { }
        }

        // 3) Chọn năm qua input spinner (ưu tiên ID, fallback Flatpickr)
        String yearText = String.valueOf(date.getYear());
        boolean yearSet = false;
        if (LOCATOR_DATEPICKER_YEAR_INPUT != null && !LOCATOR_DATEPICKER_YEAR_INPUT.isEmpty()) {
            try {
                WebElement yearEl = waitVisible(
                        LOCATOR_DATEPICKER_YEAR_INPUT.startsWith("/") ? By.xpath(LOCATOR_DATEPICKER_YEAR_INPUT) : By.id(LOCATOR_DATEPICKER_YEAR_INPUT));
                yearEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                yearEl.sendKeys(Keys.DELETE);
                yearEl.sendKeys(yearText);
                yearSet = true;
            } catch (Exception ignore) { /* fallback below */ }
        }
        if (!yearSet) {
            try {
                WebElement cal = waitVisible(fpCalendarOpen);
                WebElement yearEl = cal.findElement(fpYearInput);
                yearEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                yearEl.sendKeys(Keys.DELETE);
                yearEl.sendKeys(yearText);
                yearSet = true;
            } catch (Exception ignore) { }
        }

        // 4) Chọn ngày trong lịch – ưu tiên theo ID template, fallback theo Flatpickr day cell
        String dayText = String.valueOf(date.getDayOfMonth());
        boolean dayClicked = false;

        if (ID_DATEPICKER_DAY_TEMPLATE != null && !ID_DATEPICKER_DAY_TEMPLATE.isEmpty() && ID_DATEPICKER_DAY_TEMPLATE.contains("%s")) {
            try {
                String dayId = String.format(ID_DATEPICKER_DAY_TEMPLATE, dayText);
                WebElement el = waitVisible(By.id(dayId));
                el.click();
                dayClicked = true;
            } catch (Exception ignore) { }
        }

        if (!dayClicked) {
            // Flatpickr fallback: tìm ô ngày có text đúng và không phải prev/next month
            try {
                WebElement cal = waitVisible(fpCalendarOpen);
                for (WebElement cell : cal.findElements(fpDayCells)) {
                    String txt = cell.getText();
                    if (dayText.equals(txt)) {
                        try { cell.click(); dayClicked = true; break; } catch (Exception clickErr) { /* try JS */ }
                        if (!dayClicked && driver instanceof JavascriptExecutor) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cell);
                            dayClicked = true; break;
                        }
                    }
                }
            } catch (Exception ignore) { }
        }

        if (!dayClicked) {
            try {
                By oNgay = By.xpath(String.format(XPATH_O_NGAY_TRONG_LICH_TEMPLATE, dayText));
                WebElement el = waitVisible(oNgay);
                try { waitClickable(oNgay).click(); }
                catch (Exception clickErr) {
                    try {
                        if (driver instanceof JavascriptExecutor) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                        }
                    } catch (Exception ignored) { }
                    try { new Actions(driver).moveToElement(el).pause(Duration.ofMillis(100)).click().perform(); }
                    catch (Exception ignored) { }
                    try {
                        if (driver instanceof JavascriptExecutor) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                        }
                    } catch (Exception ignored) { }
                }
            } catch (Exception ignore) { }
        }
    }

    /**
     * Chọn trực tiếp ngày trong lịch bằng một XPath do người dùng truyền vào.
     * Ví dụ: //td[.='15'] hoặc //button[@data-day='15']
     */
    public void chonNgayBatDauBangXpath(String dayCellXPath) {
        waitClickable(inputNgayBatDau).click();
        By by = By.xpath(dayCellXPath);
        WebElement el = waitVisible(by);
        try {
            waitClickable(by).click();
        } catch (Exception clickErr) {
            try {
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                }
            } catch (Exception ignored) { }
            try {
                new Actions(driver).moveToElement(el).pause(Duration.ofMillis(100)).click().perform();
            } catch (Exception ignored) { }
            try {
                if (driver instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                }
            } catch (Exception ignored) { }
        }
    }

    /** Chỉ mở popup lịch của trường "Ngày bắt đầu" (không chọn ngày) */
    public void moPopupNgayBatDau() {
        waitClickable(inputNgayBatDau).click();
    }

    /**
     * Chọn ngày trong lịch dựa trên cấu hình tĩnh DATEPICKER_DAY_ID/DATEPICKER_DAY_XPATH.
     */
    public void chonNgayBatDauTheoCauHinh() {
        if (DATEPICKER_DAY_ID != null && !DATEPICKER_DAY_ID.isEmpty()) {
            waitVisible(By.id(DATEPICKER_DAY_ID));
            waitClickable(By.id(DATEPICKER_DAY_ID)).click();
            return;
        }
        if (DATEPICKER_DAY_XPATH != null && !DATEPICKER_DAY_XPATH.isEmpty()) {
            chonNgayBatDauBangXpath(DATEPICKER_DAY_XPATH);
        }
    }

    /** Click "Lưu thông tin" */
    public void clickLuuThongTin() {
        waitClickable(btnLuuThongTin).click();
    }

    /** Chờ toast/thông báo thành công */
    public void choThongBaoThanhCong() {
        try {
            waitVisible(toastThanhCong);
        } catch (Exception ignored) {
            // Không bắt buộc
        }
    }

    // ====== AnHocKy ======
    /** Click nút ẩn học kỳ */
    public void clickAnHocKy() {
        waitClickable(btnAnHocKy).click();
    }

    /** Chờ thông báo sau khi ẩn học kỳ thành công */
    public void choThongBaoAnHocKy() {
        try {
            waitVisible(thongBaoAnThanhCong);
        } catch (Exception ignored) { /* optional */ }
    }

    /** Lấy text thông báo ẩn thành công để assert (trả null nếu không thấy) */
    public String getTextThongBaoAnHocKy() {
        try {
            return waitVisible(thongBaoAnThanhCong).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /** Trả về giá trị hiện tại của ô Học kỳ (để assertion trong test) */
    public String getHocKyValue() {
        try {
            WebElement e = waitVisible(inputHocKy);
            return e.getAttribute("value");
        } catch (Exception ex) {
            return null;
        }
    }
}
