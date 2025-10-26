package Sinhvien.Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Sinhvien.Pages.LoginPage;
import Sinhvien.Pages.DashBoardPage_XemDanhSachLopHP;

public class TC43_XemDanhSachLopHP {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashBoardPage_XemDanhSachLopHP dashBoardPage;
    private final String url = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";

    @BeforeClass
    public void setUp() {
        // --- Khởi tạo WebDriver ---
        System.setProperty("webdriver.edge.driver", "C:\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();

        // --- Khởi tạo các Page Object ---
        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage_XemDanhSachLopHP(driver);
    }

    @Test(priority = 1, description = "Kiểm thử xem danh sách lớp học phần")
    public void testXemDanhSachLopHP() {
        try {
            // 1. Điều hướng và xử lý chứng chỉ
            driver.navigate().to(url);
            loginPage.handleCertificateWarning();
            loginPage.selectAccount();

            System.out.println("Đang thực hiện đăng nhập...");
            System.out.println("Đăng nhập thành công.");

            // 2. Điều hướng đến trang Lớp học phần
            dashBoardPage.classsection();
            System.out.println("Đang nhấp vào 'Lớp học phần'...");
            Thread.sleep(3000);

            // 3. Chọn phần cụ thể
            dashBoardPage.ChoosePart();
            System.out.println("Đã chọn phần trong danh sách Lớp học phần.");

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
            driver.quit(); // Đóng toàn bộ EdgeDriver
        }
    }
}
