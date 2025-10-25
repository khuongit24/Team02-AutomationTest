package TC39_XemUngTuyenTroGiang;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashBoardPage {
    private WebDriverWait wait;

    // Các Locator
    // child-studentaffairs-studentaffairstimetable-index tương ứng với mục "Xem thời khóa biểu"
    private By TaskList = By.id("child-trogiang-resultapply");
    private By Task = By.id("parent-trogiang");
    private By HK = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div[1]/div/div[1]/select/option[6]");
    private By HP = By.xpath("/html/body/div[2]/main/section/div[2]/div/div/div[1]/div/div[2]/select/option[6]");

    // Constructor
    public DashBoardPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToTask() {
        wait.until(ExpectedConditions.elementToBeClickable(Task)).click();
    }

	public void navigateToTaskList() {
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.elementToBeClickable(TaskList)).click();
	}
	public void ChoosePart() {
		wait.until(ExpectedConditions.elementToBeClickable(HK)).click();
		wait.until(ExpectedConditions.elementToBeClickable(HP)).click();
	}

}
