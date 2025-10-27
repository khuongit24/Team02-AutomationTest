package TroGiang.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashBoardPage_XemTienDoCongViec {
    private WebDriverWait wait;

    // Các Locator
    private By TaskList = By.id("child-trogiang-tasklist");
    private By Task = By.id("parent-trogiang");

    // Constructor
    public DashBoardPage_XemTienDoCongViec(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToTask() {
        wait.until(ExpectedConditions.elementToBeClickable(Task)).click();
    }

	public void navigateToTaskList() {
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.elementToBeClickable(TaskList)).click();
	}
}