package common;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class AppiumClass extends WebDriverHelper{


    @BeforeTest
    public void setup(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName","Android");
        caps.setCapability("deviceName","Android Emulator");
        caps.setCapability("platformVersion","11");
        caps.setCapability("appPackage","com.example.android");
        caps.setCapability("appActivity", "com.example.android.MainActivity");  // Replace with your app's activity

//        caps.setCapability(CapabilityType.PLATFORM_NAME,"ANDROID");
//
//        try {
//            AppiumDriver<MobileElement> driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
//
//            MobileElement button = driver.findElement(By.id("com.example.android:id/button"));
//            button.click();
//
//            Thread.sleep(5000);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @AfterTest
    public void teardown(){
        driver.quit();
    }
}
