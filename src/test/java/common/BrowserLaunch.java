package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserLaunch {

    private WebDriver driver;
    public static Report report = new Report();
    private static final ConfigLoader configLoader = new ConfigLoader();


    public void launchBrowser(String testcasename,String browserName, String url) {
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();

            } else if (browserName.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();

            } else {
                System.out.println("Please provide a valid browser name (e.g., 'chrome' or 'edge').");
            }
            setupBrowser(url);
            driver.manage().window().maximize();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public WebDriver launchIncognitoBrowser(String testcasename, String browserName,String url) {

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("incognito");
            driver = new ChromeDriver(chromeOptions);

        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("inprivate");  // Edge incognito mode
            driver = new EdgeDriver(edgeOptions);

        } else {
            System.out.println("Please provide a valid browser name (e.g., 'chrome' or 'edge').");
            return null;
        }

        setupBrowser(url);
        return driver;
    }

    private void setupBrowser(String url) {
        driver.manage().window().fullscreen();
        driver.manage().deleteAllCookies();
        driver.get(configLoader.getProperty(url)); // Load application URL
    }

}
