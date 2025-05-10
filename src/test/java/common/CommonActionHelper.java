//package common;
//
//import com.aventstack.extentreports.gherkin.model.Scenario;
//import io.appium.java_client.AppiumBy;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.SupportsContextSwitching;
//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.*;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import ru.yandex.qatools.ashot.AShot;
//import ru.yandex.qatools.ashot.Screenshot;
//import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//public class CommonActionHelper extends WebDriverHelper
//{
//
//    public static final Logger logger = LogManager.getLogger(CommonActionHelper.class);
//    private WebElement objElement;
//    private Select objSelectDropdown;
//    //private WebDriverWait wait;;
//    private AppiumDriver mobileDriver;
//
//    private static final int SWIPE_DURATION = 1500;
//    private static final int SWIPE_EDGE_OFFSET = 100;
//    private boolean isStepPass = false;
//
//    public  static Map<String, Map<String, String>> completeSheetData;
//
//    public CommonActionHelper()
//    {
//
//    }
//
//    public CommonActionHelper(AppiumDriver driver)
//    {
//        this.mobileDriver = driver;
//    }
//
//
//    public By getbjectLocator(WebElement imgAcademyLogo)
//    {
//        String type = "";
//        String value =  "";
//
//        By locator = null;
//        String elementStr = imgAcademyLogo.toString();
//        logger.debug("Element String:: "+elementStr);
//        if(elementStr != null && elementStr.contains("->")){
//            String sraary[] = elementStr.split("->");
//            elementStr = sraary[1];
//            int index=elementStr.indexOf(":");
//            type = elementStr.substring(0,index).trim();
//            value = elementStr.substring(index+1,elementStr.length()-1);
//        }else if(elementStr != null && elementStr.contains("Proxy element for: DefaultElementLocator '")){
//            int index = elementStr.indexOf("Locator '");
//            elementStr = elementStr.substring(index+9,elementStr.length());
//            int ss1= elementStr.indexOf(":", 1);
//            value = elementStr.substring(ss1+2, elementStr.length()-1);
//            elementStr = elementStr.substring(0, ss1);
//            type = elementStr.substring(elementStr.indexOf(".", 1)+1, elementStr.length());
//        }
//        logger.debug("Locator Type::"+type);
//        if(type !=null && !type.equalsIgnoreCase("xpath")){
//            value = "'"+value+"'";
//        }
//        logger.debug("Locator Value:: "+value);
//        LocatorTypeEnum ltEnum = LocatorTypeEnum.valueOf(type);
//        switch(ltEnum)
//        {
//            case id:
//                locator = By.id(value);
//                break;
//            case name:
//                locator = By.name(value);
//                break;
//            case cssSelector:
//                locator = By.cssSelector(value);
//                break;
//            case linkText:
//                locator = By.linkText(value);
//                break;
//            case partialLinkText:
//                locator = By.partialLinkText(value);
//                break;
//            case tagName:
//                locator = By.tagName(value);
//                break;
//            case xpath:
//                locator = By.xpath(value);
//                break;
//        }
//        //System.out.println(locator);
//        return locator;
//
//    }
//
//    /***
//     * To Handle I Frame
//     * @param element
//     * @param iFrameId
//     * @author KG
//     * @return
//     */
//
//    public WebElement waitForInnerFormElement(WebElement element, String iFrameId)
//    {
//        logger.info("Waiting for element : " + element);
//        driver.switchTo().frame(driver.findElement(By.id(iFrameId)));
//        if (driver != null)
//        {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//        } /*else if (mobileDriver != null) {
//                  wait = new WebDriverWait(mobileDriver,Integer.parseInt(webPropHelper.getConfigPropProperty("WEBDRIVER_WAIT")));
//           }*/
//        if(element != null)
//        {
//            //getbjectLocator(element);
//
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//            //wait.until(ExpectedConditions.visibilityOf(element));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(getbjectLocator(element)));
//            scrollPageToWebElement(element);
//        }
//
//
//        return element;
//    }
//
//    /***
//     * To Handle iFrame for default
//     * @param element
//     * @param iFrameId
//     * @author KG
//     * @return
//     */
//
//    public void SwitchToDefaultFrame()
//    {
//        //logger.info("Waiting for element : " + element);
//        driver.switchTo().defaultContent();
//    }
//
//    public void switchToFrame(WebElement element) {
//        driver.switchTo().frame(element);
//    }
//    public WebElement waitForElement(WebElement element)
//    {
//        logger.info("Waiting for element : " + element);
//        if (driver != null)
//        {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//        } /*else if (mobileDriver != null) {
//                  wait = new WebDriverWait(mobileDriver,Integer.parseInt(webPropHelper.getConfigPropProperty("WEBDRIVER_WAIT")));
//           }*/
//        if(element != null)
//        {
//            //getbjectLocator(element);
//
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//            //wait.until(ExpectedConditions.visibilityOf(element));
//            wait.until(ExpectedConditions.presenceOfElementLocated(getbjectLocator(element)));
//            scrollPageToWebElement(element);
//        }
//
//
//        return element;
//    }
//
//    protected boolean moveHover(WebElement element)
//    {
//        boolean flag = false;
//        try{
//            //Thread.sleep(2000);
//            logger.info("Hover on an element");
//            this.objElement = waitForElement(element);
//            if (isClickable(element))
//            {
//
//                Actions actions = new Actions(driver);
//                actions.moveToElement(objElement).build().perform();
//                //objElement.click();
//                //Thread.sleep(1000);
//                flag = true;
//                captureScreenShot(Constants.PASS);
//            } else {
//
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Hover on element::"+e.getMessage());
//            //e.printStackTrace();
//        }
//        return flag;
//    }
//
//
//    /**
//     * Waits for an element to be visible for a specified time(Webdriver wait)
//     *
//     * @param element
//     * @return
//     * @throws Exception
//     * @throws NoSuchElementException
//     */
//	/*public WebElement waitForElement(WebElement element) {
//		logger.info("Waiting for element : " + element);
//		if (driver != null) {
//			wait = new WebDriverWait(driver, explicitWaitTime);
//		} else if (mobileDriver != null) {
//			wait = new WebDriverWait(mobileDriver,Integer.parseInt(webPropHelper.getConfigPropProperty("WEBDRIVER_WAIT")));
//		}
//		if(element != null){
//			wait.until(ExpectedConditions.visibilityOf(element));
//		}
//		return element;
//	}
//	 */
//    public boolean isClickable(WebElement element)
//    {
//        try	{
//            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
//            wait.until(ExpectedConditions.elementToBeClickable(element));
//            logger.debug("Element is clickable true");
//            return true;
//        }catch (Exception e) {
//            logger.debug("Element is clickable false");
//            return false;
//        }
//    }
//	/*public static void waitForPageLoad() {
//	    WebDriverWait wait = new WebDriverWait(driver, 60);
//
//	    Predicate<RemoteWebDriver> pageLoaded = new Predicate<RemoteWebDriver>() {
//
//	        @Override
//	        public boolean apply(RemoteWebDriver input) {
//	            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//	        }
//
//	    };
//	    wait.until(pageLoaded);
//	}*/
//
//
//    public static boolean waitForPageLoad(RemoteWebDriver driver)
//    {
//        boolean pageLoadwaitFlag= false;
//        try
//        {
//            ExpectedCondition<Boolean> pageLoadCondition = new
//                    ExpectedCondition<Boolean>()
//                    {
//                        //	@Override
//                        public Boolean apply(WebDriver driver)
//                        {
//                            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//                        }
//                    };
//
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadWaitTime));
//            wait.until(pageLoadCondition);
//            pageLoadwaitFlag= true;
//        }catch (Exception e)
//        {
//            logger.error("Page Load Wait exception msg::"+e.getMessage());
//        }
//        logger.debug("Page load wait time seconds:"+pageLoadWaitTime+" :: isPageLoaded:"+pageLoadwaitFlag);
//        return pageLoadwaitFlag;
//    }
//
//    /**
//     * Get the title of a page
//     *
//     * @return title of the screen
//     * @throws Exception
//     */
//    public String getTitle() {
//        String screenTitle = driver.getTitle();
//        logger.info("The title is " + screenTitle);
//
//        return screenTitle;
//    }
//
//    /**
//     * Get the current URL of the application
//     *
//     * @return returns the url of the page
//     * @throws Exception
//     */
//    public String getCurrentPageURL() {
//        logger.info("Get the current page url");
//        String appURL = driver.getCurrentUrl();
//        return appURL;
//    }
//
//    /**
//     * Get the text of a label
//     *
//     * @param WebElement
//     *            as the paramter
//     * @return text associated with the WebElement
//     */
//    protected String getText(WebElement element) {
//        String actualText = null;
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement.isEnabled()){
//                actualText = objElement.getText();
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("gettext exception msg");
//        }
//        logger.info("The text associated with the WebElement is " + actualText);
//        return actualText;
//    }
//
//    protected String getTextUsingJS(WebElement element) {
//        String actualText = null;
//        try {
//            scrollToVisibleElement(element);
//            actualText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
//
//        } catch (Exception e) {
//            logger.error("gettext exception msg");
//        }
//        logger.info("The text associated with the WebElement is " + actualText);
//        return actualText;
//    }
//
//
//    /**
//     * Set the textfield value
//     *
//     * @param element
//     * @param text
//     * @throws Exception
//     */
//    protected void setInputText(WebElement element, String text) {
//        logger.info("Input the text box value : " + text);
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement.isEnabled())
//            {
//                objElement.clear();
//                objElement.sendKeys(text);
////				captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in setInputText msg::"+e.getMessage());
//        }
//
//    }
//
//    protected void setInputTextWithEnterKey(WebElement element, String text) {
//        logger.info("Input the text box value : " + text);
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement.isEnabled()){
//                objElement.sendKeys(text+"\n");
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in setInputTextWithEnterKey msg::"+e.getMessage());
//        }
//
//    }
//
//    public void scrollPageToWebElement(WebElement element)
//    {
//        logger.info("ScrollPage to Element");
//        try{
//            if(element != null)
//            {
//                Thread.sleep(500);
//                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
//                Thread.sleep(500);
//                ((JavascriptExecutor)driver).executeScript("window.scrollBy(0, -200)", "");
//
//                captureScreenShot(Constants.PASS);
//            }else {
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("ScrollToElement Exception Msg:: "+e.getMessage());
//            captureScreenShot(Constants.FAIL);
//        }
//    }
//
//    public void scrollToBottom() throws InterruptedException {
//        try {
//            long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
//
//            while (true) {
//                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
//                Thread.sleep(3000);
//
//                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
//                Thread.sleep(2000);
//                if (newHeight == lastHeight) {
//                    break;
//                }
//                lastHeight = newHeight;
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public WebElement runTimeXpath(String xvalue)
//    { 	WebElement TT = null;
//        By locator = null;
//
//        try
//        {
//            logger.info("runTimeXpath for element : " + xvalue);
//            if (driver != null)
//            {
//                wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//            }
//            if(xvalue != null)
//            {
//                String value = "//*[contains(text(), "+xvalue+")]";
//                locator = By.xpath(value);
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
//                //wait.until(ExpectedConditions.visibilityOf(element));
//                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//                TT = driver.findElement(By.xpath(value));
//                //waitForElement(TT);
//                //scrollPageToWebElement(TT);
//                //Thread.sleep(3000);
//            }
//            captureScreenShot(Constants.PASS);
//
//
//        }
//
//        catch (Exception e)
//        {
//            logger.error("clickOnButton exception msg::"+e.getMessage());
//            captureScreenShot(Constants.FAIL);
//        }
//        return TT;
//    }
//
//    /**
//     * Press the TAB button
//     *
//     * @param element
//     * @throws Exception
//     */
//    protected void tabInputBox(WebElement element) {
//        logger.info("Press the TAB");
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement.isEnabled()){
//                objElement.sendKeys(Keys.TAB);
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  tabInputBox msg::"+e.getMessage());
//        }
//    }
//
//    /**
//     * Clears the value from the TextBox
//     *
//     * @param element
//     * @throws Exception
//     */
//    protected void clearInputBox(WebElement element) {
//        logger.info("Clear the Input box value");
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement.isEnabled()){
//                objElement.clear();
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  clearInputBox msg::"+e.getMessage());
//        }
//        //checkForSnapshot();
//    }
//
//
//    /**
//     * Clicks on the Button
//     *
//     * @param element
//     * @throws Exception
//     */
//    protected boolean clickOnButton(WebElement element) {
//        boolean flag = false;
//        try {
//            logger.info("Click on the button");
//            if (isClickable(element)) {
//                try {
//                    Actions actions = new Actions(driver);
//                    actions.moveToElement(element).click().build().perform();
//                } catch (Exception e) {
//                    element.click();
//                    Thread.sleep(1000);
//                }
//                flag = true;
//            } else {
//                logger.info("Element not clickable" + element);
//            }
//        } catch (Exception e) {
//            logger.error("clickOnButton exception msg::" + e.getMessage());
//            //e.printStackTrace();
//        }
//        return flag;
//    }
//
//	/*public static void popupCheck() {
//
//        WebElement btnClosePopup = driver.findElement(By.xpath("//*[contains(@id,'evergage-tooltip')]//*[@title='Close Message']"));
//        if(btnClosePopup.isDisplayed())
//        {
//               btnClosePopup.click();
//        }else {
//               System.out.println("Pop up not displayed");
//        }
// }*/
//
//    protected void clickOnButton(WebElement element, String elementDescription) {
//        logger.info("Click on the button");
//        this.objElement = waitForElement(element);
//        if (isClickable(element)) {
//            objElement.click();
//
//            captureScreenShot(Constants.PASS);
//        } else {
//
//            captureScreenShot(Constants.FAIL);
//        }
//    }
//
//    public WebElement getfindElementByXPath(String xpath){
//        WebElement element = null;
//        try{
//
//            if(xpath != null){
//                element = getDriver().findElement(By.xpath(xpath));
//            }
//        }catch (Exception e) {
//            logger.error("getfindElementByXPath exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return element;
//    }
//    /**
//     * Clicks on the Radio Button
//     *
//     * @param element
//     * @throws Exception
//     */
//    protected boolean clickOnRadioButton(WebElement element) {
//        boolean flag = false;
//        logger.info("Click on the Radio button");
//        try{
//            this.objElement = waitForElement(element);
//            if (objElement.isEnabled()) {
//                objElement.click();
//                flag = true;
//                captureScreenShot(Constants.PASS);
//            } else {
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  ClickonRadioBtn msg::"+e.getMessage());
//        }
//        return flag;
//    }
//
//    protected void clickOnRadioButton(WebElement element,
//                                      String elementDescription) {
//        logger.info("Click on the Radio button");
//        this.objElement = waitForElement(element);
//        if (objElement.isEnabled()) {
//            objElement.click();
//            captureScreenShot(Constants.PASS);
//        } else {
//            captureScreenShot(Constants.FAIL);
//        }
//    }
//
//    /**
//     * Clicks on the Link
//     *
//     * @param element
//     * @throws Exception
//     */
//    public void clickOnLink(WebElement element) {
//        logger.info("Click on the Link");
//        try{
//            this.objElement = waitForElement(element);
//            if(objElement != null){
//                objElement.click();
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  setInputText msg::"+e.getMessage());
//        }
//    }
//
//    protected void clickOnLink(WebElement element, String elementDescription) {
//        logger.info("Click on the Link");
//        this.objElement = waitForElement(element);
//        objElement.click();
//
//    }
//
//    /**
//     * Selects an dropdown value by visible text
//     *
//     * @param element
//     * @param text
//     * @throws Exception
//     */
//    protected void selectByText(WebElement element, String text) {
//        logger.info("The Checkbox value to be selected is : " + text);
//        try{
//            this.objElement = waitForElement(element);
//            this.objSelectDropdown = new Select(objElement);
//            if (objElement.isEnabled()) {
//                objSelectDropdown.selectByVisibleText(text);
//                captureScreenShot(Constants.PASS);
//            } else {
//
//            }captureScreenShot(Constants.FAIL);
//        }catch (Exception e) {
//            logger.error("Exception in  setInputText msg::"+e.getMessage());
//        }
//    }
//
//    /**
//     * Selects an dropdown value by Value attribute of the entry
//     *
//     * @param element
//     * @param text
//     * @throws Exception
//     */
//    protected void selectByValue(WebElement element, String text) {
//        logger.info("The Checkbox value(by Value) to be selected is : " + text);
//        try{
//            this.objElement = waitForElement(element);
//            this.objSelectDropdown = new Select(objElement);
//            if (objElement.isEnabled()) {
//                objSelectDropdown.selectByValue(text);
//                captureScreenShot(Constants.PASS);
//            } else {
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  setInputText msg::"+e.getMessage());
//        }
//    }
//
//    /**
//     * Checks if the Webelement is enabled
//     *
//     * @param WebElement
//     * @throws Exception
//     */
//    protected boolean isEnabled(WebElement element) {
//        logger.info("Check if the Webelement is enabled");
//        boolean flag = false;
//        try{
//            this.objElement = waitForElement(element);
//            flag = this.objElement.isEnabled();
//
//        }catch (Exception e) {
//            logger.error("Exception in  isEnabled msg::"+e.getMessage());
//        }
//        return flag;
//    }
//
//    protected boolean isEnabled(WebElement element, String elementDescription) {
//        logger.info("Check if the Webelement is enabled");
//        boolean flag;
//        this.objElement = waitForElement(element);
//        flag = this.objElement.isEnabled();
//        if (flag) {
//            captureScreenShot(Constants.PASS);
//        }
//        return flag;
//    }
//
//    /**
//     * Checks if the Webelement is displayed
//     *
//     * @param WebElement
//     * @throws Exception
//     */
//
//    protected boolean VerifyTextPersent(String expectedText)
//    {
//        boolean flag = false;
//        logger.info("Check if the Text is persent on the page");
//        try
//        {
//            waitForPageLoad(getDriver());
//            String pageText = driver.getPageSource();
//            Assert.assertTrue(pageText.contains(expectedText));
//            //System.out.println(pageText);
//            logger.info("Text/Message -> " + expectedText + " <- is found on the current page.");
//            flag = true;
//            //captureScreenShot(Constants.PASS);
//        }
//        catch (java.lang.NullPointerException e)
//        {
//            logger.error("NullPointerException in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//        }
//        catch(AssertionError e)
//        {
//            logger.error("AssertionError in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//            throw new AssertionError("Text/Message -> " + expectedText + " <- is not found on the current page.");
//        }
//        catch(Exception e)
//        {
//            logger.error("Exception in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//        }
//        return flag;
//
//    }
//
//    protected boolean VerifyTextNotPersent(String expectedText)
//    {
//        boolean flag = false;
//        logger.info("Check if the Text is persent on the page");
//        try
//        {
//            waitForPageLoad(getDriver());
//            String pageText = driver.getPageSource();
//            Assert.assertFalse(pageText.contains(expectedText));
//
//            logger.info("Text/Message -> " + expectedText + " <- is not found on the current page.");
//            flag = true;
//            captureScreenShot(Constants.PASS);
//        }
//        catch (java.lang.NullPointerException e)
//        {
//            logger.error("NullPointerException in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//        }
//        catch(AssertionError e)
//        {
//            logger.error("AssertionError in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//            throw new AssertionError("Text/Message -> " + expectedText + " <- is found on the current page.");
//        }
//        catch(Exception e)
//        {
//            logger.error("Exception in  isDisplay msg::"+e.getMessage());
//            flag = false;
//            captureScreenShot(Constants.FAIL);
//        }
//        return flag;
//
//    }
//
//
//    protected boolean isDisplayed(WebElement element) {
//        logger.info("Check if the Webelement is displayed");
//        boolean flag = false;
//        try{
//            this.objElement = waitForElement(element);
//            flag = this.objElement.isDisplayed();
//            if (flag) {
//                captureScreenShot(Constants.PASS);
//            }else{
//                captureScreenShot(Constants.FAIL);
//            }
//        }catch (Exception e) {
//            logger.error("Exception in  isDisplay msg::"+e.getMessage());
//        }
//        return flag;
//    }
//
//    protected boolean isNotDisplayed(WebElement element) {
//        logger.info("Check if the Webelement is not displayed");
//        try{
//            Thread.sleep(8000);
//            if (element.isDisplayed()) {
//                return false;
//            }else{
//                return true;
//            }
//        }catch (Exception e) {
//            //logger.error("Exception in  isDisplay msg::"+e.getMessage());
//            return true;
//        }
//    }
//
//    protected boolean isDisplayed(WebElement element, String elementDescription)
//    {
//        logger.info("Check if the Webelement is displayed");
//        boolean flag;
//        this.objElement = waitForElement(element);
//        flag = this.objElement.isDisplayed();
//        if (flag) {
//            captureScreenShot(Constants.PASS);
//        }
//        return flag;
//    }
//
//    /**
//     * Checks if the Webelement is selected
//     *
//     * @param WebElement
//     * @throws Exception
//     */
//    protected boolean isSelected(WebElement element) {
//        logger.info("Check if the Webelement is selected");
//        boolean flag = false;
//        try{
//            this.objElement = waitForElement(element);
//            flag = this.objElement.isSelected();
//
//        }catch (Exception e) {
//            logger.error("Exception in  isSelected msg::"+e.getMessage());
//        }
//        return flag;
//    }
//
//    protected boolean isSelected(WebElement element, String elementDescription) {
//        logger.info("Check if the Webelement is selected");
//        boolean flag;
//        this.objElement = waitForElement(element);
//        flag = this.objElement.isSelected();
//        if (flag) {
//            captureScreenShot(Constants.PASS);
//        }
//        return flag;
//    }
//
//    /**
//     * Convenience method for swiping across the screen. Specified by:
//     * swipe(...) in TouchShortcuts Parameters: startx starting x coordinate.
//     * starty starting y coordinate. endx ending x coordinate. endy ending y
//     * coordinate. duration amount of time in milliseconds for the entire swipe
//     * action to take
//     */
//    protected void swipe(int startX, int startY, int endX, int endY,
//                         int swipeDuration) {
//        //mobileDriver.swipe(startX, startY, endX, endY, swipeDuration);
//
//    }
//
//    /**
//     * method for tapping a position on the screen.
//     *
//     * Specified by: tap(...) in TouchShortcuts Parameters: fingers number of
//     * fingers/appendages to tap with. x x coordinate. y y coordinate. duration
//     * how long between pressing down, and lifting fingers/appendages.
//     */
//    protected void preciseTap(int xPosition, int yPosition, int duration) {
//        //int numOfFingers = 1;
//        //mobileDriver.tap(numOfFingers, xPosition, yPosition, duration);
//
//    }
//
//    /**
//     * Hides the keyboard if it is showing. On iOS, there are multiple
//     * strategies for hiding the keyboard. Defaults to the "tapOutside" strategy
//     * (taps outside the keyboard). Switch to using
//     * hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done") if this doesn't
//     * work.
//     *
//     * Specified by: hideKeyboard() in DeviceActionShortcuts
//     */
//    protected void hideKeyBorad() {
//        try{
//            //mobileDriver.hideKeyboard();
//        }catch (Exception e) {
//            logger.error("Exception in  hideKeyboard msg::"+e.getMessage());
//        }
//
//    }
//
//    /**
//     * method to navigate to back
//     *
//     */
//    protected void clickBackButton() {
//        if(mobileDriver!=null){
//            mobileDriver.navigate().back();
//        }
//        if(driver!=null){
//            driver.navigate().back();
//        }
//    }
//
//    /**
//     * method to set the context to required view.
//     *
//     * Views are NATIVE_APP , WEBVIEW_1
//     *
//     * @param context
//     *            view to be set
//     * @throws InterruptedException
//     */
//    public void setContext(String context) throws InterruptedException {
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            logger.error(e);
//            throw e;
//        }
//        //Set<String> contextNames = mobileDriver.getContextHandles();
//        Set<String> contextNames = ((SupportsContextSwitching)mobileDriver).getWindowHandles();
//        logger.info("Context Names : " + contextNames);
//        if (context.contains("NATIVE")) {
//            ((SupportsContextSwitching)mobileDriver).context((String) contextNames.toArray()[0]);
//        } else if (context.contains("WEBVIEW")) {
//            ((SupportsContextSwitching)mobileDriver).context((String) contextNames.toArray()[1]);
//        }
//        logger.info("Current context" + ((SupportsContextSwitching)mobileDriver).getContext());
//    }
//
//    /**
//     * clears text form input field
//     *
//     * @param webElement
//     */
//    protected void clearText(WebElement webElement) {
//        try{
//            webElement.clear();
//        }catch (Exception e) {
//            logger.error("Exception in  clearText msg::"+e.getMessage());
//        }
//    }
//
//    /**
//     * method to find element
//     *
//     * @param by
//     * @return true if element present
//     * @return false if element not present
//     */
//    public boolean isElementPresent(By by) {
//        try {
//            if (mobileDriver != null) {
//                mobileDriver.findElement(by);
//            }
//            if (driver != null) {
//                driver.findElement(by);
//            }
//            return true;
//        } catch (NoSuchElementException e) {
//            logger.error(e);
//            return false;
//        }
//    }
//
//    /**
//     * method to swipe right
//     */
//    public void swipeRight() {
//        Dimension size = driver.manage().window().getSize();
//        int startx = (int) (size.width * 0.9);
//        int endx = (int) (size.width * 0.20);
//        int starty = size.height / 2;
//        swipe(startx, starty, endx, starty, 5000);
//    }
//
//    /**
//     * method to swipe left
//     */
//    public void swipeLeft() {
//        Dimension size = driver.manage().window().getSize();
//        int startx = (int) (size.width * 0.8);
//        int endx = (int) (size.width * 0.20);
//        int starty = size.height / 2;
//        swipe(startx, starty, endx, starty, 1000);
//    }
//
//    /**
//     * method to swipe left for specified mobileElement
//     *
//     * @param mobileElement
//     */
//    public void swipeLeft(WebElement mobileElement) {
//        Point currentLocation = mobileElement.getLocation();
//        Dimension elementSize = mobileElement.getSize();
//        int x = currentLocation.getX() + elementSize.getWidth() - 1;
//        int y = currentLocation.getY();
//        int endx = currentLocation.getX();
//        swipe(x, y, endx, y, 1000);
//    }
//
//    /**
//     * method to swipe right for specified mobileElement
//     *
//     * @param mobileElement
//     */
//    public void swipeRight(WebElement mobileElement) {
//        Point currentLocation = mobileElement.getLocation();
//        Dimension elementSize = mobileElement.getSize();
//        int x = currentLocation.getX();
//        int y = currentLocation.getY();
//        int endx = x + elementSize.getWidth() - 1;
//        swipe(x, y, endx, y, 1000);
//    }
//
//    /**
//     * method to swipeUp in a mobile page
//     */
//    public void swipeUp() {
//        Dimension dimensions = mobileDriver.manage().window().getSize();
//        int startY = (int) (dimensions.getHeight() * 0.5);
//        int endY = (int) (dimensions.getHeight() * 0.2);
//        swipe(0, startY, 0, endY, 1000);
//    }
//
//    /**
//     * method to scroll to visible element in a mobile page
//     *
//     * @param by
//     *            is to identify a element example By.id or By.xpath..etc
//     * @param MAX_SCROLL_COUNT
//     *            is a count to scroll in mobile page
//     * @throws Exception
//     */
//    public void scrollToVisibleElementInPage(By by, int maxscrollcount) throws Exception {
//        Dimension dimension = mobileDriver.manage().window().getSize();
//        int height = dimension.getHeight() - SWIPE_EDGE_OFFSET;
//        int startx = dimension.getWidth() / 2;
//        boolean found = false;
//        for (int i = 0; i < maxscrollcount; i++) {
//            if (isElementPresent(by)) {
//                found = true;
//                break;
//            }
//            swipe(startx, height, startx, 0, SWIPE_DURATION);
//        }
//        if (!found && !isElementPresent(by)) {
//            // giving up scrolling for element to be displayed after
//            // MAX_SCROLL_COUNT reached.
//            //throw new ModException("element was not visible after scrolling");
//        }
//    }
//
//    /**
//     * method scroll to visible text in a list and it will click on that element
//     *
//     * @param elementName
//     */
//    public void androidScrollToVisibleTextInListAndClick(String elementName) {
//        AndroidDriver ad = (AndroidDriver) mobileDriver;
//        WebElement element = ad.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()" + ".className(\"android.widget.ListView\")).scrollIntoView("+ "new UiSelector().text(\"" + elementName + "\"));"));
//        //+".resourceId(\"android:id/list\")).scrollIntoView("
//        element.click();
//    }
//
//
//    public static void scrollToVisibleElement(WebElement locator) {
//        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", locator);
//    }
//    //=========================================================================
//
//    public static boolean embedScreenshot(Scenario scenario, WebElement... locator) {
//        boolean flag = false;
//        //confition
//        if (locator.length > 0) {
//            scrollToVisibleElement(locator[0]);
//            try {
//                BufferedImage image = new AShot().takeScreenshot(driver).getImage();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                try {
//                    ImageIO.write(image, "png", baos);
//                    baos.flush();
//                    byte[] imageInByte = baos.toByteArray();
//                    baos.close();
//                    scenario.attach("Current Page URL is " + driver.getCurrentUrl(),"text/plain","");
//                    //MJR 08/20/19
//                    String s = driver.manage().getCookies().toString();
//                    //Updated for OMS automation
//                    if (s.contains("www.academy.com"))
//                        scenario.attach(s.substring(s.indexOf("correlationId"), s.indexOf("correlationId") + 50),"text/plain","");
//                    scenario.attach(imageInByte, "image/png","");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                flag = true;
//            } catch (Exception wde) {
//                logger.error("embedScreenshot() inside WebDriverException while execution::" + wde.getMessage());
//            }
//        } else {
//            try {
//                Object output = ((JavascriptExecutor) driver).executeScript("return window.devicePixelRatio");
//                String value = String.valueOf(output);
//                float windowDPR = Float.parseFloat(value);
//                BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(windowDPR), 1000)).takeScreenshot(driver).getImage();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(image, "png", baos);
//                baos.flush();
//                byte[] imageInByte = baos.toByteArray();
//                baos.close();
//                scenario.attach("Current Page URL is " + driver.getCurrentUrl(), "text/plain", "");
//                //MJR 08/20/19
//                String s = driver.manage().getCookies().toString();
//                //Updated for OMS automation
//                if (s.contains("www.academy.com"))
//                    scenario.attach(s.substring(s.indexOf("correlationId"), s.indexOf("correlationId") + 50), "text/plain", "");
//                scenario.attach(imageInByte, "image/png", "");
//                //scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
//                flag = true;
//            } catch (Exception wde) {
//                logger.error("embedScreenshot() inside WebDriverException while execution::" + wde.getMessage());
//            }
//        }
//        logger.debug("EmbedScreenshot flag::" + flag);
//        return flag;
//    }
//
//
///*	public static boolean embedScreenshot(Scenario scenario, WebElement locator) {
//		boolean flag = false;
//		//confition
//		if (locator.isDisplayed()) {
//			scrollToVisibleElement(locator);
//			try {
//				BufferedImage image = new AShot().takeScreenshot(driver).getImage();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				try {
//					ImageIO.write(image, "png", baos);
//					baos.flush();
//					byte[] imageInByte = baos.toByteArray();
//					baos.close();
//					scenario.attach("Current Page URL is " + driver.getCurrentUrl(),"text/plain","");
//					//MJR 08/20/19
//					String s = driver.manage().getCookies().toString();
//					//Updated for OMS automation
//					if (s.contains("www.academy.com"))
//						scenario.attach(s.substring(s.indexOf("correlationId"), s.indexOf("correlationId") + 50),"text/plain","");
//					scenario.attach(imageInByte, "image/png","");
//				} catch (IOException e) {

//					e.printStackTrace();
//				}
//			} catch (Exception wde) {
//				logger.error("embedScreenshot() inside WebDriverException while execution::" + wde.getMessage());
//			}
//		}
//		try {
//			Object output = ((JavascriptExecutor) driver).executeScript("return window.devicePixelRatio");
//			String value = String.valueOf(output);
//			float windowDPR = Float.parseFloat(value);
//			BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(windowDPR), 1000)).takeScreenshot(driver).getImage();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(image, "png", baos);
//			baos.flush();
//			byte[] imageInByte = baos.toByteArray();
//			baos.close();
//			scenario.attach("Current Page URL is " + driver.getCurrentUrl(),"text/plain","");
//			//MJR 08/20/19
//			String s = driver.manage().getCookies().toString();
//			//Updated for OMS automation
//			if (s.contains("www.academy.com"))
//				scenario.attach(s.substring(s.indexOf("correlationId"), s.indexOf("correlationId") + 50),"text/plain","");
//			scenario.attach(imageInByte, "image/png","");
//			//scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
//			flag = true;
//		} catch (Exception wde) {
//			logger.error("embedScreenshot() inside WebDriverException while execution::" + wde.getMessage());
//		}
//		logger.debug("EmbedScreenshot flag::" + flag);
//		return flag;
//	}*/
//
//    public static void writeToRep(Scenario scenario, String data) {
//        scenario.attach(data,"text/plain","");
//    }
//
//
//    public void captureScreenShot(String status) {
//        logger.debug("Status::" + status);
//
//        if (Constants.PASS.equalsIgnoreCase(status)) {
//            this.isStepPass = true;
//        } else {
//            this.isStepPass = false;
//        }
//
//        if ("yes".equalsIgnoreCase(webPropHelper.captureScreenShot)) {
//            // takeScreenshot();
//            if ("no".equalsIgnoreCase(webPropHelper.captureOnlyFAIL) && !isStepPass) {
//                //(Constants.FAIL.equalsIgnoreCase(status) || Constants.PARTIALLYPASS.equalsIgnoreCase(status))){
//                takeScreenshot();
//
//            }/*else if("no".equalsIgnoreCase(webPropHelper.captureOnlyFAIL)){
//				takeScreenshot();
//			}*/
//        }
//    }
//
//    public static void takeScreenshot() {
//        //Updated by MJR 23/7/19
//        try {
//            Thread.sleep(Constants.thread_low);
//            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
//            System.out.println(System.getProperty("user.dir") + "/Report/Screenshots/" + Constants.screenShortTagNames + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png");
//            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/Report/Screenshots/" + Constants.screenShortTagNames + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png"));
////			ImageIO.write(fpScreenshot.getImage(),"PNG", new File("Report/Screenshots/"+Constants.screenShortTagNames+"_"+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+".png"));
////			ImageIO.write(fpScreenshot.getImage(), "PNG", new File(System.getProperty("user. dir") + "/Report/Screenshots/" + Constants.screenShortTagNames + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + ".png"));
//            //File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            //FileUtils.copyFile(src, new File("Report/Screenshots/"+Constants.screenShortTagNames+"_"+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+".png"));
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isStepPass() {
//        return isStepPass;
//    }
//
//    public void setStepPass(boolean isStepPass) {
//        this.isStepPass = isStepPass;
//    }
//
//    public void close() {
//        try {
//            captureScreenShot(Constants.PASS);
//            if (driver != null) {
//                driver.close();
//                driver.quit();
//            }
//        } catch (Exception e) {
//            logger.error("close driver() inside Exception while execution::" + e.getMessage());
//            captureScreenShot(Constants.FAIL);
//        }
//    }
//
//    protected void clicknSetInputText(WebElement element, String text) {
//        logger.info("Input the text box value : " + text);
//        try {
//            this.objElement = waitForElement(element);
//            if (objElement.isEnabled()) {
//                objElement.click();
//                objElement.sendKeys(Keys.CONTROL+"a");
//                objElement.sendKeys(Keys.DELETE);
//                objElement.sendKeys(text);
////				captureScreenShot(Constants.PASS);
//            } else {
//                captureScreenShot(Constants.FAIL);
//            }
//        } catch (Exception e) {
//            logger.error("Exception in setInputText msg::" + e.getMessage());
//        }
//    }
//
//
//    protected boolean isDisplayed(WebElement element, int timeOut) {
//        logger.info("Check if the Webelement is displayed");
//        boolean flag = false;
//        try {
//            this.objElement = waitForElement(element, timeOut);
//            flag = this.objElement.isDisplayed();
//            if (flag) {
//                captureScreenShot(Constants.PASS);
//            } else {
//                captureScreenShot(Constants.FAIL);
//            }
//        } catch (Exception e) {
//            logger.error("Exception in  isDisplay msg::" + e.getMessage());
//        }
//        return flag;
//    }
//
//
//    public WebElement waitForElement(WebElement element, int timeOut) {
//        logger.info("Waiting for element : " + element);
//        if (driver != null) {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//            wait.until(ExpectedConditions.presenceOfElementLocated(getbjectLocator(element)));
//            scrollPageToWebElement(element);
//        }
//        return element;
//    }
//
//    protected int getDisplayedWebElementsCount(List<WebElement> elements) {
//        int size = -1;
//        try {
//            waitForElement(elements.get(0));
//            List<WebElement> element = elements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
//            return element.size();
//        } catch (Exception e) {
//            logger.error("Element visible exception msg::" + e.getMessage());
//        }
//        return size;
//    }
//
//    protected boolean clickOnVisibleButton(List<WebElement> elements) {
//        boolean flag = false;
//        try {
//            logger.info("Click on the button");
//            waitForElement(elements.get(0));
//            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
//            clickOnButton(element);
//            flag = true;
//        } catch (Exception e) {
//            logger.error("clickOnButton exception msg::" + e.getMessage());
//        }
//        return flag;
//    }
//
//    protected Set<String> getWebElementsText(List<WebElement> elements) {
//        Set<String> elementsText = new HashSet<String>();
//        try {
//            waitForElement(elements.get(0));
//            for (WebElement ele : elements) {
//                if (ele.isDisplayed()) {
//                    elementsText.add(ele.getText());
//                }
//            }
//        } catch (Exception e) {
//            logger.error("clickOnButton exception msg::" + e.getMessage());
//        }
//        return elementsText;
//    }
//
//    public void jsClick(WebElement element) {
//        if (isClickable(element)) {
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].scrollIntoView(true);", element);
//            js.executeScript("arguments[0].click();", element);
//
//        } else {
//            fail(element + " is not clickable");
//        }
//    }
//
//    protected void setInputText(List<WebElement> element, List<String> values) {
//        Assert.assertEquals("Elements and values size are not same", element.size(), values.size());
//        for (int i = 0; i < element.size(); i++) {
//            logger.info("Input the text box value : " + values);
//            try {
//                this.objElement = waitForElement(element.get(i));
//                if (objElement.isEnabled()) {
//                    objElement.clear();
//                    objElement.sendKeys(values.get(i));
//                    captureScreenShot(Constants.PASS);
//                } else {
//                    captureScreenShot(Constants.FAIL);
//                }
//            } catch (Exception e) {
//                logger.error("Exception in setInputText msg::" + e.getMessage());
//            }
//        }
//    }
//
//    protected boolean isElementDisplayed(List<WebElement> elements) {
//        boolean flag = false;
//        try {
//            logger.info("Verify Element Present");
//            waitForElement(elements.get(0));
//            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
//            flag = isDisplayed(element);
//        } catch (Exception e) {
//            logger.error("element presence exception msg::" + e.getMessage());
//        }
//        return flag;
//    }
//
//    public String getCSSAttributeValue(WebElement element, String cSSAttribute) {
//        String attributeValue = null;
//        try {
//            attributeValue = element.getCssValue(cSSAttribute);
//        } catch (Exception e) {
//            logger.error("fetch CSS Attribute exception msg::" + e.getMessage());
//        }
//        logger.info("CSS Attribute::" + attributeValue);
//        return attributeValue;
//    }
//
//    public String fetchCookieValue(String cookieName) {
//        String cookieValue = "false";
//        Set<Cookie> cookies = driver.manage().getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().contains(cookieName)) {
//                cookieValue = cookie.getValue();
//            }
//        }
//        return cookieValue;
//    }
//
//    public String getAttribute(List<WebElement> elements, String attributeName) {
//        try {
//            logger.info("Verify Element Present");
//            waitForElement(elements.get(0));
//            WebElement element = elements.stream().filter(WebElement::isDisplayed).findFirst().get();
//            return element.getAttribute(attributeName);
//        } catch (Exception e) {
//            logger.error("element presence exception msg::" + e.getMessage());
//        } return "";
//    }
//}
//
//
//
