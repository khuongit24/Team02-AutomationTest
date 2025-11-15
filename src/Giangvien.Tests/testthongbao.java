package testchucnang;

import org.testng.annotations.Test;
import pages.Setup;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class testthongbao {
	private WebDriver driver;
	  @Test(priority = 0)
	  public void xemthongbao() throws InterruptedException {
			System.out.println("Test case 1: Xem thông báo");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]/div/div/ul/div[1]/div[2]/div/div/div/li[1]/div/div[1]/a/div")).click();
	  }
	  
	  @Test(priority = 1)
	  public void timthongbao() throws InterruptedException {
			System.out.println("Test case 2: Tìm thông báo");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]/div/div/div[2]/div/input")).sendKeys("học kỳ 251");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]/div/div/div[2]/div/a")).click();
	  }
	  
	  @Test(priority = 2)
	  public void xoathongbao() throws InterruptedException {
			System.out.println("Test case 3: Xóa thông báo");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div[1]/nav/div/ul/li[1]/div/div/ul/div[1]/div[2]/div/div/div/li[1]/div/div[2]/div/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[5]/div/div[6]/button[1]")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/div/nav/div/ul/li[1]")).click();
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
