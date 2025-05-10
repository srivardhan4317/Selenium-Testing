package commonPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestSetup {

    protected static WebDriver driver;

    @BeforeSuite
    public void setUpSuite() {
        String reportBasePath = System.getProperty("user.dir");
        TestReporter.initializeReport(reportBasePath);
    }

    @AfterSuite
    public void tearDownSuite() {
        TestReporter.closeReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {

        if (isTestInGroup(method, "UI")) {
            initializeDriver();
        }
        String testName = method.getName();
        TestReporter.startTest(testName, testName);

        TestReporter.logTestStatus("Starting test: " + testName, "info");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method) {
        String testName = method.getName();

        TestReporter.endTest(testName, testName);
        TestReporter.logTestStatus("Ending test: " + testName, "info");

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private boolean isTestInGroup(Method method, String group) {
        for (String methodGroup : method.getAnnotation(Test.class).groups()) {
            if (methodGroup.equals(group)) {
                return true;
            }
        }
        return false;
    }

    private void initializeDriver() {
        if (driver == null) {
            try {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/Drivers/chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

                TestReporter.logTestStatus("WebDriver initialized successfully", "info");
            } catch (Exception e) {
                TestReporter.logTestStatus("Error initializing WebDriver: " + e.getMessage(), "fail");
                throw new RuntimeException("Failed to initialize WebDriver", e);
            }
        }
    }

}
