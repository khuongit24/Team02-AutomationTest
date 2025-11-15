package com.admintest;

import com.Pages.BaseTest;

import java.util.List;

import org.testng.annotations.Test;

/**
 * Test case xem/tìm kiếm học kỳ.
 */
public class XemHocKy extends BaseTest {
    private static final String SEARCH_SEMESTER_CODE = "251"; 

    @Test(description = "Tìm kiếm và xem học kỳ")
    public void xemHocKyTheoMa() throws InterruptedException {
        navigateAndLogin();
        navigateToSemesterPage();

        hocKyPage.clickOtimKiemHocKy();
        shortSleep();

        hocKyPage.nhapNoiDungTimKiemHocKy(SEARCH_SEMESTER_CODE);
        shortSleep();

        List<String> cellTexts = hocKyPage.getDanhSachTextBangHocKy();
        String fullTable = hocKyPage.getToanBoTextBangHocKy();

        System.out.println("==== Kết quả tìm kiếm học kỳ cho mã: " + SEARCH_SEMESTER_CODE + " ====");
        if (cellTexts.isEmpty()) {
            System.out.println("Không lấy được danh sách cell. Hãy kiểm tra lại các XPath placeholder trong HocKyPage.");
        } else {
            for (String s : cellTexts) {
                System.out.println("Cell: " + s);
            }
        }
        System.out.println("---- Toàn bộ text trong bảng ----");
        System.out.println(fullTable);
    }
}
