package common;

import commonPackage.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.concurrent.TimeUnit;

public class BrowserLaunch {

    private WebDriver driver;
    public static Report report = new Report();
    private static final ConfigLoader configLoader = new ConfigLoader();

    public void launchBrowser(String browserName, String url) {
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
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

    public WebDriver launchIncognitoBrowser(String browserName, String url) {
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("incognito");
                driver = new ChromeDriver(chromeOptions);

            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("inprivate");
                driver = new EdgeDriver(edgeOptions);

            } else {
                System.out.println("Please provide a valid browser name (e.g., 'chrome' or 'edge').");
                return null;
            }

            setupBrowser(url);
            return driver;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupBrowser(String url) {
        driver.manage().window().fullscreen();
        driver.manage().deleteAllCookies();
        driver.get(configLoader.getProperty(url));
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
