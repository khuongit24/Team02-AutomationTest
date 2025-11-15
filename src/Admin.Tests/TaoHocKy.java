package com.admintest;

import com.Pages.BaseTest;
import com.Pages.TestConfig;

import java.time.LocalDate;

import org.testng.annotations.Test;

/**
 * Test case tạo học kỳ mới.
 */
public class TaoHocKy extends BaseTest {
    private static final String HOC_KY_INPUT = TestConfig.DEFAULT_SEMESTER_CODE; 
    private static final String NAM_BAT_DAU_INPUT = TestConfig.DEFAULT_START_YEAR; 
    private static final LocalDate NGAY_BAT_DAU_INPUT = LocalDate.of(
            Integer.parseInt(TestConfig.DEFAULT_START_YEAR), 1, 1);
    private static final String CUSTOM_DAY_XPATH = "/html/body/div[4]/div[2]/div/div[2]/div/span[13]";

    @Test(description = "Tạo học kỳ mới")
    public void taoHocKyMoi() throws InterruptedException {
        navigateAndLogin();
        navigateToSemesterPage();

        hocKyPage.clickThemMoi();
        shortSleep();

        // Chọn "Năm bắt đầu" trước (để hệ thống sinh đúng tiền tố cho ô Học kỳ)
        hocKyPage.chonNamBatDau(NAM_BAT_DAU_INPUT);
        shortSleep();

        // Điền ô "Học kỳ" – chỉ nhập số cuối (1-3)
        hocKyPage.nhapHocKy(HOC_KY_INPUT);
        
        // Xác nhận giá trị hợp lệ
        String hocKyValue = hocKyPage.getHocKyValue();
        if (hocKyValue == null || hocKyValue.length() < 3 ||
                !(hocKyValue.endsWith("1") || hocKyValue.endsWith("2") || hocKyValue.endsWith("3"))) {
            throw new AssertionError("Giá trị ô Học kỳ không hợp lệ sau khi nhập: " + hocKyValue);
        }

        // Chọn ngày bắt đầu
        if (CUSTOM_DAY_XPATH != null && !CUSTOM_DAY_XPATH.isEmpty()) {
            hocKyPage.chonNgayBatDauBangXpath(CUSTOM_DAY_XPATH);
        } else {
            hocKyPage.chonNgayBatDau(NGAY_BAT_DAU_INPUT);
        }

        hocKyPage.clickLuuThongTin();
        hocKyPage.choThongBaoThanhCong();
    }
}
