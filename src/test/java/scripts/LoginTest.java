package scripts;

import java.io.IOException;

import common.ActionHelper;
import common.Reporter;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class LoginTest {
    WebDriver driver;
    ExtentTest logger;

    @BeforeMethod
    public void setup() {
        // Set the WebDriver path and initialize Extent Report
        ActionHelper.browserLaunch.launchBrowser("TC1", "chrome", "application");

        // Initialize Extent report and create test log
        Reporter.getReporter();
        logger = Reporter.createTest("LoginTest");
    }

    @Test
    public void testGoogleTitle() throws IOException {
//        System.out.println("Title is " + driver.getTitle());
        ActionHelper.reportertwo.pass();

        // Verify if title contains expected text (this will likely fail to trigger screenshot capture)
//        Assert.assertTrue(driver.getTitle().contains("Mukesh"));
    }

    @AfterMethod
    public void tearDown() throws IOException {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            String screenshotPath = ActionHelper.getScreenshot(driver);
//            logger.fail(result.getThrowable().getMessage(),
//                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//        } else if (result.getStatus() == ITestResult.SUCCESS) {
//            logger.pass("Test Passed");
//        }

//        Reporter.flushReport();

        // Close the driver after test execution
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
