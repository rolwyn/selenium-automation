package selenium_project.selenium_project;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

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

public class Scenario5 {
	@Test
	public void scenario5(ExtentReports extent, ExtentTest test) throws InterruptedException, IOException {
		
		test = extent.createTest("Scenario 5 - Update the Academic calendar");
		test.log(Status.INFO, "Update the Academic calendar process started...");
		
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
	    XSSFCell link = row.getCell(3);
	    String myurl = link.toString();
	    System.out.println("link "+myurl);
		
//			byte[] encodedBytes = Base64.encodeBase64("yourpassword".getBytes());
//	        System.out.println("encodedBytes "+ new String(encodedBytes));
		System.setProperty( 
	            "webdriver.chrome.driver",
	            "C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\drivers\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver(); 
  
        // Maximize the browser 
        // using maximize() method 
        driver.manage().window().maximize(); 
  
        // Launching website 
        driver.get("https://about.me.northeastern.edu/home/");
        
        // check if correct URL is routed
        Thread.sleep(2000);
        String expectedMainURL = myurl;
        String currentMainURL = driver.getCurrentUrl();
        System.out.print(currentMainURL);
        Assert.assertEquals(currentMainURL, expectedMainURL);
        
        driver.findElement(By.xpath("//a[contains(@href,\"https://me.northeastern.edu\")]")).click();
        WebDriverWait waitTimer = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for the login page to be loaded
//        waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("body")));
        boolean flag = true;
        while (flag) {
        	if (isElementPresent(driver, By.id("i0116"))) {
        		flag = false;
        		System.out.println("Login page loaded!");
        	} else {
        		// refresh page
        		System.out.println("Refreshing, login not loaded");
        		driver.navigate().refresh();
//        		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        		System.out.println("Login page loaded!");
        	}   	
        }
        
//        driver.navigate().refresh();
//		Thread.sleep(10000);
        waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("i0116")));
	    // fill username, password and hit submit
        Thread.sleep(2000);
		File scrFile2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile2, new File("./Scenario2/screenshot_beforelogin_studenthub.png"));
	    WebElement usernameField = driver.findElement(By.id("i0116"));
	    usernameField.sendKeys(username);
	    WebElement loginButton = driver.findElement(By.id("idSIButton9"));
	    loginButton.click();
	    WebElement passwordField = driver.findElement(By.id("i0118"));
	    passwordField.sendKeys(mypass);
	    Thread.sleep(1000);
	    WebElement signInButton = driver.findElement(By.id("idSIButton9"));
	    signInButton.click();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	    // wait for duo authentication
		Thread.sleep(5000);
	    // click trust button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button"))).click();
	    
	    // click stay signed in submit button
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-report-value='Submit']"))).click(); 
	    
	    // close welcome pop up
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-icon-name='Cancel']"))).click();
	    
	    // screenshot on student hub
		File sc1 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("./Scenario5/BeforeResourcesClick.png"));
	    
	    // soft assertion for student hub to check if it is the correct link
	    String expectedURL = "https://northeastern.sharepoint.com/sites/StudentHub";
	    String currentURL = driver.getCurrentUrl();
	    System.out.println(currentURL);
	    SoftAssert softAssertion = new SoftAssert();
	    softAssertion.assertEquals(expectedURL, currentURL);
	    
	    // check if Student resources text is there in href link
	    Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(@href,\"Student-Resources.aspx\")]")).click();
		
		// click academics, classes and registration
		Thread.sleep(3000);
		File sc2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc2, new File("./Scenario5/BeforeAcademicsClassesRegClick.png"));
		driver.findElement(By.xpath("//*[@data-gtm-sh-resources-group='Academics, Classes & Registration']")).click();
		
		// click Academic Calendar
		Thread.sleep(3000);
		File sc3 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc3, new File("./Scenario5/AfterAcademicsClassesRegClick.png"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-gtm-sh-resources-link='Academic Calendar']"))).click();
//		driver.findElement(By.xpath("//*[@data-gtm-sh-resources-link='Academic Calendar']")).click();
		Thread.sleep(5000);
		
		File sc4 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc4, new File("./Scenario5/BeforeAcademicCalendarClick.png"));
		
		// Switch focus to current tab
		String currentTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
			}
		}
		Thread.sleep(1000);
		
		// click academic calendar
		driver.findElement(By.xpath("//*[contains(@href,'https://registrar.northeastern.edu/article/academic-calendar/')]")).click();

		// Screenshot after clicking academic calendar
		Thread.sleep(2000);
		File sc5 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc5, new File("./Scenario5/AfterAcademicCalendarClick.png"));
		
		// scroll to calendar
		WebElement scrollElement = driver.findElement(By.id("trumba.spud.5"));
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].scrollIntoView(true);", scrollElement);
		
		// switch to options iframe
		driver.switchTo().frame("trumba.spud.6.iframe");
		
		Thread.sleep(2000);
		File sc6 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc6, new File("./Scenario5/BeforeCalendarUntick.png"));
		
		driver.findElement(By.xpath("//*[@id='mixItem0']")).click();
		
		// Screenshot after calendar untick
		Thread.sleep(2000);
		File sc7 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc7, new File("./Scenario5/AfterCalendarUntick.png"));
		
		//switch window to default
		driver.switchTo().defaultContent();
		
		// scroll and verify button
		WebElement scrollToButton = driver.findElement(By.id("nu-global-footer"));
		jsExec.executeScript("arguments[0].scrollIntoView(true);", scrollToButton);
		
		// Screenshot after scroll to Add to calendar button
	    Thread.sleep(2000);
		File sc8 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc8, new File("./Scenario5/AfterScrollToAddCalendarBtn.png"));
	    
	 // switch to options event iframe for assertion, Verify if button is displayed
 		driver.switchTo().frame("trumba.spud.2.iframe");
 		driver.findElement(By.xpath("//*[@id='ctl04_ctl90_ctl00_buttonAtmc']"));
 		Assertion Assertion = new Assertion();
        try {
			Assertion.assertTrue(driver.findElement(By.xpath("//*[@id='ctl04_ctl90_ctl00_buttonAtmc']")).isDisplayed(), "Add to My Calendar is not present");
			Reporter.log("Assertion (Add to My Calendar Button is displayed) - <b>Actual:</b> "+driver.findElement(By.xpath("//*[@id='ctl04_ctl90_ctl00_buttonAtmc']")).isDisplayed()+", \n <b>Expected: </b>true");
		    test.log(Status.INFO, "Assertion (Add to My Calendar Button is displayed) - <b>Actual:</b> "+driver.findElement(By.xpath("//*[@id='ctl04_ctl90_ctl00_buttonAtmc']")).isDisplayed()+", \n <b>Expected: </b>true");
		} catch (AssertionError e) {
			Reporter.log("Assertion failed - Add to My Calendar Button is not present");
			test.log(Status.ERROR, "Assertion failed - Add to My Calendar Button is not present");
		    throw e;
		}
 		Thread.sleep(2000);
 		//switch window to default
 		driver.switchTo().defaultContent();
 		
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("alert('Add to My Calendar button is present');");
	    
	    Thread.sleep(2000);
	    driver.quit();
	}
	
//	public static void main(String args[]) throws InterruptedException, IOException {
//		scenario5();
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