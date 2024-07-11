package selenium_project.selenium_project;

import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scenario4 {

	@Test
	public void scenario4(ExtentReports extent, ExtentTest test) throws InterruptedException, IOException {
		
		test = extent.createTest("Scenario 4 - Download a DATASET");
		test.log(Status.INFO, "Download dataset process started");
		
		String filePath="C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\";

	    String fileName = "data";

	    FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(filePath+fileName+".xlsx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get the sheet, get the 1st row and then 0th cell
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row=sheet.getRow(1);
		XSSFCell usernameCell = row.getCell(0);
	    String username=usernameCell.toString();
		System.out.print("username: "+username);
		
		XSSFCell shortUsername = row.getCell(2);
	    String shortUname=shortUsername.toString();
		System.out.print("short_username: "+shortUname);
		
		XSSFCell passwordCell = row.getCell(1);
	    String password=passwordCell.toString();   
//	    System.out.println("password: "+password);
		
		byte[] decodedpass = Base64.decodeBase64(password);
	    String mypass =  new String(decodedpass);
	    System.out.println("decodedBytes "+ new String(mypass));
	    
        // get the northeastern link
	    XSSFCell link = row.getCell(9);
	    String libURL = link.toString();
	    System.out.println("link "+libURL);
		
//			byte[] encodedBytes = Base64.encodeBase64("yourpassword".getBytes());
//	        System.out.println("encodedBytes "+ new String(encodedBytes));
		System.setProperty( 
	            "webdriver.chrome.driver",
	            "C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\drivers\\chromedriver.exe");
		
		String downloadFilepath = System.getProperty("user.dir");
		System.out.print(downloadFilepath);

		WebDriver driver = new ChromeDriver(); 
  
        // Maximize the browser 
        // using maximize() method 
        driver.manage().window().maximize(); 
  
        // Launching website 
        driver.get(libURL);
        
        WebDriverWait waitTimer = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        Thread.sleep(2000);
		File sc1 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("./Scenario4/BeforeLibraryLogin.png"));
        
		// wait for signin button to be available
        waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.className("sign-in-btn-ctm"))).click();

		// login again with username and password
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		WebElement newUsername = driver.findElement(By.id("username"));
		newUsername.sendKeys(shortUname);
	    WebElement newPassword = driver.findElement(By.id("password"));
	    newPassword.sendKeys(mypass); // password
	    Thread.sleep(2000);
		File sc2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc2, new File("./Scenario4/AfterFillingLoginDetails.png"));
	    driver.findElement(By.xpath("//button[@name='_eventId_proceed']")).click();
	    
		//switch to Duo security
		driver.switchTo().frame("duo_iframe");
		Thread.sleep(2000);
		File sc3 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc3, new File("./Scenario4/DuoProcess.png"));
		driver.findElement(By.xpath("//*[@id=\"auth_methods\"]/fieldset[1]/div[1]/button")).click();
		Thread.sleep(5000);
//		//switch window to default
		driver.switchTo().defaultContent();
		
		Thread.sleep(2000);
		File sc4 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc4, new File("./Scenario4/BeforeDigitalRepositoryService.png"));
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href,\"https://repository.library.northeastern.edu\")]"))).click();
		
		// Switch focus to current tab
		String currentTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
			}
		}
		
		Thread.sleep(2000);
		File sc5 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc5, new File("./Scenario4/AfterDigitalRepositoryServiceClick.png"));
		// authenticate
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href,\"/users/auth/shibboleth\")]"))).click();
		
		// click on datasets button
		Thread.sleep(2000);
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]/div[1]/section/div[1]/a[5]"))).click();
		
		Thread.sleep(2000);
		File sc6 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc6, new File("./Scenario4/AfterClickingDatasetBtn.png"));
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("searchFieldHeader"))).sendKeys("csv");
		
		// screenshot after entering search term
		driver.findElement(By.id("search-submit-header")).click();
		Thread.sleep(2000);
		File sc7 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc7, new File("./Scenario4/AfterSuccessfulSearch.png"));
		
		// after search click on a list item
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/main/section/ul/article[1]/div/header/h4/a")).click();
		
		Thread.sleep(2000);
		File sc8 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc8, new File("./Scenario4/AfterListItemClicked.png"));
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		WebElement zipButton = driver.findElement(By.xpath("//*[@id=\"metadata\"]/section/div/div/div[1]/a[1]"));
		jsExec.executeScript("arguments[0].scrollIntoView(true);", zipButton);
		
		boolean isElementPresent = isElementPresent(driver, By.xpath("//*[@id=\"metadata\"]/section/div/div/div[1]/a[1]"));
		Assertion Assertion = new Assertion();
        try {
			Assertion.assertTrue(isElementPresent, "Zip Button is not present");
			Reporter.log("Assertion (Zip Button is present) - <b>Actual:</b> "+isElementPresent+", \n <b>Expected: </b>true");
		    test.log(Status.INFO, "Assertion (Zip Button is present) - <b>Actual:</b> "+isElementPresent+", \n <b>Expected: </b>true");
		} catch (AssertionError e) {
			Reporter.log("Assertion failed - Zip Button is not present");
			test.log(Status.ERROR, "Assertion failed - Zip Button is not present");
		    throw e;
		}
		
        // click zip button, also take screenshot before it
		Thread.sleep(2000);
		File sc9 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc9, new File("./Scenario4/BeforeZipBtnClick.png"));
		zipButton.click();
		
	    Thread.sleep(5000);
	    driver.quit();
	}

//	public static void main(String args[]) throws InterruptedException, IOException {
//		scenario4();
//	}
	
	private static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}