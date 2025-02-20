package common;

import com.aventstack.extentreports.markuputils.ExtentColor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting {
    private static final ConfigLoader configLoader = new ConfigLoader();
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + configLoader.getProperty("screenshots");

    private static void createScreenshotDir() {
        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
    }

    public static String captureScreenshot(WebDriver driver) {
        createScreenshotDir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String screenshotName = "screenshot_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + screenshotName;
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static void passStep(String stepDesc) {
        TestLogger.logTestStatus(stepDesc, "pass");
    }

    public static void passStep(String stepDesc, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        TestLogger.logWithScreenshot(stepDesc, screenshotPath);
    }

    public static void failStep(String stepDesc) {
        TestLogger.logTestStatus(stepDesc, "fail");
    }

    public static void failStep(String stepDesc, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        TestLogger.logWithScreenshot(stepDesc, screenshotPath);
    }

    public static void infoStep(String stepDesc) {
        TestLogger.logTestStatus(stepDesc, "info");
    }

    public static void infoStep(String stepDesc, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        TestLogger.logWithScreenshot(stepDesc, screenshotPath);
    }

    public static void skipStep(String stepDesc) {
        TestLogger.logTestStatus(stepDesc, "skip");
    }

    public static void skipStep(String stepDesc, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        TestLogger.logWithScreenshot(stepDesc, screenshotPath);
    }

    public static void warningStep(String stepDesc) {
        TestLogger.logTestStatus(stepDesc, "warning");
    }

    public static void warningStep(String stepDesc, WebDriver driver) {
        String screenshotPath = captureScreenshot(driver);
        TestLogger.logWithScreenshot(stepDesc, screenshotPath);
    }

    public static void conditionCheckTest(String actualVal, String ExpVal, String ObjDesc) {
        if (actualVal.equals(ExpVal)) {
            TestLogger.logWithMarkup("Condition " + ObjDesc + " passed. Actual value: " + actualVal + ", Expected value: " + ExpVal, ExtentColor.GREEN);
        } else {
            TestLogger.logWithMarkup("Condition " + ObjDesc + " failed. Actual value: " + actualVal + ", Expected value: " + ExpVal, ExtentColor.RED);
        }
    }
}