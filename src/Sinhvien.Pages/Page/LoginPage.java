package TroGiang.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Các Locator (Bộ định vị)
    private By detailsButton = By.id("details-button"); // Cho chứng chỉ không hợp lệ
    private By proceedLink = By.id("proceed-link"); // Cho chứng chỉ không hợp lệ
    private By submitlogin = By.id("submitlogin");
    private By Email = By.cssSelector("#tilesHolder > div:nth-child(2) > div > div");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Phương thức để xử lý cảnh báo chứng chỉ (Nếu có)
    public void handleCertificateWarning() {
        try {
            driver.findElement(detailsButton).click();
            driver.findElement(proceedLink).click();
            
        } catch (Exception e) {
            System.out.println("No certificate warning found or error handling it.");
        }
        driver.findElement(submitlogin).click();

    }
    public void selectAccount() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(Email));
        driver.findElement(Email).click();
        System.out.println("Clicked the email account.");
    }
}
