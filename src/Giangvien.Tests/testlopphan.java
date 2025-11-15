package testchucnang;

import org.testng.annotations.Test;

import pages.Setup;

import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class testlopphan {
	private WebDriver driver;
	  @Test(priority = 0)
	  public void uploadfile() throws InterruptedException {
			System.out.println("Test case 1: Upload danh sách sinh viên");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/nav/div/div[1]/div[2]/div/div/div/ul/li[2]")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select/option[16]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[5]/a/span/i")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[2]/div/div/div/input")).sendKeys("D:\\da\\sinhvien.xlsx");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[3]/button[2]")).click();
	  }
	  
	  @Test(priority = 1)
	  public void updatefile() throws InterruptedException {
			System.out.println("Test case 2: Cập nhật danh sách sinh viên");	
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select/option[16]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[5]/a/span/i")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[2]/div/div/div/input")).sendKeys("D:\\da\\capnhatsinhvien.xlsx");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[3]/button[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[6]/div/div[6]/button[1]")).click();
	  }
	  
	  @Test(priority = 2)
	  public void replacefile() throws InterruptedException {
			System.out.println("Test case 3: Thay thế danh sách sinh viên");	
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select/option[16]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[1]/div/div[1]/select")).click();
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[2]/div/div/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[5]/a/span/i")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[2]/div/div/div/input")).sendKeys("D:\\da\\capnhatsinhvien.xlsx");
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/main/section[1]/div[5]/div/div/div[3]/button[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[6]/div/div[6]/button[2]")).click();
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
