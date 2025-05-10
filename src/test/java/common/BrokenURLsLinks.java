//package common;
//
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.net.ssl.HttpsURLConnection;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//
//public class BrokenURLsLinks extends CommonActionHelper {
//    private static final Logger logger = LogManager.getLogger(BrokenURLsLinks.class);
//    private int readTimeout = 30;
//    private String urlXpath = "a";
//    private String imageXpath = "img";
//    private String homePage;
//    public HashMap<String, String> validLinkUrlMap = new HashMap<String, String>();
//    public HashMap<String, String> brokenLinkUrlMap = new HashMap<String, String>();
//    public static String errorText="";
//
//    public boolean getBrokenLinks(){
//        boolean flag= false;
//        try{
//            errorText="";
//            //initializeDriver();
//            //openBaseURL(url);
//            waitForPageLoad(getDriver());
//
//            logger.debug("homeURL:"+homeURL);
//            if(homeURL!=null && homeURL.contains(".academy.com")){
//                homePage = homeURL.substring(0,homeURL.indexOf(".academy.com")+12);
//                logger.debug("homePageURL:"+homePage);
//            }
//            flag = checkBrokenAllLinks();
//        }  catch (Exception e) {
//            logger.error("getBrokenLinks exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }finally{
//            quitDriver();
//            logger.debug("FINAL VALIDATION LINK URL TEST STATUS::"+flag);
//            logger.debug("Total Link Url Tested::"+(validLinkUrlMap.size()+brokenLinkUrlMap.size()));
//            logger.debug("===============VALID LINK URL's ========================== COUNT:"+validLinkUrlMap.size());
//            printResult(validLinkUrlMap);
//            logger.debug("===============BROKEN LINK URL's ========================== COUNT:"+brokenLinkUrlMap.size());
//            addBrokenUrlsToErrorTxt(brokenLinkUrlMap);
//            logger.debug(errorText);
//        }
//        return flag;
//    }
//
//    public boolean checkBrokenAllLinks(){
//        boolean flag= true;
//        List<WebElement> linkList = getElementLinksList(urlXpath);
//        String linkUrl;
//        int respCode;
//
//        try{
//            for(WebElement element : linkList){
//                linkUrl = element.getAttribute("href");
//                if(linkUrl == null || linkUrl.isEmpty()){
//                    logger.debug("URL is either not configured for anchor tag or it is empty");
//                    continue;
//                }
//
//				/*if(!linkUrl.startsWith(homePage)){
//					logger.debug("URL belongs to another domain, skipping it.");
//					continue;
//				}*/
//
//                respCode = testLinkUrl(linkUrl);
//                String errorTxt="";
//                if(respCode >= 400){
//                    flag= false;
//                    errorTxt = "is a broken link";
//                    brokenLinkUrlMap.put(linkUrl, errorTxt);
//
//                } else{
//                    errorTxt = "is a valid link";
//                    validLinkUrlMap.put(linkUrl, errorTxt);
//                }
//                logger.debug(linkUrl+" :: RespCode:: "+respCode +"  Status::"+errorTxt);
//            }
//
//        }  catch (Exception e) {
//            logger.error("checkAllLinks exception msg::"+e.getMessage());
//            //e.printStackTrace();
//        }
//        return flag;
//    }
//
//    public List<WebElement>  getElementLinksList(String xpath){
//        List<WebElement> linkList = new ArrayList<WebElement>();
//        try{
//            linkList = getDriver().findElements(By.tagName(xpath));
//        }catch (Exception e) {
//            logger.error("getElementLinksList  Exception MSG::"+e.getMessage());
//        }
//        return linkList;
//    }
//
//    public int testLinkUrl(String https_url ){
//        int statusCode = 404;
//        URL url;
//        try {
//
//            url = new URL(https_url);
//            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
//            con.setRequestProperty("Cookie","debug=aso");
//            con.setDoInput(true);
//            con.setUseCaches(false);
//            con.setInstanceFollowRedirects(true);
//            con.setReadTimeout(readTimeout * 1000);
//            con.setConnectTimeout(readTimeout * 1000);
//            //con.connect();
//            statusCode = con.getResponseCode();
//
//        } catch (MalformedURLException e) {
//            logger.error("testLinkUrl MalformedURL Exception MSG::"+e.getMessage());
//            //e.printStackTrace();
//        } catch (Exception e) {
//            logger.error("testLinkUrl Exception MSG::"+e.getMessage());
//            // e.printStackTrace();
//        }
//        return statusCode;
//    }
//
//    private void printResult(HashMap<String, String> map){
//        for (Map.Entry<String,String> entry : map.entrySet()) {
//            logger.debug("URL = " + entry.getKey() +
//                    ",  :=: " + entry.getValue());
//        }
//
//    }
//
//    private void addBrokenUrlsToErrorTxt(HashMap<String, String> map){
//        StringBuffer brokenUrls = new StringBuffer();
//        for (Map.Entry<String,String> entry : map.entrySet()) {
//            brokenUrls.append("URL = ").append( entry.getKey()).append(" :=: ").append(entry.getValue()).append("\n");
//        }
//        brokenUrls.append("Summary:- Total URL Count:").append((brokenLinkUrlMap.size()+validLinkUrlMap.size()))
//                .append(" Valid URL Count:").append(validLinkUrlMap.size()).append("  Broken URL Count:").append(brokenLinkUrlMap.size());
//        errorText = brokenUrls.toString();
//    }
//
//
//    public boolean getBrokenImageLinks(){
//        boolean flag= false;
//        try{
//            waitForPageLoad(getDriver());
//            flag = checkBrokenAllImageLinks();
//        }  catch (Exception e) {
//            logger.error("getBrokenLinks exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }finally{
//            quitDriver();
//            logger.debug("FINAL VALIDATION LINK URL TEST STATUS::"+flag);
//            logger.debug("Total Link Url Tested::"+(validLinkUrlMap.size()+brokenLinkUrlMap.size()));
//            logger.debug("===============VALID LINK URL's ========================== COUNT:"+validLinkUrlMap.size());
//            printResult(validLinkUrlMap);
//            logger.debug("===============BROKEN LINK URL's ========================== COUNT:"+brokenLinkUrlMap.size());
//            addBrokenUrlsToErrorTxt(brokenLinkUrlMap);
//            logger.debug(errorText);
//        }
//        return flag;
//    }
//
//    public boolean checkBrokenAllImageLinks(){
//        boolean flag= true;
//        List<WebElement> linkList = getElementLinksList(imageXpath);
//        String linkUrl;
//        int respCode;
//
//        try{
//            for(WebElement element : linkList){
//                linkUrl = element.getAttribute("src");
//                if(linkUrl == null || linkUrl.isEmpty()){
//                    logger.debug("URL is either not configured for anchor tag or it is empty");
//                    continue;
//                }
//                respCode = testLinkUrl(linkUrl);
//                String errorTxt="";
//                if(respCode >= 400){
//                    flag= false;
//                    errorTxt = "is a broken link";
//                    brokenLinkUrlMap.put(linkUrl, errorTxt);
//
//                } else{
//                    errorTxt = "is a valid link";
//                    validLinkUrlMap.put(linkUrl, errorTxt);
//                }
//                logger.debug(linkUrl+" :: RespCode:: "+respCode +"  Status::"+errorTxt);
//            }
//
//        }  catch (Exception e) {
//            logger.error("checkAllLinks exception msg::"+e.getMessage());
//            //e.printStackTrace();
//        }
//        return flag;
//    }
//}
//
//
