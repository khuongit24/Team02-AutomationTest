package com.admintest;

import com.Pages.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test case ẩn học kỳ.
 */
public class AnHocKy extends BaseTest {

    @Test(description = "Ẩn học kỳ và xác nhận thông báo hoàn thành")
    public void anHocKy_DaHoanThanh() throws InterruptedException {
        navigateAndLogin();
        navigateToSemesterPage();

        hocKyPage.clickAnHocKy();
        shortSleep();

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
}
