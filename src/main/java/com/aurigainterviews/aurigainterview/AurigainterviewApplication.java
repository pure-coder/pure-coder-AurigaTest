package com.aurigainterviews.aurigainterview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;

@RestController
@SpringBootApplication
public class AurigainterviewApplication {

	private static final Logger log = LoggerFactory.getLogger(AurigainterviewApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AurigainterviewApplication.class, args);
	}

	// Get json data from external URL
	String getResource() {
		RestTemplate restTemplate = new RestTemplate();

//		This is the external url being called
//		String externalUrl = "https://api.cybergator.co.uk/testing/devices";

		// This is a call to an url to get the assumed response from the call to external url above.
		String url = "http://localhost:8080/externalUrl.json";

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		String body = responseEntity.getBody();
		HttpStatus statusCode = responseEntity.getStatusCode();

		if(statusCode == HttpStatus.OK){
			return body;
		}
		else{
			log.info("err: Could not load resource");
			return null;
		}

	}

	// Get the object from json array.
	String responseToJSON(String response) {

		try {
			JSONArray jsonArr = new JSONArray(response);
			return parseJson(jsonArr.getJSONObject(0));
		}
		catch (JSONException err){
			log.info(err.toString());
			return null;
		}
	}

	// Parse the json object and deserialize into object to get desired data from object
	String parseJson(JSONObject json){

		Iterator keys = json.keys();

		while(keys.hasNext()){
			String currentKey = (String) keys.next();
			try {
				String currentValue = json.getString(currentKey);

				ObjectMapper mapper = new ObjectMapper();
				DataHolder dataHolder = mapper.readValue(currentValue, DataHolder.class);

				return buildResponse(dataHolder);
			}
			catch (JSONException | IOException err) {
				log.info(err.toString());
				return null;
			}

		}
		return null;
	}

	// Serialize Object with specified data into json which will be sent back to UI
	String buildResponse(DataHolder data){

		DataHolderSerializer dataHolderSerializer = new DataHolderSerializer(DataHolder.class);
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("dataHolderSerializer", new Version(2, 1, 3, null, null, null));
		module.addSerializer(DataHolder.class, dataHolderSerializer);
		objectMapper.registerModule(module);

		try {
			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			log.info(e.toString());
			return null;
		}
	}

	// Get request from user for device details, call external url, send data back to user.
	@GetMapping(value="/client_response", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity<String> clientResponse(){
		String urlResponse = getResource();

		if(urlResponse != null){
			String clientResponse = responseToJSON(urlResponse);
			return ResponseEntity.ok().body(clientResponse);
		}
		else{
			return ResponseEntity.notFound().build();
		}
	}

}
