package common;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CommonGenericUtils {

    private static final Logger logger = LogManager.getLogger(CommonGenericUtils.class);
    public static String screenShortTagNamesaa;

    public String generateRandomEmailId() {

        String randomGeneratedString = RandomStringUtils.randomAlphanumeric(10);
        String head = "test";
        String tail = "@deleteme.com";
        String newGeneratedEmail = head + randomGeneratedString + tail;
        logger.debug("The new random generated email address is " + newGeneratedEmail);
        return newGeneratedEmail;

    }

    /***
     * @Description - Using this you can set value to an attribute of an element on the UI using JS
     * @param driver
     * @param elem
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttributeValue(WebDriver driver, WebElement elem, String attributeName, String attributeValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", elem, attributeName, attributeValue);
    }
}

