package com.admintest;

import org.testng.annotations.Test;
import com.Pages.BaseTest;
/**
 * Test case mở đăng ký trợ giảng.
 */
public class MoDangKyTroGiang extends BaseTest {

    @Test(description = "Mở đăng ký trợ giảng và lưu thông tin")
    public void moDangKyTroGiangFlow() throws InterruptedException {
        navigateAndLogin();
        navigateToTroGiangForm();

        troGiangPage.clickButtonDangKy();
        shortSleep();

        troGiangPage.openDropdownNganh();
        shortSleep();
        troGiangPage.chonNganhCongNgheThongTin();
        shortSleep();

        troGiangPage.openNgayMoPopup();
        shortSleep();
        troGiangPage.selectNgayMoTheoXpath();
        shortSleep();

        troGiangPage.openNgayDongPopup();
        shortSleep();
        troGiangPage.selectNgayDongTheoXpath();
        shortSleep();

        troGiangPage.clickLuuThongTin();
    }
}
