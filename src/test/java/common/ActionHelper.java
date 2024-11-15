package common;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;

public class ActionHelper {

    public static WebDriver driver;

    private static final ConfigLoader configLoader = new ConfigLoader();
    public static BrowserLaunch browserLaunch = new BrowserLaunch();
    public static ApiHelper api = new ApiHelper();
    public static ExcelUtils excel = new ExcelUtils();
//    public static Report report = new Report();

    public static Reporterrtwo reportertwo = new Reporterrtwo();

        public static String getScreenshot(WebDriver driver) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "/target/Screenshots";
//                    + System.currentTimeMillis() + ".png";
            File destination = new File(path);

            try {
                FileUtils.copyFile(src, destination);
            } catch (IOException e) {
                System.out.println("Capture Failed " + e.getMessage());
            }
            return path;
        }

    public static void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }

    }
}

