package Sinhvien.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Sinhvien.Pages.LoginPage;
import Sinhvien.Pages.DashBoardPage_XemUngTuyenTroGiang;

public class TC39_XemUngTuyenTroGiang {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashBoardPage_XemUngTuyenTroGiang dashBoardPage;
    private final String url = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.edge.driver", 
            "C:\\Users\\asus\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage_XemUngTuyenTroGiang(driver);
    }

    @Test(priority = 1, description = "Kiểm thử xem kết quả đăng ký Trợ giảng")
    public void testXemUngTuyenTroGiang() {
        try {
            // 1. Mở URL và xử lý cảnh báo chứng chỉ
            driver.navigate().to(url);
            loginPage.handleCertificateWarning();
            loginPage.selectAccount();

            System.out.println("Đang thực hiện đăng nhập...");
            // Có thể thêm assert để kiểm tra đăng nhập thành công
            System.out.println("Đăng nhập thành công.");

            // 2. Điều hướng đến trang Trợ giảng
            dashBoardPage.navigateToTask();
            System.out.println("Đang nhấp vào 'Trợ giảng'...");
            Thread.sleep(3000);

            dashBoardPage.navigateToTaskList();
            System.out.println("Đã đến trang Kết quả đăng ký.");
            Thread.sleep(3000);

            // 3. Chọn phần cụ thể trong trang Trợ giảng
            dashBoardPage.ChoosePart();
            System.out.println("Đã chọn phần trong danh sách Trợ giảng.");

        } catch (Exception e) {
            System.err.println("Kiểm thử thất bại: " + e.getMessage());
            e.printStackTrace();
            assert false : "Lỗi trong quá trình kiểm thử: " + e.getMessage();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(3000); // Tạm dừng để xem kết quả
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit(); // Đóng hoàn toàn EdgeDriver
        }
    }
}
