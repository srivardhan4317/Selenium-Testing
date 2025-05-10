package common;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FrameWorkHelper {
    private static final Logger logger = LogManager.getLogger(FrameWorkHelper.class);

    public static String getRandomAlphabetic(int randomStrLength){
        logger.debug("");
        return RandomStringUtils.randomAlphabetic(randomStrLength);
    }

    public static String getRandomAlphanumeric(int randomStrLength){
        return RandomStringUtils.randomAlphanumeric(randomStrLength);
    }


    public static String getRandomNumber(int randomIntLength){
        return RandomStringUtils.randomNumeric(randomIntLength);
    }

    public static String getSystemDateAndTimeWithFormat(String format) {
        Date today = new Date();
        SimpleDateFormat orderDateFormat = new SimpleDateFormat(format);
        return orderDateFormat.format(today);
    }

    public static String getSystemDateAndTimeWithFormat(String format, int addOrSubDays) {
        Date today = new Date();
        SimpleDateFormat orderDateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, addOrSubDays);
        return orderDateFormat.format(c.getTime());
    }
}
