package com.admintest;

import com.Pages.BaseTest;

import org.testng.annotations.Test;

/**
 * Test case cập nhật học kỳ
 */
public class CapNhatHocKy extends BaseTest {

    @Test(description = "Cập nhật học kỳ: mở lịch và chọn ngày bắt đầu")
    public void capNhatHocKy_ChonNgayBatDau() throws InterruptedException {
        navigateAndLogin();
        navigateToSemesterPage();

        hocKyPage.clickCapNhat();
        shortSleep();

        hocKyPage.moPopupNgayBatDau();
        shortSleep();

        hocKyPage.chonNgayBatDauTheoCauHinh();
        shortSleep();

        hocKyPage.clickLuuThongTin();
        hocKyPage.choThongBaoThanhCong(); 
    }
}
