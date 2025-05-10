//package common;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//
//public class JSONValidationUtils extends APIJsonHelper {
//    private static final Logger logger = LogManager.getLogger(JSONValidationUtils.class);
//    public static final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
//    public static final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";
//    public static String jsonSchemaValidationErrorMsg = "";
//
//    public boolean validateStatusCode(int expectedCode) {
//        boolean flag = false;
//        logger.debug("Actual:" + getStatusCode() + ":: Expected:" + expectedCode);
//        if (getStatusCode() == expectedCode) {
//            flag = true;
//        }
//        logger.debug("Validate Status Code FLAG::" + flag);
//        return flag;
//    }
//
//    public static boolean isNotNull(JSONObject jObj, String name) {
//        boolean flag = false;
//        try {
//            Object propObj = jObj.get(name);
//            logger.debug(name + "::" + propObj);
//            if (propObj instanceof String) {
//                String cname = propObj.toString();
//                if (cname != null && !cname.trim().isEmpty() && !cname.trim().equalsIgnoreCase("null") && !cname.trim().equalsIgnoreCase("empty")) {
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//            } else if (propObj instanceof Boolean) {
//                flag = true;
//            } else if (propObj instanceof Long) {
//                flag = true;
//            } else if (propObj instanceof Float) {
//                flag = true;
//            } else if (propObj instanceof Double) {
//                flag = true;
//            }
//
//        } catch (Exception e) {
//            flag = false;
//        }
//
//        return flag;
//    }
//
//
//    public static boolean isNotNull(Object propObj) {
//        boolean flag = false;
//        try {
//            if (propObj != null) {
//                if (propObj instanceof String) {
//                    String cname = propObj.toString();
//                    if (cname != null && !cname.trim().isEmpty() && !cname.trim().equalsIgnoreCase("null") && !cname.trim().equalsIgnoreCase("empty")) {
//                        flag = true;
//                    } else {
//                        flag = false;
//                    }
//                } else if (propObj instanceof Boolean) {
//                    flag = true;
//                } else if (propObj instanceof Long) {
//                    flag = true;
//                } else if (propObj instanceof Float) {
//                    flag = true;
//                } else if (propObj instanceof Double) {
//                    flag = true;
//                }
//            }
//        } catch (Exception e) {
//            flag = false;
//        }
//
//        return flag;
//    }
//
//    public boolean validateStatusCode(int actualCode, int expectedCode) {
//        boolean flag = false;
//        logger.debug("Actual:" + actualCode + ":: Expected:" + expectedCode);
//        if (actualCode == expectedCode) {
//            flag = true;
//        }
//        return flag;
//    }
//
//    public boolean validateStatusLine(String actualStatusLine, String expectedStatusLine) {
//        boolean flag = false;
//        logger.debug("Actual:" + actualStatusLine + ":: Expected:" + expectedStatusLine);
//        if (expectedStatusLine != null && expectedStatusLine.contains(actualStatusLine)) {
//            flag = true;
//        }
//        return flag;
//    }
//
//    public boolean validateStatusLine(String expectedStatusLine) {
//        boolean flag = false;
//        logger.debug("Actual:" + getStausLine() + ":: Expected:" + expectedStatusLine);
//        if (expectedStatusLine != null && expectedStatusLine.contains(getStausLine())) {
//            flag = true;
//        }
//        return flag;
//    }
//
//    public boolean validateContentType(String actualContentType, String expectedContentType) {
//        boolean flag = false;
//        logger.debug("Actual:" + actualContentType + ":: Expected:" + expectedContentType);
//        if (expectedContentType != null && expectedContentType.contains(actualContentType)) {
//            flag = true;
//        }
//        return flag;
//    }
//
//    public boolean validateContentType(String expectedContentType) {
//        boolean flag = false;
//        logger.debug("Actual:" + getContentType() + ":: Expected:" + expectedContentType);
//        if (expectedContentType != null && expectedContentType.contains(getContentType())) {
//            flag = true;
//        }
//        return flag;
//    }
//
//
//    public static JsonNode getJsonNode(String jsonText)
//            throws IOException {
//        return JsonLoader.fromString(jsonText);
//    } // getJsonNode(text) ends
//
//    public static JsonNode getJsonNode(File jsonFile)
//            throws IOException {
//        return JsonLoader.fromFile(jsonFile);
//    } // getJsonNode(File) ends
//
//    public static JsonNode getJsonNode(URL url)
//            throws IOException {
//        return JsonLoader.fromURL(url);
//    } // getJsonNode(URL) ends
//
//    public static JsonNode getJsonNodeFromResource(String resource)
//            throws IOException {
//        return JsonLoader.fromResource(resource);
//    } // getJsonNode(Resource) ends
//
//    public static JsonSchema getSchemaNode(String schemaText)
//            throws IOException, ProcessingException {
//        final JsonNode schemaNode = getJsonNode(schemaText);
//        return _getSchemaNode(schemaNode);
//    } // getSchemaNode(text) ends
//
//    public static JsonSchema getSchemaNode(File schemaFile)
//            throws IOException, ProcessingException {
//        final JsonNode schemaNode = getJsonNode(schemaFile);
//        return _getSchemaNode(schemaNode);
//    } // getSchemaNode(File) ends
//
//    public static JsonSchema getSchemaNode(URL schemaFile)
//            throws IOException, ProcessingException {
//        final JsonNode schemaNode = getJsonNode(schemaFile);
//        return _getSchemaNode(schemaNode);
//    } // getSchemaNode(URL) ends
//
//    public static JsonSchema getSchemaNodeFromResource(String resource)
//            throws IOException, ProcessingException {
//        final JsonNode schemaNode = getJsonNodeFromResource(resource);
//        return _getSchemaNode(schemaNode);
//    } // getSchemaNode() ends
//
//    public static boolean validateJson(JsonSchema jsonSchemaNode, JsonNode jsonNode)
//            throws ProcessingException {
//        boolean flag = false;
//        ProcessingReport report = jsonSchemaNode.validate(jsonNode);
//        flag = report.isSuccess();
//        logger.debug("JSON Schema Validation is Success::" + flag);
//        if (!flag) {
//            for (ProcessingMessage processingMessage : report) {
//                throw new ProcessingException(processingMessage);
//            }
//        }
//        return flag;
//    } // validateJson(Node) ends
//
//    public static boolean isJsonValid(JsonSchema jsonSchemaNode, JsonNode jsonNode) throws ProcessingException {
//        ProcessingReport report = jsonSchemaNode.validate(jsonNode);
//        return report.isSuccess();
//    } // validateJson(Node) ends
//
//    public static boolean isJsonValid(String schemaText, String jsonText) throws ProcessingException, IOException {
//        final JsonSchema schemaNode = getSchemaNode(schemaText);
//        final JsonNode jsonNode = getJsonNode(jsonText);
//        return isJsonValid(schemaNode, jsonNode);
//    } // validateJson(Node) ends
//
//    public static boolean isJsonValid(File schemaFile, File jsonFile) throws ProcessingException, IOException {
//        final JsonSchema schemaNode = getSchemaNode(schemaFile);
//        final JsonNode jsonNode = getJsonNode(jsonFile);
//        return isJsonValid(schemaNode, jsonNode);
//    } // validateJson(Node) ends
//
//    public static boolean isJsonValid(URL schemaURL, URL jsonURL) throws ProcessingException, IOException {
//        final JsonSchema schemaNode = getSchemaNode(schemaURL);
//        final JsonNode jsonNode = getJsonNode(jsonURL);
//        return isJsonValid(schemaNode, jsonNode);
//    } // validateJson(Node) ends
//
//    public static void validateJson(String schemaText, String jsonText) throws IOException, ProcessingException {
//        final JsonSchema schemaNode = getSchemaNode(schemaText);
//        final JsonNode jsonNode = getJsonNode(jsonText);
//        validateJson(schemaNode, jsonNode);
//    } // validateJson(text) ends
//
//    public static void validateJson(File schemaFile, File jsonFile) throws IOException, ProcessingException {
//        final JsonSchema schemaNode = getSchemaNode(schemaFile);
//        final JsonNode jsonNode = getJsonNode(jsonFile);
//        validateJson(schemaNode, jsonNode);
//    } // validateJson(File) ends
//
//    public static void validateJson(URL schemaDocument, URL jsonDocument) throws IOException, ProcessingException {
//        final JsonSchema schemaNode = getSchemaNode(schemaDocument);
//        final JsonNode jsonNode = getJsonNode(jsonDocument);
//        validateJson(schemaNode, jsonNode);
//    } // validateJson(URL) ends
//
//    public static boolean validateJsonResource(String schemaResource, String jsonResource) throws IOException, ProcessingException {
//        final JsonSchema schemaNode = getSchemaNode(schemaResource);
//        final JsonNode jsonNode = getJsonNodeFromResource(jsonResource);
//        return validateJson(schemaNode, jsonNode);
//    } // validateJsonResource() ends
//
//    private static JsonSchema _getSchemaNode(JsonNode jsonNode)
//            throws ProcessingException {
//        final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
//        if (null == schemaIdentifier) {
//            ((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
//        }
//
//        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
//        return factory.getJsonSchema(jsonNode);
//    } // _getSchemaNode() ends
//
//    public static String convertJsonFileToString(String filePath) {
//        String content = "";
//        try {
//            content = new String(Files.readAllBytes(Paths.get(filePath)));
//        } catch (IOException e) {
//            logger.error("convertJsonFileToString IOException msg::" + e.getMessage());
//            e.printStackTrace();
//        }
//        return content;
//    }
//
//    public static JSONObject getJSONObject(String responseStr) throws ParseException {
//
//        return (JSONObject) new JSONParser().parse(responseStr);
//    }
//
//    public boolean validateJsonSchema(String jsonSchemaFilePath) {
//        boolean flag = false;
//        jsonSchemaValidationErrorMsg = "";
//        try {
//            flag = isJsonValid(convertJsonFileToString(JsonReaderCommon.jsonSchemaFolderPath + jsonSchemaFilePath + ".json"), response.asString());
//
//        } catch (ProcessingException e) {
//            jsonSchemaValidationErrorMsg = e.getMessage();
//            logger.error("ProcessingException msg::" + e.getMessage());
//        } catch (Exception e) {
//            jsonSchemaValidationErrorMsg = e.getMessage();
//            logger.error("IOException msg::" + e.getMessage());
//        }
//        logger.debug("JSON Schema Validate FLAG:: " + flag + "ERROR Txt:" + jsonSchemaValidationErrorMsg);
//        return flag;
//    }
//}
