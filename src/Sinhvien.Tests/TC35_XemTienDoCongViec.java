package TC35_XemTienDoCongViec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class TC35_XemTienDoCongViec {
    public static void main(String[] args) {
        // --- Cấu hình và Khởi tạo ---
        System.setProperty("webdriver.edge.driver", "C:\\Users\\asus\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        
        // Dữ liệu đăng nhập (Nên lưu trữ ở file cấu hình/properties nhưng ở đây đặt tạm)
        String url = "https://cntttest.vanlanguni.edu.vn:18081/Ta2025";
        
        // --- Khởi tạo Page Objects ---
        LoginPage loginPage = new LoginPage(driver);
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        
        try {
            // 1. Điều hướng đến URL và xử lý cảnh báo chứng chỉ
            driver.navigate().to(url);
            loginPage.handleCertificateWarning();
            loginPage.selectAccount();
            
            // 2. Thực hiện Đăng nhập
            System.out.println("Đang thực hiện đăng nhập...");
            System.out.println("Đăng nhập thành công.");
            
            // 3. Điều hướng đến trang Xem Công việc
            dashBoardPage.navigateToTask();
            System.out.println("Đang nhấp vào 'Công việc'...");
            Thread.sleep(3000);
            dashBoardPage.navigateToTaskList();
            System.out.println("Đã đến trang Công Việc.");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Kiểm thử thất bại: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 4. Đóng trình duyệt
            // Dành thời gian để xem kết quả trước khi đóng (tùy chọn)
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.close();
        }
    }
}