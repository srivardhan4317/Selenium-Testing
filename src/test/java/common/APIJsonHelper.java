//package common;
//
//
//import io.restassured.http.ContentType;
//import io.restassured.http.Cookies;
//import io.restassured.http.Headers;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import io.restassured.response.ResponseBody;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.URL;
//import java.net.URLConnection;
//
//import static io.restassured.RestAssured.given;
//
//public class APIJsonHelper {
//    private static final Logger logger = LogManager.getLogger(APIJsonHelper.class);
//    public static Response response;
//    public static PropertiesHelper loadProps = PropertiesHelper.getInstance();
//    private String errorTxt;
//
//
//    public static io.restassured.http.Cookies httpCookies;
//
//
//    public Response initiateRestAPICall(String url){
//
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().when().get(url);
//            logger.debug("JSON Response::"+response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//
//    public Response initiateRestAPICallWithCookie(String url){
//        //"http://35.224.100.42/api/category?categoryIds=15750"
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").get(url);
//            logger.debug("Response:::"+ response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestDeleteAPICall(String url){
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().when().cookies(httpCookies).contentType("application/json").delete(url);
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestAPICallWithCookies(String url,List<io.restassured.http.Cookie> restAssuredCookies){
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().contentType(ContentType.JSON).cookies(new Cookies(restAssuredCookies)).get(url);
//            logger.debug("JSON Response::"+response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestAPICallWithCookies exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithCookies(String url,List<io.restassured.http.Cookie> restAssuredCookies,String jsonRequestFilePath){
//        try{
//            url = urlAddDebug(url);
//            String postRequestStr = JSONValidationUtils.convertJsonFileToString(JsonReaderCommon.jsonRequestFolderPath+ jsonRequestFilePath+".json");
//            logger.debug("POST Request JSON:"+postRequestStr);
//            response = given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(postRequestStr).cookies(new Cookies(restAssuredCookies)).post(url);
//            //response = given().contentType(ContentType.JSON).body(postRequestStr).cookies(new Cookies(restAssuredCookies)).post(url);
//            logger.debug("JSON Response::"+response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICallWithCookies exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPutAPICallWithCookies(String url,List<io.restassured.http.Cookie> restAssuredCookies,String jsonRequestFilePath){
//        try{
//            url = urlAddDebug(url);
//            String postRequestStr = JSONValidationUtils.convertJsonFileToString(JsonReaderCommon.jsonRequestFolderPath+ jsonRequestFilePath+".json");
//            logger.debug("PUT Request JSON:"+postRequestStr);
//            response = given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(postRequestStr).cookies(new Cookies(restAssuredCookies)).put(url);
//            //response = given().contentType(ContentType.JSON).body(postRequestStr).cookies(new Cookies(restAssuredCookies)).post(url);
//            logger.debug("JSON Response::"+response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPutAPICallWithCookies exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestAPICallWithGuestUserCookies(String url){
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").when().get(url);
//            logger.debug("JSON Response::"+response.asString());
//
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//
//
//
//    public Response initiateRestAPIPostCallForGuestAuthen(String url){
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().contentType("application/json").post(url);
//            logger.debug("JSON Response::"+response.asString());
//            httpCookies =  response.getDetailedCookies();//given().contentType("application/json").when().post(url).then().statusCode(200).extract().response().getDetailedCookies();
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICallWithCookies exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithoutCookiesAndReqStr(String url,String regRequestStr){
//        try{
//            url = urlAddDebug(url);
//            logger.debug("POST Request JSON:"+regRequestStr);
//            response = given().relaxedHTTPSValidation().contentType("application/json").body(regRequestStr).post(url);
//            logger.debug("JSON Response::"+response.asString());
//            //httpCookies =  given().contentType("application/json").when().body(postRequestStr).post(url).then().statusCode(200).extract().response().getDetailedCookies();
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithoutCookies(String url,String jsonRequestFilePath){
//        try{
//            url = urlAddDebug(url);
//            String postRequestStr = JSONValidationUtils.convertJsonFileToString(JsonReaderCommon.jsonRequestFolderPath+ jsonRequestFilePath+".json");
//            logger.debug("POST Request JSON:"+postRequestStr);
//            response = given().relaxedHTTPSValidation().contentType("application/json").body(postRequestStr).post(url);
//            logger.debug("JSON Response::"+response.asString());
//            //httpCookies =  given().contentType("application/json").when().body(postRequestStr).post(url).then().statusCode(200).extract().response().getDetailedCookies();
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICall(String url,String jsonRequestFilePath){
//        try{
//            url = urlAddDebug(url);
//            String postRequestStr = JSONValidationUtils.convertJsonFileToString(JsonReaderCommon.jsonRequestFolderPath+ jsonRequestFilePath+".json");
//            logger.debug("POST Request JSON:"+postRequestStr);
//            response = given().relaxedHTTPSValidation().contentType("application/json").body(postRequestStr).post(url);
//            logger.debug("JSON Response::"+response.asString());
//            httpCookies =  response.getDetailedCookies();//given().relaxedHTTPSValidation().contentType("application/json").when().body(postRequestStr).post(url).then().statusCode(200).extract().response().getDetailedCookies();
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithCookies(String url,String jsonRequestFilePath){
//        try{
//            url = urlAddDebug(url);
//            String postRequestStr = JSONValidationUtils.convertJsonFileToString(JsonReaderCommon.jsonRequestFolderPath+ jsonRequestFilePath+".json");
//            logger.debug("POST Request JSON:"+postRequestStr);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").body(postRequestStr).post(url);
//            logger.debug("JSON Response::"+response.asString());
//
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithCookiesAndRequestJsonStr(String url,String jsonRequestStr){
//        try{
//            url = urlAddDebug(url);
//            logger.debug("POST Request JSON:"+jsonRequestStr);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").body(jsonRequestStr).post(url);
//            logger.debug("POST Response JSON::"+response.asString());
//
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithCookiesAndWithOutBody(String url){
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").post(url);
//            logger.debug("POST Response JSON::"+response.asString());
//
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//    public Response initiateUpdateRestPostAPICallWithCookies(String url,String jsonRequestStr){
//        try{
//            url = urlAddDebug(url);
//            logger.debug("POST Request JSON:"+jsonRequestStr);
//            response = given().relaxedHTTPSValidation().cookies(httpCookies).contentType("application/json").body(jsonRequestStr).post(url);
//            logger.debug("JSON Response::"+response.asString());
//
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICall exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    public Response initiateRestPostAPICallWithoutBody(String url){
//
//        try{
//            url = urlAddDebug(url);
//            response = given().relaxedHTTPSValidation().contentType("application/json").post(url);
//            logger.debug("JSON Response::"+response.asString());
//        }catch (Exception e) {
//            response = null;
//            logger.error("initiateRestPostAPICallWithCookies exception msg::"+e.getMessage());
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//
//    public String initiateErrorRestAPICall(String strURL){
//
//        try{
//            URL url = new URL(strURL);//"http://35.202.244.154/api/categorycategoryIds={15613,15157,15645}");//"http://35.202.244.154/15613,15157,15645");
//
//            URLConnection urlc = url.openConnection();
//            urlc.setDoOutput(true);
//            urlc.setAllowUserInteraction(false);
//
//            PrintStream ps = new PrintStream(urlc.getOutputStream());
//            ps.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlc
//                    .getInputStream()));
//            String l = null;
//            while ((l=br.readLine())!=null) {
//                logger.debug(l);
//            }
//            br.close();
//        }catch (Exception e) {
//            errorTxt = e.getMessage();
//            logger.error("##### initiateErrorRestAPICall() inside Exception msg :: "+e.getMessage());
//        }
//        return errorTxt;
//    }
//
//    public Response getResponse() {
//        return response;
//    }
//
//    public void setResponse(Response response) {
//        APIJsonHelper.response = response;
//    }
//
//    public String toString()
//    {
//        return APIJsonHelper.response.asString() ;
//    }
//
//    public int getStatusCode()
//    {
//        return APIJsonHelper.response.statusCode() ;
//    }
//
//    public String getStausLine()
//    {
//        return APIJsonHelper.response.statusLine();
//    }
//
//    public String getContentType()
//    {
//        return APIJsonHelper.response.contentType();
//    }
//
//    public String getHeader(String expectedHeader)
//    {
//        return APIJsonHelper.response.header(expectedHeader);
//    }
//
//    public Headers getHeaders()
//    {
//        return APIJsonHelper.response.headers();
//    }
//
//    public ResponseBody<?> getResponseBody()
//    {
//        return APIJsonHelper.response.getBody();
//    }
//
//    public String getResponseBodyToString()
//    {
//        return APIJsonHelper.response.getBody().asString();
//    }
//
//    public String getErrorTxt() {
//        return errorTxt;
//    }
//
//    public void setErrorTxt(String errorTxt) {
//        this.errorTxt = errorTxt;
//    }
//
//
//    public String getValueFromResponse(String jsonPath){
//        String value ="";
//        try{
//            if(response != null){
//                JsonPath jsonPathEvaluator = response.jsonPath();
//                value = jsonPathEvaluator.get(jsonPath);
//            }
//
//        }catch (Exception e) {
//            logger.error("Exception Msg::"+e.getMessage());
//        }
//        logger.debug("Value From Json Response::"+ value);
//        return value;
//    }
//
//    public void validateserver(String server)
//    {
//        response.then().assertThat().header("server", loadProps.getTestDataProperty(server));
//
//    }
//
//    public void validatefieldfromresponse(String arg1)
//    {
//        response.then().body("objectID",equalTo(loadProps.getTestDataProperty(arg1)));
//
//    }
//
//
//    private String urlAddDebug(String url){
//
//        try{
//            if(url!= null){
//                if(url.contains("?")) {
//                    url = url + "&debug=aso";
//                } else {
//                    url = url + "?debug=aso";
//                }
//            }
//        }catch (Exception e) {
//            logger.error("Exception Msg::"+e.getMessage());
//        }
//        logger.debug("EndPoint URL::"+url);
//        return url;
//    }
//}
