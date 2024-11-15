package scripts;

import common.ActionHelper;
import common.Report;
import org.testng.annotations.Test;

public class TestScript extends ActionHelper {

    @Test()
    void test() {
        Report.setup();
        try {
            ActionHelper.browserLaunch.launchBrowser("TC1", "chrome", "application");
//            report.pass("TC1");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            ActionHelper.quitBrowser();
            Report.tearDown();
        }

    }


}

