package selenium_project.selenium_project;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scenario1 {
	
	public void scenario1(ExtentReports extent, ExtentTest test) throws InterruptedException, IOException {
		test = extent.createTest("Scenario 1 - Download the latest transcript");
		test.log(Status.INFO, "Download the latest transcript process started");
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
		
		String downloadFilepath = System.getProperty("user.dir");
		System.out.print(downloadFilepath);

		WebDriver driver = new ChromeDriver();
  
        // Maximize the browser 
        // using maximize() method 
        driver.manage().window().maximize(); 
  
        // Launching website 
        driver.get(myurl);
        
        // check if correct URL is routed
        Thread.sleep(2000);
        String expectedMainURL = myurl;
        String currentMainURL = driver.getCurrentUrl();
        Assertion Assertion = new Assertion();
		try {
			Assertion.assertEquals(currentMainURL, expectedMainURL);
			Reporter.log("Hub Homepage URL Success - <b>Actual:</b> "+currentMainURL+ "\n <b>Expected:</b> "+expectedMainURL);
		    test.log(Status.INFO, "Assertion for Hub Homepage URL - <b>Actual:</b> "+currentMainURL+ "\n <b>Expected:</b> "+expectedMainURL);
		} catch (AssertionError e) {
			Reporter.log("Actual and Expected are not the same");
			test.log(Status.ERROR, "Actual and Expected are not the same");
		    throw e;
		}
        
        File sc1 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("./Scenario1/studenthub_before.png"));
        driver.findElement(By.xpath("//a[contains(@href,\"https://me.northeastern.edu\")]")).click();
        WebDriverWait waitTimer = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for the login page to be loaded
        boolean flag = true;
        while (flag) {
        	if (isElementPresent(driver, By.id("i0116"))) {
        		flag = false;
        		System.out.println("Login page loaded!");
        	} else {
        		// refresh page
        		System.out.println("Refreshing, login not loaded");
        		driver.navigate().refresh();
        		System.out.println("Login page loaded!");
        	}   	
        }
        
        waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("i0116")));
	    // fill username, password and hit submit
        Thread.sleep(2000);
		File sc2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc2, new File("./Scenario1/login_before_username.png"));
		
	    WebElement usernameField = driver.findElement(By.id("i0116"));
	    usernameField.sendKeys(username);
	    
	    Thread.sleep(1000);
	    File sc3 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc3, new File("./Scenario1/login_after_username.png"));
	    
	    WebElement loginButton = driver.findElement(By.id("idSIButton9"));
	    loginButton.click();
	    
	    Thread.sleep(1000);
	    File sc4 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc4, new File("./Scenario1/login_before_password.png"));
	    
	    WebElement passwordField = driver.findElement(By.id("i0118"));
	    passwordField.sendKeys(mypass);
	    
	    Thread.sleep(1000);
	    File sc5 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc5, new File("./Scenario1/login_after_password.png"));
	    
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
	    
	    // soft assertion for student hub to check if it is the correct link
	    String expectedURL = "https://northeastern.sharepoint.com/sites/StudentHub";
	    String currentURL = driver.getCurrentUrl();
	    System.out.println(currentURL);
	    SoftAssert softAssertion = new SoftAssert();
	    
	    try {
	    	softAssertion.assertEquals(expectedURL, currentURL);
		    Reporter.log("Student hub Assertion Success - <b>Actual:</b> "+currentURL+ "\n <b>Expected:</b> "+expectedURL);
		    test.log(Status.INFO, "Student hub Assertion Success - <b>Actual:</b> "+currentURL+ "\n <b>Expected:</b> "+expectedURL);
		} catch (AssertionError e) {
			Reporter.log("Student hub Assertion Failed: URLs are not same.");
			test.log(Status.ERROR, "Student hub Assertion Failed: URLs are not same.");
		    throw e;
		}
	    
	    Thread.sleep(2000);
	    File sc6 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc6, new File("./Scenario1/beforeClickingResources.png"));
	    
	    // check if Student resources text is there in href link
		driver.findElement(By.xpath("//a[contains(@href,\"Student-Resources.aspx\")]")).click();
		
		Thread.sleep(3000);
	    File sc7 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc7, new File("./Scenario1/afterClickingResources.png"));
		
		// click academics, classes and registration
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-gtm-sh-resources-group='Academics, Classes & Registration']"))).click();
//		driver.findElement(By.xpath("//*[@data-gtm-sh-resources-group='Academics, Classes & Registration']")).click();
		
		Thread.sleep(3000);
	    File sc8 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc8, new File("./Scenario1/afterClickingAcademicsClasses.png"));
		
		// click my transcript
		driver.findElement(By.xpath("//*[@data-gtm-sh-resources-link='My Transcript']")).click();
		
		// Switch focus to current tab
		Thread.sleep(5000);
		String currentTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
			}
		}
		
		// login again with username and password
		waitTimer.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		WebElement newUsername = driver.findElement(By.id("username"));
		newUsername.sendKeys(shortUname);
	    WebElement newPassword = driver.findElement(By.id("password"));
	    newPassword.sendKeys(mypass); // password
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//button[@name='_eventId_proceed']")).click();
	    
		//switch to Duo security
		driver.switchTo().frame("duo_iframe");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"auth_methods\"]/fieldset[1]/div[1]/button")).click();
		Thread.sleep(5000);
//		//switch window to default
		driver.switchTo().defaultContent();
	    
	    // change option to graduate
		File sc9 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc9, new File("./Scenario1/afterClickingTranscripts.png"));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[@value='GR']"))).click();
//	    driver.findElement(By.xpath("//option[@value='GR']")).click();
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Submit']"))).click();
//	    driver.findElement(By.xpath("//input[@value='Submit']")).click();
	        
	    Thread.sleep(5000);
	    File sc10 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc10, new File("./Scenario1/afterClickingGradTranscriptSubmit.png"));
	    
	    ChromeOptions opt = new ChromeOptions();
	    opt.addArguments("headless");
	    
	    ChromeDriver newdriver = new ChromeDriver(opt);
	    Thread.sleep(3000);

	    // print the transcript
	    newdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    Pdf pdf = ((PrintsPage) driver).print(new PrintOptions());
	    byte[] pdfBytes = OutputType.BYTES.convertFromBase64Png(pdf.getContent());
	    Path pdfFilePath = Paths.get(downloadFilepath+"\\mytranscript.pdf");
	    Files.write(pdfFilePath, pdfBytes);
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("alert('PDF is downloaded');");
	    
	    softAssertion.assertAll();
	    Thread.sleep(2000);
	    
	    driver.quit();
	}
	
	private static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
