package common;


import freemarker.log.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.LocalDateTime;


public class MobileDriverHelper {
    public static final Logger logger = Logger.getLogger(MobileDriverHelper.class.getName());
    protected static PropertiesHelper propHelper = PropertiesHelper.getInstance();
    public static AppiumDriver driver;
    public static final int DEFAULT_EXPLICIT_WAIT = Integer.parseInt(propHelper.getConfigPropProperty("default_explicit_wait"));
    public static String lab = System.getProperty("lab", propHelper.getConfigPropProperty("lab"));
    public static String apiToken, sessionId, starttime, endtime, scenarioTags, APP = null;
    public static String platform = System.getProperty("platform", propHelper.getConfigPropProperty("platform"));
    public static String apiServer = System.getProperty("apiServer", propHelper.getConfigPropProperty("apiServer"));
    public static boolean captureVideo = Boolean.parseBoolean(System.getProperty("captureVideo", propHelper.getConfigPropProperty("captureVideo")));
    //	public static boolean directConnect = Boolean.parseBoolean(System.getProperty("directConnect", propHelper.getConfigPropProperty("directConnect")));
    public static boolean captureNetwork = Boolean.parseBoolean(System.getProperty("captureNetwork", propHelper.getConfigPropProperty("captureNetwork")));
    public static String serverUrl = System.getProperty("serverUrl", propHelper.getConfigPropProperty("serverUrl"));
    public static String lambdatestHubUrl = System.getProperty("lambdatestHubUrl", propHelper.getConfigPropProperty("lambdatestHubUrl"));
    public static String testdatafile = System.getProperty("testdatafilename", propHelper.getConfigPropProperty("testdatafilename"));
    public static String weburl = System.getProperty("weburl", propHelper.getConfigPropProperty("weburl"));
    public static String ios_automationName = System.getProperty("ios_automationName", propHelper.getConfigPropProperty("ios_automationName"));
    public static String ios_udid = System.getProperty("ios_udid", propHelper.getConfigPropProperty("ios_udid"));
    public static String ios_appiumVersion = System.getProperty("ios_appiumVersion", propHelper.getConfigPropProperty("ios_appiumVersion"));
    public static String ios_appsettings = System.getProperty("ios_appsettings", propHelper.getConfigPropProperty("ios_appsettings"));
    public static String ios_app = System.getProperty("ios_app", propHelper.getConfigPropProperty("ios_app"));
    public static String ios_lambdaTestapp = System.getProperty("ios_lambdaTestapp", propHelper.getConfigPropProperty("ios_lambdaTestapp"));
    public static String ios_deviceName = System.getProperty("ios_deviceName", propHelper.getConfigPropProperty("ios_deviceName"));
    public static String ios_platformVersion = System.getProperty("ios_platformVersion", propHelper.getConfigPropProperty("ios_platformVersion"));

    public static String android_automationName = System.getProperty("android_automationName", propHelper.getConfigPropProperty("android_automationName"));
    public static String android_appiumVersion = System.getProperty("android_appiumVersion", propHelper.getConfigPropProperty("android_appiumVersion"));
    public static String android_udid = System.getProperty("android_udid", propHelper.getConfigPropProperty("android_udid"));
    public static String android_appPackage = System.getProperty("android_appPackage", propHelper.getConfigPropProperty("android_appPackage"));
    public static String android_appActivity = System.getProperty("android_appActivity", propHelper.getConfigPropProperty("android_appActivity"));
    public static String android_app = System.getProperty("android_app", propHelper.getConfigPropProperty("android_app"));
    public static String android_lambdaTestapp = System.getProperty("android_lambdaTestapp", propHelper.getConfigPropProperty("android_lambdaTestapp"));
    public static String android_deviceName = System.getProperty("android_deviceName", propHelper.getConfigPropProperty("android_deviceName"));
    public static String android_platformVersion = System.getProperty("android_platformVersion", propHelper.getConfigPropProperty("android_platformVersion"));
    public static boolean android_directConnect = Boolean.parseBoolean(System.getProperty("android_directConnect", propHelper.getConfigPropProperty("android_directConnect")));
    public static boolean ios_directConnect = Boolean.parseBoolean(System.getProperty("ios_directConnect", propHelper.getConfigPropProperty("ios_directConnect")));

    public void initializeDriver() {
        if (lab.equalsIgnoreCase("HeadSpin")) {
            apiToken = (serverUrl).split("/")[4];
            if (platform.equalsIgnoreCase("iOS")) {
                APP = ios_app;
                DesiredCapabilities ios_caps = new DesiredCapabilities();
                ios_caps.setCapability("automationName", ios_automationName);
                ios_caps.setCapability("platformName", platform);
                ios_caps.setCapability("bundleId", ios_app);
                ios_caps.setCapability("udid", ios_udid);
                ios_caps.setCapability("autoAcceptAlerts", false);
                ios_caps.setCapability("autoDismissAlerts", false);
                // ios_caps.setCapability("headspin:controlLock", true);
                ios_caps.setCapability("deviceOrientation", "potrait");
                ios_caps.setCapability("headspin:ignoreFailedDevices", false);
                ios_caps.setCapability("headspin:capture.video", captureVideo);
                ios_caps.setCapability("directConnect", ios_directConnect);
                ios_caps.setCapability("headspin:capture.network", captureNetwork);
                ios_caps.setCapability("snapshotMaxDepth", 59);
                ios_caps.setCapability("customSnapshotTimeout", 50000);
                try {
                    System.out.println("URL and Capabilities : " + new URL(serverUrl) + " " + ios_caps.toString());
                    driver = new IOSDriver(new URL(serverUrl), ios_caps);
                    System.out.println("iOS Driver Initialized ....................................");
                    driver.setSetting("customSnapshotTimeout", 50000);
                    driver.setSetting("snapshotMaxDepth", 60);
                } catch (Exception exp) {
                    System.out.println("Unable to create iOS driver ....................................");
                    System.out.println(exp.getCause());
                }
            } else if (platform.equalsIgnoreCase("android")) {
                APP = android_appPackage;
                DesiredCapabilities android_caps = new DesiredCapabilities();
                android_caps.setCapability("automationName", android_automationName);
                android_caps.setCapability("platformName", platform);
                android_caps.setCapability("appPackage", android_appPackage);
                android_caps.setCapability("appActivity", android_appActivity);
                android_caps.setCapability("udid", android_udid);
                android_caps.setCapability("directConnect", android_directConnect);
                android_caps.setCapability("autoAcceptAlerts", false);
                android_caps.setCapability("autoDismissAlerts", false);
                // android_caps.setCapability("headspin:controlLock", true);
                android_caps.setCapability("deviceOrientation", "potrait");
                android_caps.setCapability("headspin:ignoreFailedDevices", false);
                android_caps.setCapability("headspin:appiumVersion", android_appiumVersion);
                android_caps.setCapability("headspin:capture.video", captureVideo);
                android_caps.setCapability("headspin:capture.network", captureNetwork);
                try {
                    System.out.println("URL and Capabilities : " + new URL(serverUrl) + android_caps.toString());
                    driver = new AndroidDriver(new URL(serverUrl), android_caps);
                    System.out.println("Android Driver Initialized ....................................");
                } catch (Exception exp) {
                    System.out.println("Unable to create Android driver ....................................");
                    System.out.println(exp.getCause());
                }
            }
            starttime = Integer.toString(getTime());
            sessionId = driver.getSessionId().toString();
            logger.debug("Session ID : " + sessionId);
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("visual", true);
            capabilities.setCapability("network", true);
            capabilities.setCapability("video", true);
            capabilities.setCapability("isRealMobile", true);
            capabilities.setCapability("console", true);
            if (platform.equalsIgnoreCase("iOS")) {
                capabilities.setCapability("platformName", "ios");
                capabilities.setCapability("deviceName", ios_deviceName);
                capabilities.setCapability("platformVersion", ios_platformVersion);
                capabilities.setCapability("app", ios_lambdaTestapp);
                capabilities.setCapability("automationName", "xcuitest");
                capabilities.setCapability("snapshotMaxDepth", 59);
                capabilities.setCapability("customSnapshotTimeout", 50000);
                capabilities.setCapability("appiumVersion", ios_appiumVersion);

                try {
                    System.out.println("URL and Capabilities : " + new URL(lambdatestHubUrl) + capabilities.toString());
                    driver = new IOSDriver(new URL(lambdatestHubUrl), capabilities);
                    sessionId = driver.getSessionId().toString();
                    System.out.println("sessionId :: " + sessionId);
                    System.out.println("iOS Driver Initialized ....................................");
                    Thread.sleep(5000);
                    driver.setSetting("customSnapshotTimeout", 50000);
                    driver.setSetting("snapshotMaxDepth", 60);
                } catch (Exception exp) {
                    System.out.println("Unable to create iOS driver ....................................");
                    System.out.println(exp.getCause());
                }
            } else {
                capabilities.setCapability("platformName", "android");
                capabilities.setCapability("deviceName", android_deviceName);
                capabilities.setCapability("platformVersion", android_platformVersion);
                capabilities.setCapability("app", android_lambdaTestapp);
                capabilities.setCapability("automationName", "uiautomator2");
                capabilities.setCapability("appiumVersion", android_appiumVersion);
                try {
                    System.out.println("URL and Capabilities : " + new URL(lambdatestHubUrl) + capabilities.toString());
                    driver = new AndroidDriver(new URL(lambdatestHubUrl), capabilities);
                    sessionId = driver.getSessionId().toString();
                    System.out.println("sessionId :: " + sessionId);
                    System.out.println("Android Driver Initialized ....................................");
                    Thread.sleep(5000);
                } catch (Exception exp) {
                    System.out.println("Unable to create Android driver ....................................");
                    System.out.println(exp.getCause());
                }
            }
            starttime = Integer.toString(getTime());
            logger.debug("Session ID : " + sessionId);
        }
    }

    public void user_launches_the_browser_and_navigates_to_page() {
        if (lab.equalsIgnoreCase("HeadSpin")) {
            apiToken = (serverUrl).split("/")[4];
            if (platform.equalsIgnoreCase("iOS")) {
                //APP = ios_app;
                DesiredCapabilities ios_caps = new DesiredCapabilities();
                ios_caps.setCapability("automationName", ios_automationName);
                ios_caps.setCapability("platformName", platform);
                ios_caps.setCapability("bundleId", "com.apple.mobilesafari");
                ios_caps.setCapability("udid", ios_udid);
                ios_caps.setCapability("udid", ios_udid);
                ios_caps.setCapability("autoAcceptAlerts", false);
                ios_caps.setCapability("autoDismissAlerts", false);
                // ios_caps.setCapability("headspin:controlLock", true);
                ios_caps.setCapability("autoGrantPermissions", true);
                ios_caps.setCapability("deviceOrientation", "potrait");
                ios_caps.setCapability("headspin:ignoreFailedDevices", false);
                ios_caps.setCapability("headspin:capture.video", captureVideo);
                ios_caps.setCapability("directConnect", ios_directConnect);
                ios_caps.setCapability("headspin:capture.network", captureNetwork);
                ios_caps.setCapability("snapshotMaxDepth", 59);
                ios_caps.setCapability("customSnapshotTimeout", 50000);
                try {
                    System.out.println("URL and Capabilities : " + new URL(serverUrl) + " " + ios_caps.toString());
                    driver = new IOSDriver(new URL(serverUrl), ios_caps);
                    System.out.println("iOS Driver Initialized ....................................");
                    driver.setSetting("customSnapshotTimeout", 50000);
                    driver.setSetting("snapshotMaxDepth", 60);
                } catch (Exception exp) {
                    System.out.println("Unable to create iOS driver ....................................");
                    System.out.println(exp.getCause());
                }
            }
        }
    }

    public static int getTime() {
        return LocalDateTime.now().toLocalTime().toSecondOfDay();
    }

    public static void quitDriver() {
        logger.debug("inside teardown()....");
        if (driver != null) {
            driver.quit();
            logger.debug("driver quit success.");
        }
    }
}
