package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporter {
    private static ExtentReports extent;
    private static ExtentTest test;

    // Initialize the Extent Report
    public static ExtentReports getReporter() {
        if (extent == null) {
            ExtentSparkReporter  htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/Reports/extentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }

    // Create a new test log in the report
    public static ExtentTest createTest(String testName) {
        test = getReporter().createTest(testName);
        return test;
    }

    // Flush the report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
