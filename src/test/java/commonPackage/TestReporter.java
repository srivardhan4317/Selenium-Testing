package commonPackage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class TestReporter {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testLog = new ThreadLocal<>();
    private static final String REPORT_FOLDER = "/target/Reports/";
    private static final String SCREENSHOT_DIR = REPORT_FOLDER + "Screenshots/";
    private static final ThreadLocal<ExtentTest> childTestLog = new ThreadLocal<>();


    private static void createScreenshotDir() {
        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
    }

    public static void initializeReport(String reportBasePath) {

            String currentDate = getCurrentDate();
            String dateFolderPath = reportBasePath + REPORT_FOLDER + currentDate;
            String reportPath = dateFolderPath+ File.separator + "Automation_Report_"+ getCurrentDate() +".html";
            File reportFile = new File(reportPath);
            if(reportFile.exists()){
                boolean deleted = reportFile.delete();
                if(deleted){
                    System.out.println("Old report deleted successfully");
                }
                else{
                    System.out.println("Failed to delete old report");
                }
            }
        if (extentReports == null) {

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setTheme(Theme.STANDARD);
            reporter.config().setDocumentTitle("Test Report");
            reporter.config().setReportName("Test Execution Summary");
            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
        }
    }


    public static void startTest(String testName, String description) {
        if (extentReports == null) {
            throw new IllegalStateException("Report not initialized.");
        }
        ExtentTest test = extentReports.createTest(testName, description);
        testLog.set(test);
    }

    public static void endTest(String testName, String description) {
        if (testLog != null) {
            extentReports.flush();
        }
    }

    public static void startChildTest(String childTestName) {
        ExtentTest parent = testLog.get();
        if (parent == null) {
            throw new IllegalStateException("Parent test not started. Call startTest() first.");
        }
        ExtentTest child = parent.createNode(childTestName);
        childTestLog.set(child);
    }


    public static ExtentTest getLogger() {
        ExtentTest childLogger = childTestLog.get();
        if (childLogger != null) return childLogger;

        ExtentTest parentLogger = testLog.get();
        if (parentLogger == null) {
            throw new IllegalStateException("No test is currently running. Call startTest() first.");
        }
        return parentLogger;
    }

    public static void endChildTest() {
        childTestLog.remove();
    }



//    public static ExtentTest getLogger() {
//        ExtentTest logger = testLog.get();
//        if (logger == null) {
//            throw new IllegalStateException("No test is currently running. Call startTest() first.");
//        }
//        return logger;
//    }

    public static void logTestStatus(String message, String status) {
        switch (status.toLowerCase()) {
            case "pass":
                getLogger().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
                break;
            case "fail":
                getLogger().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
                break;
            case "info":
                getLogger().info(message);
                break;
            case "skip":
                getLogger().skip(message);
                break;
            case "warning":
                getLogger().warning(message);
                break;
            default:
                getLogger().info(message);
        }
    }

    public static String captureScreenshot(WebDriver driver) {
        createScreenshotDir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String screenshotName = "screenshot_" + timestamp + ".png";
        String relativePath = SCREENSHOT_DIR + screenshotName;
        String fullPath = System.getProperty("user.dir") + relativePath;

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(fullPath));
            return relativePath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static void logWithScreenshot(String message, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        try {
            getLogger().info(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            getLogger().fail("Unable to add screenshot: " + e.getMessage());
        }
    }

    public static void conditionCheck(String actual, String expected, String description) {
        if (actual.equals(expected)) {
            getLogger().pass(MarkupHelper.createLabel(description + " passed. Actual: " + actual + ", Expected: " + expected, ExtentColor.GREEN));
        } else {
            getLogger().fail(MarkupHelper.createLabel(description + " failed. Actual: " + actual + ", Expected: " + expected, ExtentColor.RED));
        }
    }

    private static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.getMonthValue() + "_" + now.getDayOfMonth() + "_" + now.getYear();
    }

    public static void passStep(String stepDesc) {
        logTestStatus(stepDesc, "pass");
    }

    public static void failStep(String stepDesc) {
        logTestStatus(stepDesc, "fail");
    }

    public static void infoStep(String stepDesc) {
        logTestStatus(stepDesc, "info");
    }

    public static void skipStep(String stepDesc) {
        logTestStatus(stepDesc, "skip");
    }

    public static void warningStep(String stepDesc) {
        logTestStatus(stepDesc, "warning");
    }

    public static void passStep(String stepDesc, WebDriver driver) {
        logWithScreenshot(stepDesc, driver);
    }

    public static void failStep(String stepDesc, WebDriver driver) {
        logWithScreenshot(stepDesc, driver);
    }

    public static void infoStep(String stepDesc, WebDriver driver) {
        logWithScreenshot(stepDesc, driver);
    }

    public static void skipStep(String stepDesc, WebDriver driver) {
        logWithScreenshot(stepDesc, driver);
    }

    public static void warningStep(String stepDesc, WebDriver driver) {
        logWithScreenshot(stepDesc, driver);
    }

    public static void logWithMarkup(String message, ExtentColor color) {
        getLogger().info(MarkupHelper.createLabel(message, color));
    }

    public static void closeReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

}
