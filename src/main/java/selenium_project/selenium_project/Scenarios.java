package selenium_project.selenium_project;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Scenarios {
	ExtentHtmlReporter myreport;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeSuite
	public void reportSetup() {
		// report creation
		System.out.print("Started test cases");
		extent = new ExtentReports();
		myreport = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\custom\\customTestResults.html");
		extent.attachReporter(myreport);
	  }
	
	@Test(priority = 0)
	public void TestScenario1() throws IOException, InterruptedException {
		Scenario1 scenario1 = new Scenario1();
		scenario1.scenario1(extent, test);
	}
	
	@Test(priority = 1)
	public void TestScenario2() throws IOException, InterruptedException {
		Scenario2 scenario2 = new Scenario2();
		scenario2.scenario2(extent, test);
	}
	
	@Test(priority = 2)
	public void TestScenario3() throws IOException, InterruptedException {
		Scenario3 scenario3 = new Scenario3();
		scenario3.scenario3(extent, test);
	}
	
	@Test(priority = 3)
	public void TestScenario4() throws IOException, InterruptedException {
		Scenario4 scenario4 = new Scenario4();
		scenario4.scenario4(extent, test);
	}
	
	@Test(priority = 4)
	public void TestScenario5() throws IOException, InterruptedException {
		Scenario5 scenario5 = new Scenario5();
		scenario5.scenario5(extent, test);
	}
	
	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE)
			test.log(Status.FAIL, "failed test case" + result.getThrowable());
		
		System.out.print("Finished all test cases");
		extent.flush();
	}

}
