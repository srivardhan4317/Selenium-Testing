package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class Reporterrtwo {


        private ExtentReports extent;  // Class-level variable

        public void setup() {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/Reports/extentReport.html");

            String logoPath = "/src/test/resources/TestData/Wmlogo.png";
            String logoHtml = "<img src='" + logoPath + "' style='padding-right: -10px; float: right; height: 5%; margin-right: 0.5px; margin-top: -5px; width: auto;'>";
//            String logoHtml = "<img src='" + logoPath + "' style='padding-right: -10px; float: right; height: 1%; margin-right: 0.5px; margin-top: -10px; width: auto;'>";

            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Demo Automation Report");
            spark.config().setJs("document.body.insertAdjacentHTML('afterbegin', `" + logoHtml + "`);");

            extent.attachReporter(spark);
        }

        public void pass() {
            if (extent == null) {
                setup();
            }
            ExtentTest test = extent.createTest("Login Test").assignCategory("Regression");
            test.pass("Login test started successfully");

            // Flush the report after the test
            extent.flush();
        }



    public void report() throws IOException {
        // Initialize ExtentReports
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/target/Reports/extentReport.html");


        String logoPath = "/src/test/resources/TestData/Wmlogo.png";


        String logoHtml = "<img src='" + logoPath + "' style='padding-right: -10px; float: right; height: 1%; margin-right: 0.5px; margin-top: -10px; width: auto;'>";


        // Set report configurations
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Demo Automation Report");

        // Inject the logo at the top of the report using JavaScript
        spark.config().setJs("document.body.insertAdjacentHTML('afterbegin', `" + logoHtml + "`);");

        // Attach reporters
        extent.attachReporter(spark);

        // Create and log a test
        ExtentTest test = extent.createTest("Login Test").assignCategory("Regression");
        test.pass("Login test started successfully");
        test.pass("Opened application");
        test.pass("Login test completed successfully");
        test.pass(MarkupHelper.createLabel("Login test", ExtentColor.GREEN));
        test.fail("An intentional failure");
        test.info("Waiting for response");
        test.skip("Skipping a step");
        test.pass(MarkupHelper.createUnorderedList(Arrays.asList("Selenium", "Appium", "RestAssured")).getMarkup());

        // Another test example
        ExtentTest test1 = extent.createTest("Home Page Test");
        test1.pass("Home page test started successfully");
        test1.pass("Navigated to home page");
        test1.pass("Home page test completed successfully");
        test1.fail("Failed to load specific element");
        test1.info("Waiting for network response");
        test1.skip("Skipping additional checks");

        // Finalize the report
        extent.flush();
    }
}
