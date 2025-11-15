package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {

	public ChromeDriver setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        String chromeProfilePath = "C:\\Users\\nguye\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 22";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + chromeProfilePath);
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://cntttest.vanlanguni.edu.vn:18081/Ta2025/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("/html/body/main/section/div/div/div/div/div[3]/div")).click();
        return driver;
    }

}
