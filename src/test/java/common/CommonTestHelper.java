//package common;
//
//
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.Assert;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Base64;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//public class CommonTestHelper {
//
//    public static PropertiesHelper webPropHelper = PropertiesHelper.getInstance();
//
//    static String jdbcClassName = "";
//    static String url = webPropHelper.getTestDataProperty("JDBC_url");
//    static String jdbcUsername = webPropHelper.getTestDataProperty("JDBC_Username");
//    static String jdbcPassword = getDecryptedString(webPropHelper.getTestDataProperty("JDBC_Password"));
//    static ResultSet result;
//    public static int currentMailCount;
//    public static int intialMailCount;
//
//    public static void main(String[] args) {
//        String apiEndpointWebURL = "https://uat6www.academy.com/?debug=aso";
//
//        System.out.println(getAPIEndpointWebURL(apiEndpointWebURL));
//
//    }
//
//    public static String getAPIEndpointWebURL(String apiEndpointWebURL){
//        if(apiEndpointWebURL!= null && apiEndpointWebURL.contains("/?debug=aso")){
//            apiEndpointWebURL = apiEndpointWebURL.substring(0,apiEndpointWebURL.indexOf("/?debug=aso"));
//        }
//        return apiEndpointWebURL;
//    }
//
//    public static String executeSelectQuery(String sqlQuery, String... columnName){
//        String resultValue = "";
//        java.sql.Connection connection = null;
//        try {
//            // Load class into memory
//            Class.forName(jdbcClassName);
//            // Establish connection
//            connection = DriverManager.getConnection(url, jdbcUsername, jdbcPassword);
//            Statement stmt = connection.createStatement();
//            stmt.setQueryTimeout(180);
//            System.out.println("QUERY = " + sqlQuery);
//            logger.info("QUERY = " + sqlQuery);
//            result = stmt.executeQuery(sqlQuery);
//            if (result.next()) {
//                if (columnName.length == 1)
//                    resultValue = result.getString(columnName[0]);
//                else {
//                    for (String col : columnName)
//                        resultValue = resultValue + result.getString(col) + ":";
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            logger.info("JDBC ERROR!!!!  Please check user credentials");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
//            }
//        }
//        return resultValue;
//    }
//
//    public static String executeSelectQueryWithWait(String sqlQuery, int waitInMins, String... columnName) {
//        String resultValue = "";
//        java.sql.Connection connection = null;
//        int delayTime = waitInMins;
//        try {
//            // Load class into memory
//            Class.forName(jdbcClassName);
//            // Establish connection
//            connection = DriverManager.getConnection(url, jdbcUsername, jdbcPassword);
//            Statement stmt = connection.createStatement();
//            stmt.setQueryTimeout(180);
//            logger.info("QUERY = " + sqlQuery);
//            System.out.println("QUERY = " + sqlQuery);
//            while (resultValue.equals("") & delayTime > 0) {
//                result = stmt.executeQuery(sqlQuery);
//                if (result.next()) {
//                    if (columnName.length == 1) resultValue = result.getString(columnName[0]);
//                    else {
//                        for (String col : columnName)
//                            resultValue = resultValue + result.getString(col) + ":";
//                    }
//                } else {
//                    Thread.sleep(60000);
//                    delayTime--;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            logger.info("JDBC ERROR!!!!  Please check user credentials");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return resultValue;
//    }
//
//    public static String executeUpdateQuery(String sqlQuery){
//        String resultValue= "";
//        java.sql.Connection connection = null;
//        try {
//            // Load class into memory
//            Class.forName(jdbcClassName);
//            // Establish connection
//            connection = DriverManager.getConnection(url, jdbcUsername, jdbcPassword);
//            Statement stmt = connection.createStatement();
//            stmt.setQueryTimeout(180);
//            logger.info("QUERY = " + sqlQuery);
//            int result = stmt.executeUpdate(sqlQuery);
//            if(result>0)
//                logger.info("Update Successfull");
//            else
//                logger.info("Update not Successfull");
//        } catch (ClassNotFoundException e) {
//            logger.info("JDBC ERROR!!!!  Please check user credentials");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
//            }
//        }
//        return resultValue;
//    }
//
//    public static void writeToFile(String path, String content)
//    {
//        try {
//            FileWriter myWriter = new FileWriter(path);
//            myWriter.write(content);
//            myWriter.close();
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    public static String getUpcCode(String itemId) {
//        return CommonTestHelper.executeSelectQuery(webPropHelper.getTestDataProperty("getUPCCode").replaceAll("@itemId", itemId), "alias_value");
//    }
//
//    public static String getDecryptedString(String value) {
//        byte[] decodedBytes = Base64.getDecoder().decode(value);
//        return new String(decodedBytes);
//    }
//
//    public static int getMailCount(String mail7Id) {
//        Response response = getInboxResponseFromMail7(mail7Id);
//        JsonPath jsonpath = response.jsonPath();
//        intialMailCount = jsonpath.getJsonObject("data.size()");
//        System.out.println("Initial mail count is ==>" + intialMailCount);
//        return intialMailCount;
//    }
//
//    public static Response getMailResponse(String mail7Id) {
//        Response response = getInboxResponseFromMail7(mail7Id);
//        JsonPath jsonpath = response.jsonPath();
//        currentMailCount = jsonpath.getJsonObject("data.size()");
//        System.out.println("Current mail count is ==>" + currentMailCount);
//        return response;
//    }
//
//    public static String getLatestMailBodyContent(String mail7Id) {
//        intialMailCount = getMailCount(mail7Id);
//        Response response = getMailResponse(mail7Id);
//        String mailBodyContent = null;
//        int time = 6;
//        while (time > 1) {
//            if (currentMailCount > intialMailCount) {
//                JsonPath jsonpath = response.jsonPath();
//                Object text = jsonpath.getJsonObject("data[0].mail_source.text");
//                mailBodyContent = text.toString();
//                break;
//            } else {
//                time--;
//                sleep(100000);
//                response = getMailResponse(mail7Id);
//            }
//        }
//        Assert.assertNotEquals("Latest mail not received", intialMailCount, currentMailCount);
//        return mailBodyContent;
//    }
//
//    public static Response getInboxResponseFromMail7(String mail7Id) {
//        Response response = null;
//        String[] arrOfStr = mail7Id.split("@", -2);
//        String mail7username = arrOfStr[0];
//        RequestSpecification request = RestAssured.given();
//        RestAssured.baseURI = "https://api.mail7.io";
//        RestAssured.basePath = "/inbox?apikey=";
//        String completeURL = RestAssured.baseURI + RestAssured.basePath + webPropHelper.getConfigPropProperty("mail7ApiKey") + "&apisecret=" + webPropHelper.getConfigPropProperty("mail7ApiSecretKey") + "&to=" + mail7username;
//        System.out.println("complete uri =======> " + completeURL);
//        response = request.get(completeURL);
//        System.out.println("response=====> " + response);
//        return response;
//    }
//
//    public static String getPaymentMethodUsed(String mail7Id, String paymentMethod) {
//        String mailBodyContent = getLatestMailBodyContent(mail7Id);
//        Pattern pattern = Pattern.compile(Pattern.quote(paymentMethod), Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(mailBodyContent);
//        if (matcher.find()) {
//            System.out.println("Payment Method used: " + matcher.group());
//            return matcher.group();
//        } else {
//            System.out.println("Payment Method not found in mail body");
//            return null;
//        }
//    }
//
//    public static String getMailSubject(String mail7Id, String subject) {
//        String mailBodyContent = getLatestMailBodyContent(mail7Id);
//        Pattern pattern = Pattern.compile(Pattern.quote(subject), Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(mailBodyContent);
//        if (matcher.find()) {
//            System.out.println("Subject is: " + matcher.group());
//            return matcher.group();
//        } else {
//            System.out.println("Subject is not same as provided");
//            return null;
//        }
//    }
//}
