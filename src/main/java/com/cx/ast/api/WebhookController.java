package com.cx.ast.api;

import com.cx.ast.api.invoker.InvokeASTRestAPI;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;


@RestController
public class WebhookController {

    @Value("${refresh.token}")
    private String refreshToken;

    @Value("${ast.auth.token.url}")
    private String authTokenURL;

    @Value("${ast.result.summary.url}")
    private String astResultURL;

    @Value("${slack.notification}")
    private String slackNotification;

    @Value("${slack.url}")
    private String slackWebhook;

    Logger log = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping("/consume_message")
    public void consumeASTPayload(@RequestBody String body) throws IOException {

        log.info("Received Message from AST :: "+body);

        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        String scanID = jsonObject.get("scanId").getAsString();
        String projectID = jsonObject.get("projectId").getAsString();

        log.info( "Scan ID:: " +scanID );
        log.info( "Project ID:: " +  projectID);

        InvokeASTRestAPI invokeASTRestAPI = new InvokeASTRestAPI();
        String astAccessToken = invokeASTRestAPI.invokeASTAuthEndpoint(refreshToken, authTokenURL);

        String astScanResult = invokeASTRestAPI.invokeASTGetScanResultAPI(astAccessToken, astResultURL, scanID, projectID);

        if(slackNotification != null && slackNotification.equalsIgnoreCase("yes")) {
            StringBuilder messageBuider = new StringBuilder();
            messageBuider.append(" AST Scan Result :: " + astScanResult + "\n");

            Payload payload = Payload.builder().channel("alerts").text(messageBuider.toString()
            ).build();

            WebhookResponse
                     response = Slack.getInstance().send(slackWebhook, payload);

            log.info( "Slack Response :: " +  response.getCode());

        }

    }
}
