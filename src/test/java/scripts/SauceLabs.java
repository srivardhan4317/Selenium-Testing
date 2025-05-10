package scripts;

import commonPackage.ActionHelper;
import commonPackage.TestSetup;
import org.testng.annotations.Test;

public class SauceLabs extends TestSetup {


    @Test
    public void UiTesting(){
try{
//    ActionHelper.browserLaunch.launchBrowser("chrome", "saucelabs");

} catch (Exception e) {
    throw new RuntimeException(e);
}
finally {
    driver.quit();
    driver.close();
}
    }
}
