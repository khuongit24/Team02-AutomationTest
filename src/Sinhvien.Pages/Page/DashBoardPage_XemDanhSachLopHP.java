package TroGiang.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashBoardPage_XemDanhSachLopHP {
    private WebDriverWait wait;

    // Các Locator
    private By LopHocPhan = By.id("child-classsection-index");
    private By HK = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div[1]/div/div[1]/select/option[6]");
    private By HP = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div[1]/div/div[2]/select/option[6]");
    private By HocKy = By.id("hocky");
    private By Nganh = By.id("nganh");

    // Constructor
    public DashBoardPage_XemDanhSachLopHP(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


	public void classsection() {
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.elementToBeClickable(LopHocPhan)).click();
	}
	public void ChoosePart() {
		wait.until(ExpectedConditions.elementToBeClickable(HocKy)).click();
		wait.until(ExpectedConditions.elementToBeClickable(HK)).click();
		wait.until(ExpectedConditions.elementToBeClickable(Nganh)).click();
		wait.until(ExpectedConditions.elementToBeClickable(HP)).click();
	}
}