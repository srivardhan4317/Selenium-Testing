package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.log4testng.Logger;


public class Report {
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected static ExtentSparkReporter htmlReporter;
    public static Logger Log = Logger.getLogger(Report.class);
    public static String fileName;

    public static void setup() {
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/Reports/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static void tearDown() {
        extent.flush();
    }


    public static void logApiResponse(String testCaseName, int statusCode) {
        System.out.println(testCaseName);
        test = extent.createTest("API Test: " + testCaseName);
        if (statusCode == 200) {
            test.info("Informational Response: " + statusCode);
        } else if (statusCode == 201) {
            test.pass("Created Successfully: " + statusCode);
        } else if (statusCode >= 400 && statusCode < 500) {
            test.fail("Client Error: " + statusCode);
        } else if (statusCode >= 500) {
            test.fail("Server Error: " + statusCode);
            throw new RuntimeException("Test failed due to server error: " + statusCode);
        } else {
            test.info("Unhandled Status Code: " + statusCode);
        }
    }

    public static void info_Test(String Stepdetails) {
        try {
            test.info(MarkupHelper.createLabel(Stepdetails, ExtentColor.GREY));
            info(Stepdetails);
            System.out.println("test Info >>> " + Stepdetails + "   XXXXXXXXXXXXXXXXX");
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
    }

    public static void setTest(ExtentTest newTest) {
        test = newTest;
    }

    public static void startTestCase(String sTestCaseName) {
        Log.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    public static void endTestCase(String sTestCaseName) {
        Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
    }

    public static void info(String message) {
        Log.info(message);
    }

    public static void fail(String testCaseName) {
        test.fail(testCaseName);
    }

    public static void pass(String testCaseName) {
        test = extent.createTest("API Test: " + testCaseName);
        test.pass("Passed");


    }

    public static void warn(String message) {
        Log.warn(message);
        Log.warn("warning in File " + Thread.currentThread().getStackTrace()[1].getFileName() + " at line no >>" + Thread.currentThread().getStackTrace()[1].getLineNumber());
    }

    public static void error(String message) {
        Log.error(message);
        Log.error("Error in File " + Thread.currentThread().getStackTrace()[1].getFileName() + " at line no >>" + Thread.currentThread().getStackTrace()[1].getLineNumber());
    }

    public static void fatal(String message) {
        Log.fatal(message);
        Log.fatal("fatal in File " + Thread.currentThread().getStackTrace()[1].getFileName() + " at line no >>" + Thread.currentThread().getStackTrace()[1].getLineNumber());
    }

    public static void debug(String message) {
        Log.debug(message);
    }
}