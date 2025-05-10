package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;


import static java.lang.String.format;

public class PropertiesHelper {
    private static final Logger logger = LogManager.getLogger(PropertiesHelper.class);
    private final Properties configProp = new Properties();
    private final Properties apiEndPoints = new Properties();
    private final Properties mobileDimension = new Properties();
    private final Properties mobiletestDataproperty = new Properties();
    private final Properties testDataProperty = new Properties();
    private final Properties curbsideProperty = new Properties();
    public String captureOnlyFAIL;
    public String captureScreenShot;

    private PropertiesHelper() {
        //Private constructor to restrict new instances
        logger.debug("Read all properties from file");

        try {

            FileInputStream configPropFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/config/Config.properties");
            if (configPropFis != null) {
                configProp.load(configPropFis);
            }
            logger.debug("properties file load Done.");
            getCaptureScreenProp();

            FileInputStream apiEndPointFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/object_repo/api/EndPoints.properties");
            if (apiEndPointFis != null) {
                apiEndPoints.load(apiEndPointFis);
            }

            FileInputStream mobileViewPropFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/config/MobileViewportConfig.properties");
            if (mobileViewPropFis != null) {
                mobileDimension.load(mobileViewPropFis);
            }
            FileInputStream mobileAppTestDataFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/TestData/TD-MobileApp.properties");
            if (mobileAppTestDataFis != null) {
                mobiletestDataproperty.load(mobileAppTestDataFis);
            }

            String testdataPropFileName = System.getProperty("testdatafilename");
            if (testdataPropFileName == null && ("yes".equalsIgnoreCase(getConfigPropProperty("isLocalENV")) || "true".equalsIgnoreCase(getConfigPropProperty("isLocalENV")))) {
                testdataPropFileName = getConfigPropProperty("testdatafilename");
            }
            logger.debug("testdatafilename::" + testdataPropFileName);
            if (testdataPropFileName != null && testdataPropFileName.length() > 1) {
                if (!testdataPropFileName.contains(".properties")) {
                    testdataPropFileName = testdataPropFileName + ".properties";
                }
                logger.debug("Final testdatafilename::" + testdataPropFileName);
                FileInputStream testDataPropFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/TestData/" + testdataPropFileName);
                if (testDataPropFis != null) {
                    testDataProperty.load(testDataPropFis);
                }
            }

            Constants.thread_highest = getWaitTime("THREAD_HIGHEST");
            Constants.thread_high = getWaitTime("THREAD_HIGH");
            Constants.thread_medium = getWaitTime("THREAD_MEDIUM");
            Constants.thread_low = getWaitTime("THREAD_LOW");

        } catch (IOException e) {
            logger.error("PropertiesHelper IOException:: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public Properties loadPropertyFile(String absolutepath) {
        Properties propFile = new Properties();
        try {
            FileInputStream propFileFis = new FileInputStream(absolutepath);
            propFile.load(propFileFis);

        } catch (Exception e) {
            logger.error("PropertiesHelper.loadPropertyFile Exception:: " + e.getMessage());
            e.printStackTrace();
        }
        return propFile;
    }

    //Bill Pugh Solution for singleton pattern
    private static class LazyHolder {
        private static final PropertiesHelper INSTANCE = new PropertiesHelper();
    }

    public static PropertiesHelper getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getMobileTestDataProperty(String key) {

        String propertyValue = mobiletestDataproperty.getProperty(key);
        if (propertyValue == null || propertyValue.isEmpty()) {
            propertyValue = testDataProperty.getProperty(key);
        }
        logger.debug(format("read the data for key %s as %s", key, propertyValue));
        return propertyValue;
    }

    public List<String> getMobileTestDataPropertyList(String key) {
        String propertyValue = mobiletestDataproperty.getProperty(key);
        if (propertyValue == null || propertyValue.isEmpty()) {
            propertyValue = testDataProperty.getProperty(key);
        }
        logger.debug(format("read the data for key %s as %s", key, propertyValue));
        return Arrays.asList(propertyValue.split(","));

    }

    public String getConfigPropProperty(String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getConfigPropAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKeyFromConfigProp(String key) {
        return configProp.containsKey(key);
    }

    public String getTestDataProperty(String key) {
        return testDataProperty.getProperty(key);
    }

    public void setTestDataProperty(String key, String value) throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources/TestData/TD-Curbside.properties";
        FileInputStream in = new FileInputStream(path);
        curbsideProperty.load(in);
        in.close();
        FileOutputStream output = new FileOutputStream(path);
        curbsideProperty.setProperty(key, value);
        curbsideProperty. store(output, null);
        output.close();
    }

    public void setTestDataPropertyWithFileName(String key, String value, String fileName) throws IOException {
        String path = null;
        if (fileName.equalsIgnoreCase("Uat7Properties")) {
            path = System.getProperty("user.dir") + "/src/test/resources/TestData/TD-UAT7.properties";
        } else if (fileName.equalsIgnoreCase("Curbside")) {
            path = System.getProperty("user.dir") + "/src/test/resources/TestData/TD-Curbside.properties";
        }
        FileInputStream in = new FileInputStream(path);
        curbsideProperty.load(in);
        in.close();
        FileOutputStream output = new FileOutputStream(path);
        curbsideProperty.setProperty(key, value);
        curbsideProperty.store(output, null);
        output.close();
    }

    public String getcurbsidepickupTestDataProperty(String key) throws IOException {
        FileInputStream testDataPropFis = getFileInputStrem(System.getProperty("user.dir") + "/src/test/resources/TestData/TD-Curbside.properties");
        if (testDataPropFis != null) {
            curbsideProperty.load(testDataPropFis);
        }

        return curbsideProperty.getProperty(key);

    }


    public Set<String> getTestDataAllPropertyNames() {
        return testDataProperty.stringPropertyNames();
    }

    public boolean containsKeyFromTestDataProp(String key) {
        return testDataProperty.containsKey(key);
    }

    public String getEndpointProProperty(String key) {
        return apiEndPoints.getProperty(key);
    }

    public void getCaptureScreenProp() {
        captureScreenShot = configProp.getProperty("CaptureScreenShot");
        captureOnlyFAIL = configProp.getProperty("CaptureOnlyFAIL");
    }

    public FileInputStream getFileInputStrem(String filePath) {
        FileInputStream fileInputStrem = null;
        try {
            fileInputStrem = new FileInputStream(filePath);
        } catch (Exception e) {
            logger.error("getFileInputStrem() exception msg::" + e.getMessage());
            logger.error("FILE NOT FOUND::" + filePath);
        }
        return fileInputStrem;
    }

    public String getMobileDimensionProperty(String key) {
        return mobileDimension.getProperty(key);
    }

    public Set<String> getMobileDimensionAllPropertyNames() {
        return mobileDimension.stringPropertyNames();
    }

    public boolean containsKeyFromMobileDimension(String key) {
        return mobileDimension.containsKey(key);
    }

    public int getWaitTime(String key) {
        int wait = 10;
        try {
            if (containsKeyFromConfigProp(key)) {
                String waitTimeStr = getConfigPropProperty(key);
                logger.debug(key + "::" + waitTimeStr);
                if (waitTimeStr != null && waitTimeStr.matches("-?\\d+")) {
                    wait = Integer.valueOf(waitTimeStr);
                }
            }

        } catch (Exception e) {
            logger.error("getThreadsleep error msg::" + e.getMessage());

        }
        return wait;
    }


}
