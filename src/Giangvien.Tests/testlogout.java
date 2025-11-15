package testchucnang;

import org.testng.annotations.Test;
import pages.Setup;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class testlogout {
	private WebDriver driver;
	  @Test
	  public void logout() throws InterruptedException {
			System.out.println("Log out");
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[3]/a/div")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[3]/div/ul[2]/li/a")).click();
	  }
	  
	  @BeforeClass
		public void setUp() throws InterruptedException, IOException {
			Setup setup = new Setup();
			driver = setup.setup();
		}

	  @AfterClass
		public void tearDown() throws InterruptedException{
			Thread.sleep(5000);
			driver.quit();
		}

}
