package common;

import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MobileCommonActionHelper extends MobileDriverHelper {

    public static By allowPopup = By.xpath("//XCUIElementTypeButton[@name='Allow']");
    public static By addressBar = By.xpath("//XCUIElementTypeButton[@name='URL'] | //XCUIElementTypeTextField[@name='TabBarItemTitle'] | //XCUIElementTypeTextField[@label='Address']");
    public static By goBtn = By.xpath("//*[@label='go'] | //XCUIElementTypeButton[@name='Done'] | //*[@label='search'] | //*[@name='Go'] | //*[@text='ENTER'] | //*[@text='Go'] | //*[@id='Go'] | //*[contains(@label, 'Done')]");

    public static PropertiesHelper webPropHelper = PropertiesHelper.getInstance();
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, KEYPAD, KEYPADDOWN, UPNEW;
    }
    public void openSafariBrowser(String url){
        if (platform.equalsIgnoreCase("iOS")) {
            ((IOSDriver)driver).activateApp("com.apple.mobilesafari");
            if(isElementDisplayed(allowPopup)){
                tapOnElement(allowPopup);
            }
            clearnSetText(addressBar, webPropHelper.getConfigPropProperty(url) + "\n");
            if(isElementDisplayed(allowPopup,10)){
                tapOnElement(allowPopup);
            }
        }
        ((IOSDriver)driver).rotate(ScreenOrientation.PORTRAIT);
    }
    public static boolean isElementDisplayed(By locator) {
        boolean result = false;
        try {
            System.out.println();
            System.out.println("Checking display of locator " + locator.toString());
            WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(DEFAULT_EXPLICIT_WAIT)) ;
            result = wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
            System.out.println(result);
        } catch (Exception e) {
            logger.warn("Element not Displayed, Timed Out !!! %s " + locator.toString());
        }
        return result;
    }
    public static void tapOnElement(By locator) {
        System.out.println("Tap on element with locator : " + locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_WAIT));
        starttime = Integer.toString(getTime());
        wait.until(ExpectedConditions.presenceOfElementLocated(locator)).click();
    }
    public static void clearnSetText(By locator, String text) {
        System.out.println("Entering value " + text + " in element " + locator.toString());
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        hideKeypad();
    }
    public static void iOSAndroidEnterAction() {
        try {
            if (platform.equalsIgnoreCase("iOS")) {
                starttime = Integer.toString(getTime());
                if (isElementDisplayed(goBtn)) {
                    tapOnElement(goBtn);
                }
            } else {
                starttime = Integer.toString(getTime());
//              new Actions(driver).sendKeys(Keys.ENTER).click().perform();
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
            }
            hideKeypad();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public static void pressEnterKey(WebDriver driver, By ele) {
        WebElement element = driver.findElement(ele);
        element.sendKeys(Keys.RETURN);
    }
    public static void setValue(By locator, String text) {
        System.out.println();
        System.out.println("Entering value " + text + " in element " + locator.toString());
        driver.findElement(locator).sendKeys(text);
        //hideKeypad();
    }
    public static void hideKeypad() {
        try {
            ((HidesKeyboard)driver).hideKeyboard();
            //Dimension size = driver.manage().window().getSize();
            //new TouchAction(driver).tap(PointOption.point((int) (size.width * 0.99), (int) (size.height * 0.10))).perform();
        } catch (Exception e) {
            e.getCause();
        }
    }
    public static void swipeUpNew() {
        System.out.println("Moving Up");
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.30);
        int endY = (int) (size.height * 0.10);
        int startX = (int) (size.width * 0.55);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(startX, endY))
                .release().perform();
    }

    public static void swipeDown() {
        System.out.println("Moving Down");
        Dimension size = driver.manage().window().getSize();
        int endY = (int) (size.height * 0.70);
        int startY = (int) (size.height * 0.20);
        int startX = (int) (size.width * 0.90);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(startX, endY))
                .release().perform();
    }

    public static void swipeLeft() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.50);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(0, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(250, startY))
                .release().perform();
    }
    // Returns true if element is displayed false otherwise
    public static boolean swipeTillElementDisplayed(Direction dir, WebElement element, int noOfTimes) {
        System.out.println("Swiping till the element " + element.toString());
        while (noOfTimes > 0) {
            if (isElementDisplayed(element,5)) {
                System.out.println("Swiped till element " + element.toString());
                return true;
            }
            swipedirection(dir);
            noOfTimes -= 1;
        }
        return false;
    }

    public static boolean swipeTillElementDisplayed(Direction dir, WebElement element) {
        int noOfTimes = 5;
        System.out.println("Swiping till the element " + element.toString());
        while (noOfTimes > 0) {
            if (isElementDisplayed(element,5)) {
                System.out.println("Swiped till element " + element.toString());
                return true;
            }
            swipedirection(dir);
            noOfTimes -= 1;
        }
        return false;
    }

    // Returns true if element is displayed false otherwise
    public static boolean swipeTillElementDisplayed(Direction dir, By locator, int noOfTimes) {
        By element = locator;
        System.out.println("Swiping till the element " + element.toString());
        while (noOfTimes > 0) {
            if (isElementDisplayed(element,5)) {
                System.out.println("Swiped till element " + element.toString());
                return true;
            }
            swipedirection(dir);
            noOfTimes -= 1;
        }
        return false;
    }

    public static boolean swipeTillElementDisplayed(Direction dir, By locator) {
        int noOfTimes = 6;
        By element = locator;
        System.out.println("Swiping till the element " + element.toString());
        while (noOfTimes > 0) {
            if (isElementDisplayed(element,5)) {
                System.out.println("Swiped till element " + element.toString());
                return true;
            }
            swipedirection(dir);
            noOfTimes -= 1;
        }
        return false;
    }

    public static void swipeUp() {
        System.out.println("Moving Up");
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.60);
        int endY = (int) (size.height * 0.10);
        int startX = (int) (size.width * 0.90);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(startX, endY))
                .release().perform();
    }
    public static void swipedirection(Direction dir) {
        if (dir.toString().equalsIgnoreCase("up")) {
            swipeUp();
        } else if (dir.toString().equalsIgnoreCase("down")) {
            swipeDown();
        } else if (dir.toString().equalsIgnoreCase("right")) {
            swipeRight();
        } else if (dir.toString().equalsIgnoreCase("left")) {
            swipeLeft();
        } else if (dir.toString().equalsIgnoreCase("keypadDown")) {
            swipeDownkeyboardsreenDown();
        } else if (dir.toString().equalsIgnoreCase("keypad")) {
            swipeDownkeyboardsreen();
        } else if (dir.toString().equalsIgnoreCase("upnew")) {
            swipeUpNew();
        }
    }

    public static void swipeRight() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.50);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point((int) (size.width * 0.90), startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point((int) (size.width * 0.10), startY)).release().perform();
    }
    public static void swipeDownkeyboardsreenDown() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.30);
        int endY = (int) (size.height * 0.50);
        int startX = (int) (size.width * 0.90);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(startX, endY))
                .release().perform();
    }
    public static void swipeDownkeyboardsreen() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.30);
        int endY = (int) (size.height * 0.10);
        int startX = (int) (size.width * 0.90);
        new TouchAction((PerformsTouchActions) driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(startX, endY))
                .release().perform();
    }
    public static boolean isElementDisplayed(WebElement element, int timeout) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println(result + " :  isElementDisplayed status of element " + element.toString());
        } catch (Exception e) {
            logger.warn("Element not Displayed, Timed Out !!! %s " + element.toString());
        }
        return result;
    }
    public static boolean isElementDisplayed(By locator, int timeout) {
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            result = wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
            System.out.println();
            System.out.println(result + " :  isElementDisplayed status of locator " + locator.toString());
        } catch (Exception e) {
            logger.warn("Element not Displayed, Timed Out !!! %s " + locator.toString());
        }
        return result;
    }
    public static void setText(By locator, String text) {
        System.out.println();
        System.out.println("Entering value " + text + " in element " + locator.toString());
        try {
            driver.findElement(locator).click();
            driver.findElement(locator).sendKeys(text);
            hideKeypad();
        } catch (Exception e) {
            // passing with the warning
            logger.warn(e.getLocalizedMessage());
        }
    }

    public static void swipUpDropDown(By element) {
        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        List<WebElement> statesList = driver.findElements(element);
        touchAction.press(ElementOption.element(statesList.get(5))).moveTo(ElementOption.element(statesList.get(1))).release().perform();
    }
}
