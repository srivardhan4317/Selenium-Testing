package common;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class TestSetup {

    private static final String reportPath = "/target/Reports/";

    @BeforeSuite
    public static void setUp(){
        String reportName = reportPath + "Report.html";
        TestLogger.initializeReport(reportName);
    }

    @AfterSuite
    public static void tearDown(){
        TestLogger.closeReporter();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method){
        String testName = method.getName();
        String testDescription = "Test Description:" +testName;
        TestLogger.startTest(testName,testDescription);
        Report.info("Started test: "+ testName);
    }

    @AfterMethod(alwaysRun = true)
    public void AfterMethod(Method method){
        String testName = method.getName();
        String testDescription = "Test Description:" +testName;
        TestLogger.startTest(testName,testDescription);
        Report.info("Finished test: "+ testName);
    }

}















