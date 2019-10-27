package com.aurigainterviews.aurigainterview;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.*;

public class JUnitControllerTest {

    String theResponse = "[{ \"2b727991-5ddb-4a42-82eb-13bbb2876a28\": { \"agentVersion\": \"0.1.0\", \"alertIds\": [\"0a45fa06-87ad-4ef7-ae30-f05d42beca22\", \"16597623-a5d1-4a21-8304-58c458aefd3b\"], \"architecture\": \"x64\", \"collector\": {\"tenantId\": \"496e3cfd-672c-47ae-9dfb-0d840b0aed80\", \"collectorId\": \"5316b276-18cc-4692-b89b-26ae22a0c4ef\", \"collectorName\": \"Test_Collector\"}, \"cpuModel\": \"Intel(R) Xeon(R) CPU E5-2673 v3 @ 2.40GHz\", \"cpuNumber\": 1, \"description\": \"Auriga Test Device\", \"deviceId\": \"2b727991-5ddb-4a42-82eb-13bbb2876a28\", \"discoveryDate\": \"2019-05-21T16:02:56.819\", \"externalIp\": \"144.0.1.163\", \"externalIpCordLat\": \"52.35\", \"externalIpCordLong\": \"4.9167\", \"externalIpDateUpdated\": \"2019-06-10T09:33:07.932\", \"ipAddresses\": [{\"ipAddress\": \"1.0.0.4\", \"ipFamily\": \"IPv4\", \"macAddress\": \"01:1d:3a:20:da:2d\"},{\"ipAddress\": \"10.0.0.1\", \"ipFamily\": \"IPv4\", \"macAddress\": \"02:1d:3a:20:da:2d\"}], \"isAgentConnected\": true, \"lastSeenDate\": \"2019-06-10T09:46:00.176Z\", \"name\": \"AurigaDC01\", \"osCode\": \"Windows_NT\", \"platform\": \"win32\", \"registeredDate\": \"2019-05-21T16:02:56.819\", \"release\": \"6.3.9600\", \"updatesScriptLastRun\": \"2019-06-10T09:24:10\" }}]";

    String desiredResponse = "{ “name”: \"AurigaDC01\", \"agentVersion\": \"0.1.0\", \"howManyAlerts\": 2, \"architecture\": \"x64\", \"collector\": \"Test_Collector\", \"cpuModel\": \"Intel(R) Xeon(R) CPU E5-2673 v3 @ 2.40GHz\", \"description\": \"Auriga Test Device\", \"discoveryDate\": \"2019-05-21\", \"ipAddresses\": [\"1.0.0.4\",”10.0.0.1”] }";

    @Test
    public void getResouce() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/response.json";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();

        assertEquals(statusCode, HttpStatus.OK);

        if(statusCode == HttpStatus.OK){
//            System.out.println("status code: " + statusCode);
//            System.out.println("content type: " + contentType);
//            System.out.println("body: " + body);
            assertEquals(body, theResponse);

            responseToJSON(body);

        }
    }

    void responseToJSON(String response) {

        try {
            JSONArray jsonArr = new JSONArray(response);
//            System.out.println("Json Array: " + jsonArr);

            for (int i = 0; i < jsonArr.length(); i++)
            {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
//                System.out.println("Json Object: " + jsonObj);
                parseJson(jsonObj);
            }
        }
        catch (JSONException err){
            System.out.println("err: " + err.toString());
        }
    }

    void parseJson(JSONObject json){
            Iterator keys = json.keys();

            while(keys.hasNext()){
                String currentKey = (String) keys.next();

//                System.out.println("current key: " + currentKey);

                try {
                    String currentValue = json.getString(currentKey);
//                    System.out.println("current value (json string): " + currentValue);

                    ObjectMapper mapper = new ObjectMapper();
                    DataHolder dataHolder = mapper.readValue(currentValue, DataHolder.class);

                    System.out.println("dataholder: " + dataHolder.toString());
                    String clientResponse = dataHolder.toString();

                    buildResponse(clientResponse);

                }
                catch (JSONException | IOException err) {
                    System.out.println(err.toString());
                }

            }
    }

    void buildResponse(String clientResponse){


        assertEquals(desiredResponse, clientResponse);
    }

}
