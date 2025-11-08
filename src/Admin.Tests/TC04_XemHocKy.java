package com.admintest;

import java.util.List;

import org.testng.annotations.Test;

/**
 * TC04 - Test case xem/tìm kiếm học kỳ.
 * Kế thừa BaseTest để tái sử dụng setup/teardown chung.
 */
public class TC04_XemHocKy extends BaseTest {
    private static final String SEARCH_SEMESTER_CODE = "251"; 

    @Test(description = "TC04 - Tìm kiếm và xem học kỳ")
    public void xemHocKyTheoMa() throws InterruptedException {
        // Navigate and login
        navigateAndLogin();

        // Navigate to semester page
        navigateToSemesterPage();

        // Click vào ô tìm kiếm
        hocKyPage.clickOtimKiemHocKy();
        shortSleep();

        // Nhập nội dung tìm kiếm
        hocKyPage.nhapNoiDungTimKiemHocKy(SEARCH_SEMESTER_CODE);
        shortSleep();

        // Lấy và hiển thị kết quả
        List<String> cellTexts = hocKyPage.getDanhSachTextBangHocKy();
        String fullTable = hocKyPage.getToanBoTextBangHocKy();

        // In ra console để kiểm tra
        System.out.println("==== Kết quả tìm kiếm học kỳ cho mã: " + SEARCH_SEMESTER_CODE + " ====");
        if (cellTexts.isEmpty()) {
            System.out.println("(Cảnh báo) Không lấy được danh sách cell. Hãy kiểm tra lại các XPath placeholder trong HocKyPage.");
        } else {
            for (String s : cellTexts) {
                System.out.println("Cell: " + s);
            }
        }
        System.out.println("---- Toàn bộ text bảng (fallback) ----");
        System.out.println(fullTable);
    }

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[]{TC04_XemHocKy.class});
        testng.setDefaultSuiteName("TC04_XemHocKy Suite");
        testng.setDefaultTestName("TC04_XemHocKy Test");
        testng.run();
    }
}
