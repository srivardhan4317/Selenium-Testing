package scripts;

import common.ActionHelper;
import common.Report;
import org.testng.annotations.Test;

public class TestScript1 extends ActionHelper {

    @Test()
    void test2() {
        Report.setup();
        try {
            ActionHelper.browserLaunch.launchBrowser("TC2", "edge", "application");
//            report.pass("TC2");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            ActionHelper.quitBrowser();
            Report.tearDown();
        }

    }


}

