package testng;

import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;

import static utilities.Driver.setup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import static utilities.ExcelFileReader.readDataFromCsv;
import static scripts.PropertyReader.getProperty;

public class DataProviderUsingCsv {
	
  	WebDriver driver;
  	String filePath;
  	String ddtUrl;
	@Test(dataProvider = "dp")
	public void f(String n, String s, String res) throws IOException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.get(ddtUrl);
		
		driver.findElement(By.id("sum1")).sendKeys(n);
		driver.findElement(By.id("sum2")).sendKeys(s);
		driver.findElement(By.xpath("//button[text()='Get Total']")).click();
		
		WebElement element = driver.findElement(By.xpath("//span[@id='displayvalue']"));

		Assert.assertEquals(element.getText(), res);
	}
  
  @DataProvider
  public Iterator<Object []> dp() throws IOException {
	  return readDataFromCsv(filePath);
    };		 

  
  @BeforeTest
  public void beforeTest() throws FileNotFoundException, IOException {
	  driver = setup("CH");
	  filePath = getProperty("dataFilePath");
	  ddtUrl = getProperty("ddtUrl");
	  
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
