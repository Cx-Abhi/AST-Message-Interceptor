package com.cx.ast.api.invoker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class InvokeASTRestAPI {

    Logger log = LoggerFactory.getLogger(InvokeASTRestAPI.class);
    RestTemplate template = new RestTemplate();

    public String invokeASTAuthEndpoint(String refreshToken, String authTokenURL) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> inputMap = new LinkedMultiValueMap<>();
        inputMap.add("grant_type", "refresh_token");
        inputMap.add("client_id", "ast-app");
        inputMap.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new
                HttpEntity<MultiValueMap<String, String>>(inputMap, headers);

        String url = authTokenURL;

        ResponseEntity<String> response = template.postForEntity(url, entity, String.class);
        JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();

        return accessToken;
    }

    public String invokeASTGetScanResultAPI(String authToken, String astResultURL, String scanID, String projectID) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Spring's RestTemplate" );
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity httpEntity  = new HttpEntity<>(headers);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", scanID);

        ResponseEntity<Map> response = template.exchange(astResultURL, HttpMethod.GET, httpEntity, Map.class, uriVariables);
        log.info( "scan summary :: " +  response.getBody().toString());

        return response.getBody().toString();
    }

}
