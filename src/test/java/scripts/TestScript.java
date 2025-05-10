package scripts;

import commonPackage.ActionHelper;
import commonPackage.TestReporter;
import commonPackage.TestSetup;
import org.testng.annotations.Test;

public class TestScript extends TestSetup {

    @Test(groups = {"UI"})
    public void testLogin() {
        driver.get("https://www.saucedemo.com/");
        TestReporter.logTestStatus("Opened the login page", "info");
    }

    @Test(groups = {"API"})
    public void testApiCall() {
        TestReporter.logTestStatus("API call successful", "info");
    }

    @Test(groups = {"API"})
    public void customerNumberIdentification() {
        TestReporter.startChildTest("TestCase 1");
        TestReporter.passStep("Validated input");
        TestReporter.endChildTest();

        TestReporter.startChildTest("TestCase 2");
        TestReporter.failStep("Failed to validate response");
        TestReporter.endChildTest();

        TestReporter.startChildTest("TestCase 3");
        TestReporter.passStep("Success screen verified");
        TestReporter.endChildTest();

        // Repeat for TestCase 4 to 8...
    }


}

