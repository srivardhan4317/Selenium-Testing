//package common;
//
//import java.io.File;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.Proxy;
//
//import net.lightbody.bmp.BrowserMobProxy;
//import net.lightbody.bmp.BrowserMobProxyServer;
//import net.lightbody.bmp.client.ClientUtil;
//import net.lightbody.bmp.proxy.CaptureType;
//
//public class BrowserProxyHelper {
//    private static final Logger logger = LogManager.getLogger(BrowserProxyHelper.class);
//
//    public BrowserMobProxy proxy=null;
//    private static Proxy selinumProxy;
//    private BrowserProxyHelper(){
//        try{
//            if(proxy==null){
//                initiateBrowserMobProxy();
//            }
//        }catch (Exception e) {
//            logger.error("proxy loader exception msg::"+e.getMessage());
//        }
//    }
//
//    private static class LazyHolder
//    {
//        private static final BrowserProxyHelper INSTANCE = new BrowserProxyHelper();
//    }
//
//    public static BrowserProxyHelper getInstance()
//    {
//        return LazyHolder.INSTANCE;
//    }
//
//    public void startBrowserMobProxy(){
//        try{
//            logger.debug("initiated Start BrowserMobProxy ............");
//            proxy = new BrowserMobProxyServer();
//            proxy.setTrustAllServers(true);
//            proxy.start(0);
//            selinumProxy= ClientUtil.createSeleniumProxy(proxy);
//            logger.debug("BrowserMobProxyServer has started..............");
//        }catch (Exception e) {
//            logger.error("initiateBrowserMobProxy Exception msg::"+e.getMessage());
//        }
//    }
//
//    public Proxy getSeleniumProxy(){
//        return selinumProxy;
//    }
//
//    public void setProxyCaptureTypes(){
//
//        if(proxy != null && proxy.isStarted()){
//            logger.debug("setProxyCaptureTypes inside......");
//            proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
//        }
//    }
//
//    public void setHarFileName(){
//        if(proxy != null && proxy.isStarted()){
//            logger.debug("setProxy Har file inside......");
//            proxy.newHar("www.academy.com");
//        }
//    }
//
//    public Proxy initiateBrowserMobProxy(){
//        startBrowserMobProxy();
//        setProxyCaptureTypes();
//        setHarFileName();
//        logger.debug("-------------------  Started Browser mob proxy server..........................");
//        return getSeleniumProxy();
//    }
//
//    public boolean generateHarFile(String fileName){
//        boolean harGenerated= false;
//        try {
//            logger.debug("generating har file.........");
//            File file = new File("HarFiles");
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            if(proxy != null && proxy.isStarted()){
//                File harFile = new File(file.getAbsolutePath()+"/"+fileName);
//                proxy.getHar().writeTo(harFile);
//                logger.debug(".HAR file generated successfully ::"+harFile.getAbsolutePath());
//                stopProxy();
//                harGenerated = true;
//            }
//
//        } catch (Exception ex){
//            logger.error(ex.getMessage()+" ::Could not find file " + fileName);
//        }
//        return harGenerated;
//    }
//
//    public void stopProxy(){
//        try{
//            if(proxy != null){
//                proxy.stop();
//                proxy = null;
//                logger.debug("Proxy Server STOPED.");
//            }
//        }catch (Exception e) {
//            logger.error("stopProxy msg::"+e.getMessage());
//        }
//    }
//}
