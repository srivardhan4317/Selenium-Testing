package common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.fail;


public class WebDriverHelper {

    private WebElement objElement;
    private Select objSelectDropdown;
    public WebDriverWait wait;

    public static RemoteWebDriver driver;
    public static final Logger logger = LogManager.getLogger(WebDriverHelper.class);
    public static int thread_low;
    //    public String captureScreenShot;
    public String captureOnlyFAIL;

    public static common.Constants constant = new common.Constants();

    public static int explicitWaitTime = 5;

    public static int pageLoadWaitTime;


    private boolean isStepPass = false;

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    public void SwitchToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    public void switchToFrame(WebElement element) {
        driver.switchTo().frame(element);
    }

    public void openAndSwitchToNewtab() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

//    public int getWaitTime(String key){
//        int wait =10;
//        try{
//            if()
//        }
//    }

    public WebElement waitForElement(WebElement element) {
        logger.info("Waiting for Element: " + element);
        if (driver != null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//            wait.until(ExpectedConditions.presenceOfElementLocated(getObjectLocator(element)));
        }
        return element;
    }

    public WebElement waitForElement(WebElement element, int timeOut) {
        logger.info("Waiting for Element: " + element);
        if (driver != null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//            wait.until(ExpectedConditions.presenceOfElementLocated(getObjectLocator(element)));
            scrollPageToWebElement(element);
        }
        return element;
    }

    public static boolean waitForPageload(RemoteWebDriver driver) {
        boolean pageLoadwaitFlag = false;
        try {
            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadWaitTime));
            wait.until(pageLoadCondition);
            pageLoadwaitFlag = true;
        } catch (Exception e) {
            logger.error("page Load wait exception message::" + e.getMessage());
        }
        logger.debug("Page Load wait time seconds:" + pageLoadWaitTime + " :: isPageLoaded:" + pageLoadwaitFlag);
        return pageLoadwaitFlag;
    }

    public boolean isClickable(WebElement element) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.debug("Element is clickable true");
            return true;
        } catch (Exception e) {
            logger.debug("Element is clickable false");
            return false;
        }
    }

    public String getTitle() {
        String screenTitle = driver.getTitle();
        logger.info("The Title is " + screenTitle);
        return screenTitle;

    }

    public String getCurrentpageUrl() {
        logger.info("Get the current page url");
        String pageUrl = driver.getCurrentUrl();
        return pageUrl;
    }

    protected String getText(WebElement element) {
        String actualtext = null;
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                actualtext = objElement.getText();
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("gettext exception message");
        }
        logger.info("the text associated with the WebElement is " + actualtext);
        return actualtext;
    }

    protected void setInputText(WebElement element, String text) {
        logger.info("Input the text box value : " + text);
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.clear();
                objElement.sendKeys(text);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception is setInputtext message::" + e.getMessage());
        }
    }

    protected void setInputTextWithEnterkey(WebElement element, String text) {
        logger.info("Input the text box value : " + text);
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.sendKeys(text + "\n");
                captureScreenShot(constant.PASS);

            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception is setInputTextWithEnterkey message::" + e.getMessage());
        }
    }

    public void scrollPageToWebElement(WebElement element) {
        logger.info("Scroll page to Element");
        try {
            if (element != null) {
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)", "");
                captureScreenShot(constant.PASS);

            } else {
                captureScreenShot(constant.FAIL);

            }

        } catch (Exception e) {
            logger.error("ScrollToElement exception message:: " + e.getMessage());
            captureScreenShot(constant.FAIL);
        }
    }

    public void scrollToBottom() {
        try {
            long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
                Thread.sleep(3000);

                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
                Thread.sleep(3000);
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void tabInputBox(WebElement element) {
        logger.info("Press the TAB");
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.sendKeys(Keys.TAB);
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in tabInputBox message::" + e.getMessage());
        }
    }

    protected void clearInputBox(WebElement element) {
        logger.info("Clear the Input box value");
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.clear();
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in clearInputBox message::" + e.getMessage());
        }
    }

    protected boolean clickOnButton(WebElement element) {
        boolean flag = false;
        try {
            logger.info("Click on the button");
            if (isClickable(element)) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(element).click().build().perform();
                } catch (Exception e) {
                    element.click();
                    Thread.sleep(1000);
                }
                flag = true;
            } else {
                logger.info("Element not clickable" + element);
            }
        } catch (Exception e) {
            logger.error("clickOnButton exception message::" + e.getMessage());
        }
        return flag;

    }

    protected void clickOnBButton(WebElement element, String elementDescription) {
        logger.info("Click on the Button");
        this.objElement = waitForElement(element);
        if (isClickable(element)) {
            objElement.click();
            captureScreenShot(constant.PASS);
        } else {
            captureScreenShot(constant.FAIL);
        }
    }

//    Public WebElement findElementByxpath(String xpath) {
//        WebElement element = null;
//        try {
//            if (xpath != null) {
//                element = getDriver().findElement(By.xpath(xpath));
//
//            }
//
//        } catch (Exception e) {
//            logger.error("getFindElementByxpath exception message ::" + e.getMessage());
//            e.printStackTrace();
//        }
//        return element;
//    }

    protected boolean clickOnRadioButton(WebElement element) {
        boolean flag = false;
        logger.info("Click on the radio button");
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.click();
                flag = true;
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in clickOnradioButton message::" + e.getMessage());
        }
        return flag;
    }

    protected boolean clickOnRadioButton(WebElement element, String elementDescription) {
        boolean flag = false;
        logger.info("Click on the radio button");
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.click();
                flag = true;
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in clickOnradioButton message::" + e.getMessage());
        }
        return flag;
    }

    public void clickOnLink(WebElement element) {
        logger.info("Click on the link");
        try {
            this.objElement = waitForElement(element);
            if (objElement != null) {
                objElement.click();
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in clickOnLink message ::" + e.getMessage());
        }
    }

    protected void clcikOnLink(WebElement element, String elementDescription) {
        logger.info("Click on the Link");
        this.objElement = waitForElement(element);
        objElement.click();
    }

    /*
    Select from dropdown  By Text,

     */

    protected void selectById(WebElement element, int index) {
        logger.info("The checkbox value to be selected is : " + index);
        try {
            this.objElement = waitForElement(element);
            this.objSelectDropdown = new Select(objElement);
            if (objElement.isEnabled()) {
                objSelectDropdown.selectByIndex(index);
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in select by Text message: " + e.getMessage());
        }
    }

    protected void selectBytext(WebElement element, String text) {
        logger.info("The checkbox value to be selected is : " + text);
        try {
            this.objElement = waitForElement(element);
            this.objSelectDropdown = new Select(objElement);
            if (objElement.isEnabled()) {
                objSelectDropdown.selectByVisibleText(text);
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in select by Text message: " + e.getMessage());
        }
    }

    protected void selectByvalue(WebElement element, String value) {
        logger.info("The checkbox value to be selected is : " + value);
        try {
            this.objElement = waitForElement(element);
            this.objSelectDropdown = new Select(objElement);
            if (objElement.isEnabled()) {
                objSelectDropdown.selectByValue(value);
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in select by value message: " + e.getMessage());
        }
    }

    protected boolean isEnabled(WebElement element) {
        logger.info("Check if Webelement is Enabled");
        boolean flag = false;
        try {
            this.objElement = waitForElement(element);
            flag = this.objElement.isEnabled();
        } catch (Exception e) {
            logger.error("Exception in isEnabled message::" + e.getMessage());
        }
        return flag;

    }

    protected boolean isEnabled(WebElement element, String elementDescription) {
        logger.info("Check if Webelement is Enabled");
        boolean flag = false;
        try {
            this.objElement = waitForElement(element);
            flag = this.objElement.isEnabled();
            if (flag) {
                captureScreenShot(constant.PASS);
            }
        } catch (Exception e) {
            logger.error("Exception in isEnabled message::" + e.getMessage());
        }
        return flag;

    }

    protected boolean moveHover(WebElement element) {
        boolean flag = false;
        try {
            logger.info("Hover on a Element");
            this.objElement = waitForElement(element);
            if (isClickable(element)) {
                Actions actions = new Actions(driver);
                actions.moveToElement(objElement).build().perform();
                flag = true;
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Hover on a element::" + e.getMessage());
        }
        return flag;

    }

    protected boolean verifyTextPresent(String expectedText) {
        boolean flag = false;
        logger.info("Check if text is present on the page");
        try {
            waitForPageload(getDriver());
            String pageText = driver.getPageSource();
            Assert.assertTrue(pageText.contains(expectedText));
            logger.info("Text/Message ->" + expectedText + "<- is found on the current page");
            flag = true;
            captureScreenShot(constant.PASS);
        } catch (java.lang.NullPointerException e) {
            logger.error("NullPointerException in display message::" + e.getMessage());
            flag = false;
            captureScreenShot(constant.FAIL);
        } catch (AssertionError e) {
            logger.error("Assertion Error in isDisplay message::" + e.getMessage());
            flag = false;
            throw new AssertionError("Text/Message ->" + expectedText + "<- is not found on the current page.");
        } catch (Exception e) {
            logger.error("Exception in isDisplay message::" + e.getMessage());
            flag = false;
            captureScreenShot(constant.FAIL);
        }
        return flag;

    }

    protected boolean isDisplayed(WebElement element) {
        logger.info("Check if the Webelement is displayed");
        boolean flag = false;
        try {
            this.objElement = waitForElement(element);
            flag = this.objElement.isDisplayed();
            if (flag) {
                captureScreenShot(constant.PASS);
            }
        }
    }

    public void captureScreenShot(String status) {
        logger.debug("Status:" + status);
        if (constant.PASS.equalsIgnoreCase(status)) {
            this.isStepPass = true;
        } else {
            this.isStepPass = false;
        }

        String captureScreenShot;
        if ("yes".equalsIgnoreCase(captureScreenShot)) {
            if ("no".equalsIgnoreCase(captureOnlyFAIL) && !isStepPass) {
                takeScreenshot();
            }
        }
    }

    public static void takeScreenshot() {
        try {
            Thread.sleep(constant.thread_low);
            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            System.out.println(System.getProperty("user.dir") + "/Reports/Screenshots" + Constants.screenShortTagNames + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png");
            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/Reports/Screenshots" + Constants.screenShortTagNames + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isStepPass() {
        return isStepPass;
    }

    public void close() {
        try {
            captureScreenShot(constant.PASS);
            if (driver != null) {
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
            logger.error(" close driver() inside Exception while execution::" + e.getMessage());
            captureScreenShot(constant.FAIL);
        }

    }

    protected void clicknSetInputtext(WebElement element, String text) {
        logger.info("Input the text box value :" + text);
        try {
            this.objElement = waitForElement(element);
            if (objElement.isEnabled()) {
                objElement.click();
                objElement.sendKeys(Keys.CONTROL + "a");
                objElement.sendKeys(Keys.DELETE);
                objElement.sendKeys(text);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in setInputText message::" + e.getMessage());
        }
    }

    protected boolean isDisplayed(WebElement element, int timeOut) {
        logger.info("Check if the Webelement is displayed");
        boolean flag = false;
        try {
            this.objElement = waitForElement(element, timeOut);
            flag = this.objElement.isDisplayed();
            if (flag) {
                captureScreenShot(constant.PASS);
            } else {
                captureScreenShot(constant.FAIL);
            }
        } catch (Exception e) {
            logger.error("Exception in isDisplay message::" + e.getMessage());
        }
        return flag;
    }

    protected int getDisplayedWebElementsCount(List<WebElement> elements) {
        int size = -1;
        try {
            waitForElement(elements.get(0));
            List<WebElement> element = elements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            return element.size();
        } catch (Exception e) {
            logger.error("Element visible exception message::" + e.getMessage());
        }
        return size;
    }

    protected boolean clickOnVisibleButton(List<WebElement> elements) {
        boolean flag = false;
        try {
            logger.info("Click on the button");
            waitForElement(elements.get(0));
            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
            clickOnButton(element);
            flag = true;
        } catch (Exception e) {
            logger.error("cliconButton exception message::" + e.getMessage());
        }
        return flag;
    }

    public void jsClick(WebElement element) {
        if (isClickable(element)) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
        } else {
            fail(element + " is not clickable");

        }
    }

    protected void setInputText(List<WebElement> elements, List<String> values) {
//        Assert.assertEquals("Elements and values size are not same",elements.size(), values.size());
        for (int i = 0; i < elements.size(); i++) {
            logger.info("Input the text box value :" + values);
            try {
                this.objElement = waitForElement(elements.get(i));
                if (objElement.isEnabled()) {
                    objElement.clear();
                    objElement.sendKeys(values.get(i));
                    captureScreenShot(constant.PASS);
                } else {
                    captureScreenShot(constant.FAIL);
                }
            } catch (Exception e) {
                logger.error("Exception in setInputtext message::" + e.getMessage());
            }
        }
    }

    protected boolean isElementDisplayed(List<WebElement> elements) {
        boolean flag = false;
        try {
            logger.info("verify element is present");
            waitForElement(elements.get(0));
            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
            flag = isDisplayed(element);
        } catch (Exception e) {
            logger.error("Element presence exception message::" + e.getMessage());
        }

        return flag;
    }

    public static Object getCSSAttributevalue(WebElement element, String cSSAttribute) {
        String attributeValue = null;
        try {
            attributeValue = element.getCssValue(cSSAttribute);
        } catch (Exception e) {
            logger.error("Fetch CSS Attribute exception message::" + e.getMessage());
        }
        logger.info("CSS Attribute::" + attributeValue);
        return attributeValue;
    }

    public String getAttribute(List<WebElement> elements, String attributeName) {
        try {
            logger.info("verify Element is Present");
            waitForElement(elements.get(0));
            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            logger.error("Element presence excpetion message::" + e.getMessage());
        }
        return attributeName;
    }

    public static boolean waitForJsQueryLoad(){
        boolean jQueryFlag = false;
        try{
            ExpectedCondition<Boolean>  pageloadCondition = new ExpectedCondition<Boolean>(){
                @Override
                public Boolean apply(WebDriver driver){
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active")==0);
                }
            };
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(pageLoadWaitTime));
            wait.until(pageloadCondition);
            logger.debug("jQuery is ready");
            jQueryFlag = true;
        } catch (Exception e) {
            logger.error("jQuery wait exception message::"+ e.getMessage());
        }
        logger.debug("JQuery wait time seconds:"+pageLoadWaitTime+" ::isPageLoaded:" + jQueryFlag);
        return jQueryFlag;
    }

    public static boolean waitUntilJSReady(){
        boolean pageLoadwaitFlag = false;
        try{
            ExpectedCondition<Boolean>  pageloadCondition = new ExpectedCondition<Boolean>(){
                @Override
                public Boolean apply(WebDriver driver){
                    return  ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
            };
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(pageLoadWaitTime));
            wait.until(pageloadCondition);
            logger.debug("jQuery is ready");
            pageLoadwaitFlag = true;
        } catch (Exception e) {
            logger.error("jQuery wait exception message::"+ e.getMessage());
        }
        logger.debug("Page load wait time seconds:"+pageLoadWaitTime+" ::isPageLoaded:" + pageLoadwaitFlag);
        return pageLoadwaitFlag;

    }

    public static void waitUntilJQueryready(){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        Boolean jQueryDefined  = (Boolean) jsExec.executeScript("return ty[eof jQuery != 'undefined'");
        if (jQueryDefined == true){
            waitForJsQueryLoad();
            waitUntilJSReady();
        }
        else{
            logger.debug("jQuery is not defined on this site!");
        }
    }

    public static void sleep(Integer seconds){
        long secondsLong =(long) seconds;
        try{
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String fetchCookieValue(String cookieName) {
        String cookieValue = "false";
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(cookieName)) {
                cookieValue = cookie.getValue();
            }
        }
        return cookieValue;
    }
    public void addCookies(String cookieName, String value){
        Cookie cookie = new Cookie(cookieName,value);
        driver.manage().addCookie(cookie);
        driver.navigate().refresh();
        waitUntilJSReady();
    }



}
