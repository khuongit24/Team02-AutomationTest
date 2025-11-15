package testchucnang;

import org.testng.annotations.Test;
import pages.Setup;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class testthaydoithongtin {
	private WebDriver driver;
	  @Test
	  public void logout() throws InterruptedException {
			System.out.println("Thay đổi thông tin cá nhân");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[3]/a/div")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[3]/div/ul[1]")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[4]/div/input")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[4]/div/input")).sendKeys("0395279924");
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[5]/div/input")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/select")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/select/option[10]")).click();

			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[6]/div/select")).sendKeys("Nữ");
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]")).click();
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
