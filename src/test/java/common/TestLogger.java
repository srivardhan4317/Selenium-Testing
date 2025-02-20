package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class TestLogger {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testlog = new ThreadLocal<>();

    public static void initializeReport(String reportPath) {
        if (extentReports == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + reportPath);
            extentReports = new ExtentReports();
            String logoPath = "";
            String logoHtml = "logopath";
            sparkReporter.config().setJs("document.body.insertAdjacentHTML('afterbegin','" + logoHtml + "');");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Automation Reporting");
            extentReports.attachReporter(sparkReporter);
        }
    }

    public static void startTest(String testName, String testDescription) {
        if (extentReports == null) {
            throw new IllegalStateException("ExtentReports not initialized .Please call initializeReport() first");
        }
        ExtentTest logger = extentReports.createTest(testName, testDescription);
        testlog.set(logger);
    }

    public static void endTest(String testName, String testDescription) {
        if (extentReports != null) {
            ExtentTest logger = extentReports.createTest(testName, testDescription);
            testlog.set(logger);
            extentReports.flush();
        }
    }

    public static void closeReporter() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static ExtentTest getLogger() {
        ExtentTest logger = testlog.get();
        if (logger == null) {
            throw new IllegalStateException("ExtentReports not initialized .Please call initializeReport() first");
        }
        return logger;
    }

    public static void logTestStatus(String message, String status) {
        switch (status.toLowerCase()) {
            case "pass":
                getLogger().pass(message);
                break;
            case "fail":
                getLogger().pass(message);
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
                break;
        }
    }

    public static void logWithMarkup(String message, ExtentColor color){
        getLogger().info(MarkupHelper.createLabel(message,color));
    }

    public static void logWithScreenshot(String message,String screendhotPath){
        try{
            getLogger().info(message, MediaEntityBuilder.createScreenCaptureFromPath(screendhotPath).build());
        }
        catch (Exception e){
            getLogger().fail("Failed to add screenshot: "+ e.getMessage());
        }
    }
}














