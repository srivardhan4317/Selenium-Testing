package commonPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ApiHelper {
    private static final ConfigLoader configLoader = new ConfigLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String base_jsonPath = System.getProperty("user.dir") + "/src/test/resources/payloads/";
    public static final String Base_Url = configLoader.getProperty("BaseUri");
    public static final String Token = configLoader.getProperty("bearerToken");
    public static final String Secret_Key = configLoader.getProperty("consumer_key");
    public static Response response;


    private HashMap<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Token);
        headers.put("X-CONSUMER-KEY", Secret_Key);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private String readJsonFile(String jsonFileName) throws IOException {
        String filePath = base_jsonPath + jsonFileName;
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public Response getApiRequest(String... params) {
        try {
            String getUrl = configLoader.getProperty("getUrl1");
            response = RestAssured.given().relaxedHTTPSValidation().headers(createHeaders()).get(getUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    public void makePostRequest(HashMap<String, Object> requestBody) {
        try {
            String postUrl = configLoader.getProperty("posturl1");
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            response = RestAssured.given().relaxedHTTPSValidation().headers(createHeaders()).body(jsonBody).post(postUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendPostRequest(String jsonTemplateName, HashMap<String, String> replacements) {
        try {
            String populatedJson = readJsonFile(jsonTemplateName);
            for (String key : replacements.keySet()) {
                populatedJson = populatedJson.replace(key, replacements.get(key));
                System.out.println("Populated Json: \n" + populatedJson);
                HashMap requestBody = objectMapper.readValue(populatedJson, HashMap.class);
                makePostRequest(requestBody);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




}
