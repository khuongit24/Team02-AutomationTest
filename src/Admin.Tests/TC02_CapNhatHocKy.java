package com.admintest;

import org.testng.annotations.Test;

/**
 * TC02 - Test case cập nhật học kỳ (chọn ngày bắt đầu).
 * Kế thừa BaseTest để tái sử dụng setup/teardown chung.
 */
public class TC02_CapNhatHocKy extends BaseTest {

    @Test(description = "TC02 - Cập nhật học kỳ: mở lịch và chọn ngày bắt đầu")
    public void capNhatHocKy_ChonNgayBatDau() throws InterruptedException {
        // Navigate and login
        navigateAndLogin();

        // Navigate to semester page
        navigateToSemesterPage();

        // Click nút Cập nhật
        hocKyPage.clickCapNhat();
        shortSleep();

        // Mở popup chọn ngày bắt đầu
        hocKyPage.moPopupNgayBatDau();
        shortSleep();

        // Chọn ngày bắt đầu theo cấu hình
        hocKyPage.chonNgayBatDauTheoCauHinh();
        shortSleep();

        // Lưu thông tin
        hocKyPage.clickLuuThongTin();
        // hocKyPage.choThongBaoThanhCong(); // Optional
    }

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[]{TC02_CapNhatHocKy.class});
        testng.setDefaultSuiteName("TC02_CapNhatHocKy Suite");
        testng.setDefaultTestName("TC02_CapNhatHocKy Test");
        testng.run();
    }
}
