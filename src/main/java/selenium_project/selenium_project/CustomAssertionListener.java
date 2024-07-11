package selenium_project.selenium_project;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomAssertionListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        logAssertionResult(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        logAssertionResult(tr);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logAssertionResult(tr);
    }

    private void logAssertionResult(ITestResult tr) {
        if (tr.getThrowable() != null) {
            System.out.println("Test that has failed assertion: " + tr.getName());
            System.out.println("Log: " + tr.getThrowable().getMessage());
        }
    }
}