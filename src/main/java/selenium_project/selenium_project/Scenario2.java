package selenium_project.selenium_project;

import org.testng.Reporter;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scenario2 {

	public void scenario2(ExtentReports extent, ExtentTest test) throws InterruptedException, IOException {
		test = extent.createTest("Scenario 2 - Add two To-Do tasks for yourself.");
		test.log(Status.INFO, "Add two To-Do tasks in calendar.");
		
		 System.setProperty( 
            "webdriver.chrome.driver",
            "C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\drivers\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver(); 
		  
        // Maximize browser
        driver.manage().window().maximize();
  
        // Launching website 
        driver.get("https://northeastern.instructure.com");

        String filePath="C:\\Users\\rolwy\\eclipse-workspace\\selenium_project\\";

	    String fileName = "data";

	    // get the excel file
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

		// get the sheet, get the 1st row and the required cells
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
	    System.out.println("password: "+password);

		byte[] decodedpass = Base64.decodeBase64(password);
	    String mypass =  new String(decodedpass);

	    // only uncomment to get encoded bytes
		// byte[] encodedBytes = Base64.encodeBase64("yourpassword".getBytes());
	    // System.out.println("encodedBytes "+ new String(encodedBytes));

	    Thread.sleep(2000);
	    // fill username, password and hit submit
	    WebElement usernameField = driver.findElement(By.id("i0116"));
	    usernameField.sendKeys(username); // Replace with your username
	    WebElement loginButton = driver.findElement(By.id("idSIButton9"));
	    loginButton.click();
	    Thread.sleep(2000);
	    WebElement passwordField = driver.findElement(By.id("i0118"));
	    passwordField.sendKeys(mypass); // Replace with your password
	    Thread.sleep(1000);
	    WebElement signInButton = driver.findElement(By.id("idSIButton9"));
	    signInButton.click();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	    // wait for duo authentication
		Thread.sleep(5000);
	    // click trust button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button"))).click();

	    // hit the submit button
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-report-value='Submit']"))).click();

	    Thread.sleep(2000);
		File sc1 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("./Scenario2/beforeCalendarClick.png"));

	    // click calendar link
		WebElement calenderButton= driver.findElement(By.id("global_nav_calendar_link"));
        calenderButton.click();
        
        Thread.sleep(2000);
		File sc2 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc2, new File("./Scenario2/AfterCalendarClick.png"));

        Assertion Assertion = new Assertion();
        try {
			Assertion.assertEquals(driver.getTitle(), "Calendar");
			Reporter.log("Calendar Tab icon title Assertion Success - <b>Actual:</b> "+driver.getTitle()+ ",\n <b>Expected: </b>Calendar");
		    test.log(Status.INFO, "Calendar Tab icon title Assertion Success - <b>Actual:</b> "+driver.getTitle()+ ",\n <b>Expected: </b>Calendar");
		} catch (AssertionError e) {
			Reporter.log("Calendar Tab icon title Assertion: The tab name is not correct.");
			test.log(Status.ERROR, "Calendar Tab icon title Assertion: The tab name is not correct.");
		    throw e;
		}

        // select calendar and create new event - check if button is already clicked
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"calendars-context-list\"]/li[1]/span")));
        WebElement calendarRadioBtn = driver.findElement(By.xpath("//*[@id=\"calendars-context-list\"]/li[1]/span"));
        String isCalendarClicked = calendarRadioBtn.getAttribute("aria-checked");
        boolean clicked = Boolean.parseBoolean(isCalendarClicked);  
        Thread.sleep(1000);
        if (!clicked) {
        	calendarRadioBtn.click();
        }
        
        Thread.sleep(2000);
		File sc3 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc3, new File("./Scenario2/AfterCalendarSelection.png"));
        
        WebElement addButton= driver.findElement(By.id("create_new_event_link"));
        addButton.click();
        
        Thread.sleep(2000);
		File sc4 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc4, new File("./Scenario2/AfterClickingNewEventLink.png"));

        // click on My To Do
		Thread.sleep(2000);
		File sc5 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc5, new File("./Scenario2/BeforeClickingMyTodo.png"));
		
        WebElement liElement = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
        liElement.click();

        Thread.sleep(2000);
		File sc6 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc6, new File("./Scenario2/AfterClickingMyTodo.png"));

        // get title cell data
        XSSFCell title = row.getCell(4);
	    String Title=title.toString();

	    // Planner note title
        WebElement titleFeild = driver.findElement(By.id("planner_note_title"));
        titleFeild.sendKeys(Title);

    	XSSFCell date = row.getCell(5);
	    String Date=date.toString();

	    // find planner date element
		WebElement DateFeild = driver.findElement(By.id("planner_note_date"));

		DateFeild.clear();
		DateFeild.sendKeys(Date);

		// get the time cell value
		 XSSFCell time = row.getCell(6);
		 String Time=time.toString();

		 // set planner note time
		 WebElement TimeFeild = driver.findElement(By.id("planner_note_time"));
		 TimeFeild.sendKeys(Time);

	    // get details cell value
	    XSSFCell details = row.getCell(8);
	    String Details=details.toString();

		// add details to text area
		 WebElement DeatilsFeild = driver.findElement(By.id("details_textarea"));
		 DeatilsFeild.sendKeys(Details);
		 
	    Thread.sleep(2000);
		File sc7 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc7, new File("./Scenario2/AfterFillingTodoDetails.png"));

		// click submit
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button"))).click();

	    Thread.sleep(2000);
		File sc8 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc8, new File("./Scenario2/AfterSubmitTodo.png"));

	    // add another to-do
	    WebElement addButton2= driver.findElement(By.id("create_new_event_link"));
        addButton2.click();
        Thread.sleep(2000);

        WebElement liElement2 = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
        liElement2.click();
        Thread.sleep(2000);

        // get the rows and all the cell data
        XSSFRow row2=sheet.getRow(2);

        XSSFCell title2 = row2.getCell(4);
	    String Title2=title2.toString();
	    WebElement titleFeild2 = driver.findElement(By.id("planner_note_title"));
        titleFeild2.sendKeys(Title2);

        XSSFCell date2 = row2.getCell(5);
	    String Date2=date2.toString();

		WebElement DateFeild2 = driver.findElement(By.id("planner_note_date"));
		DateFeild2.clear();

		DateFeild2.sendKeys(Date2);

		 XSSFCell time2 = row2.getCell(6);
		 String Time2=time2.toString();
		 WebElement TimeFeild2 = driver.findElement(By.id("planner_note_time"));

		TimeFeild2.sendKeys(Time2);

	    XSSFCell details2 = row2.getCell(8);
	    String Details2=details2.toString();
		WebElement DeatilsFeild2 = driver.findElement(By.id("details_textarea"));
		DeatilsFeild2.sendKeys(Details2);
	    Thread.sleep(2000);

	    // save button click
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button"))).click();

	    Thread.sleep(2000);
		File sc9 = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc9, new File("./Scenario2/ShowTodoInCalendar.png"));

		Thread.sleep(2000);
	    driver.quit();
	}

//	public void main(String args[]) throws InterruptedException, IOException {
//		scenario2();
//	}
}