package org.zayanma.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIManager {

    String API_Key = "RGAPI-009194e9-420a-4cda-9ea4-727ce7d19415";

    public APIManager(){
    }

    public String getAPIResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
            return response.toString();  //create and reutrn string if response exists
        }else {
            return "0"; //return 0 if no response
        }
    }

    public String puuidFromName(String region, String username, String tagline) throws IOException {
        URL url = new URL("https://" + region + ".api.riotgames.com/riot/account/v1/accounts/by-riot-id/"
                + username +"/" + tagline + "?api_key=" + API_Key);
        String response = getAPIResponse(url);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(String.valueOf(response));
        return jsonNode.get("puuid").asText();
    }
}
