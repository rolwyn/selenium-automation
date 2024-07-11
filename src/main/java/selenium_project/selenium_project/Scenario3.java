package selenium_project.selenium_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Scenario3 {

	@Test
	public void scenario3(ExtentReports extent, ExtentTest test) throws InterruptedException, IOException {
		
		test = extent.createTest("Scenario 3 - Download a classroom guide");
		test.log(Status.INFO, "Downloading a classroom guide process started...");
		
		String filePath="C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\";

	    String fileName = "data";

	    FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(filePath+fileName+".xlsx"));
		} catch (FileNotFoundException e) {
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
		
		byte[] decodedpass = Base64.decodeBase64(password);
	    String mypass =  new String(decodedpass);
	    System.out.println("decodedBytes "+ new String(mypass));
	    
        // get the northeastern link
	    XSSFCell link = row.getCell(9);
	    String libURL = link.toString();
	    System.out.println("link "+libURL);
		
		//	byte[] encodedBytes = Base64.encodeBase64("yourpassword".getBytes());
		//	System.out.println("encodedBytes "+ new String(encodedBytes));
		System.setProperty( 
	            "webdriver.chrome.driver",
	            "C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\drivers\\chromedriver.exe");
		
		String downloadFilepath = System.getProperty("user.dir");
		System.out.print(downloadFilepath);
		
		//set chrome driver options for pdf
		ChromeOptions options = new ChromeOptions();
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("plugins.always_open_pdf_externally", true);

		options.setExperimentalOption("prefs",chromePrefs);

		WebDriver driver = new ChromeDriver(options);
  
        // Maximize the browser 
        // using maximize() method 
        driver.manage().window().maximize(); 
  
        // Launching website 
        driver.get("https://service.northeastern.edu/tech?id=classrooms");
        
        WebDriverWait waitTimer = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        Thread.sleep(5000);
        File sc1 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("./Scenario3/PageBeforeLogin.png"));
		
        waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"sp-nav-bar\"]/ul[2]/li/a"))).click();

		// login again with username and password
        Thread.sleep(2000);
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		WebElement newUsername = driver.findElement(By.id("username"));
		newUsername.sendKeys(shortUname);
	    WebElement newPassword = driver.findElement(By.id("password"));
	    newPassword.sendKeys(mypass);
	    
	    Thread.sleep(2000);
        File sc2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc2, new File("./Scenario3/PageAfterLoginDetails.png"));
		
		// click login
	    driver.findElement(By.xpath("//button[@name='_eventId_proceed']")).click();
	    
		//switch to Duo security
		driver.switchTo().frame("duo_iframe");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"auth_methods\"]/fieldset[1]/div[1]/button")).click();
		Thread.sleep(5000);
//		//switch window to default
		driver.switchTo().defaultContent();
		
		// scroll classroom into view
		Thread.sleep(3000);
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("campus")));
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		WebElement classroom = driver.findElement(By.xpath("//a[contains(@href,\"/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0\")]"));
		jsExec.executeScript("arguments[0].scrollIntoView(true);", classroom);
		
        File sc3 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc3, new File("./Scenario3/PageAfterScrollingToClassroom.png"));
		
		// check if classroom is present
		boolean isElementPresent = isElementPresent(driver, By.xpath("//a[contains(@href,\"/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0\")]"));
		System.out.print(isElementPresent);
		String classroomInnerHTML = driver.findElement(By.xpath("//a[contains(@href,\"/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0\")]")).getAttribute("innerHTML");
		Assertion Assertion = new Assertion();
        try {
			Assertion.assertTrue(isElementPresent, "Classroom is not present");
			Reporter.log("Assertion (Classroom is present) - <b>Actual:</b> 007 Behrakis Health Sciences Center, \n <b>Expected: </b>"+classroomInnerHTML);
		    test.log(Status.INFO, "Assertion (Classroom is present) - <b>Actual:</b> 007 Behrakis Health Sciences Center, \n <b>Expected: </b>"+classroomInnerHTML);
		} catch (AssertionError e) {
			Reporter.log("Assertion failed - Classroom names are not correct");
			test.log(Status.ERROR, "Assertion failed - Classroom names are not correct");
		    throw e;
		}
		
		// click on one classroom
		Thread.sleep(2000);
        File sc4 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc4, new File("./Scenario3/BeforeClickingAClassroom.png"));
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href,\"/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0\")]"))).click();
		
		// click on pdf to download
		Thread.sleep(4000);
		File sc5 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc5, new File("./Scenario3/BeforeClickingPDFLink.png"));
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href,\"https://nuflex.northeastern.edu/wp-content/uploads/2020/11/Hybrid_Nuflex_Classroom.pdf\")]"))).click();

		
		// Switch focus to current tab
		Thread.sleep(5000);
		String currentTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
			}
		}
		
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("alert('Nuflex Classroom PDF is downloaded');");
		
	    Thread.sleep(2000);
	    driver.quit();
	}

//	public static void main(String args[]) throws InterruptedException, IOException {
//		scenario3();
//	}
	
	private boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}