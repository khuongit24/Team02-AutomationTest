package com.admintest;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC03 - Test case ẩn học kỳ.
 * Kế thừa BaseTest để tái sử dụng setup/teardown chung.
 */
public class TC03_AnHocKy extends BaseTest {

    @Test(description = "TC03 - Ẩn học kỳ và xác nhận thông báo hoàn thành")
    public void anHocKy_DaHoanThanh() throws InterruptedException {
        // Navigate and login
        navigateAndLogin();

        // Navigate to semester page
        navigateToSemesterPage();

        // Click nút ẩn học kỳ
        hocKyPage.clickAnHocKy();
        shortSleep();

        // Chờ và kiểm tra thông báo
        hocKyPage.choThongBaoAnHocKy();
        String msg = hocKyPage.getTextThongBaoAnHocKy();
        String expectedKeyword = "đã hoàn thành";
        
        if (msg == null) {
            Assert.fail("Không tìm thấy thông báo sau khi ẩn học kỳ. Hãy điền đúng locator toast trong HocKyPage.");
        } else {
            Assert.assertTrue(msg.toLowerCase().contains(expectedKeyword),
                    "Thông báo không chứa từ khóa mong đợi. Actual: " + msg);
        }
    }

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[]{TC03_AnHocKy.class});
        testng.setDefaultSuiteName("TC03_AnHocKy Suite");
        testng.setDefaultTestName("TC03_AnHocKy Test");
        testng.run();
    }
}
