package Sinhvien.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Sinhvien.Pages.DashBoardPage_XemTienDoCongViec;
import Sinhvien.Pages.LoginPage;

public class TC35_XemTienDoCongViec {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashBoardPage_XemTienDoCongViec dashBoardPage;
    private final String url = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";

    @BeforeClass
    public void setUp() {
        // --- Cấu hình WebDriver ---
        System.setProperty("webdriver.edge.driver", "C:\\Users\\asus\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();

        // --- Khởi tạo Page Objects ---
        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage_XemTienDoCongViec(driver);
    }

    @Test(priority = 1, description = "Kiểm thử đăng nhập và điều hướng đến trang Công việc")
    public void testXemTienDoCongViec() {
        try {
            // 1. Mở trang và xử lý cảnh báo chứng chỉ
            driver.navigate().to(url);
            loginPage.handleCertificateWarning();
            loginPage.selectAccount();

            System.out.println("Đang thực hiện đăng nhập...");
            // Ở đây có thể thêm assert nếu bạn có điều kiện xác nhận đăng nhập thành công
            System.out.println("Đăng nhập thành công.");

            // 2. Điều hướng đến trang Công việc
            dashBoardPage.navigateToTask();
            System.out.println("Đang nhấp vào 'Công việc'...");
            Thread.sleep(3000);

            dashBoardPage.navigateToTaskList();
            System.out.println("Đã đến trang Công Việc.");
            Thread.sleep(3000);

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
                Thread.sleep(3000); // Xem kết quả trước khi đóng
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit(); // Dừng hẳn EdgeDriver
        }
    }
}
