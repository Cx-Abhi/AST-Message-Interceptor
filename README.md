# AST WebHook Interceptor
A Spring Boot REST API application to intercept Checkmarx AST webhook messages and send AST scan summary to multiple notification channels (currently only Slack notification is implemented). 

## Build (Maven Build)
`mvn package`

## Run the application
`java -jar ast-webhook-interceptor.jar`

## URL
Once we run the interceptor spring boot application, it will be listening for HTTP POST Webhook payload from Chaeckmarx AST on the following URL
> http://{ip or fqdn}:8081/consume_message

## Prerequisites
>
The `application.properties` file available under src\main\resources\ folder has the following keys which needs to be preconfigured before building the application
| Key | Description |
---------|-------------|
server.port|	The personal access token for the organization to enable the calls to GitHub REST API's
logging.pattern.console|	The pattern in which the logs entries ought to be written (The application is using slf4j for logging)
logging.file.name|	The filename of the Log file
logging.pattern.file|	The pattern in which the log files names will be created (slf4j)
refresh.token| AST API token that will be used to fetch the access_token from AST REST API
ast.auth.token.url| The url of Checkmarx AST from where to fetch the access_token
ast.result.summary.url| Checkmarx REST API endpoint, to fetch the scan details based on the scan-id
slack.notification| Flag indicating whether to send Slack notification
slack.url| Slack webhook endpoint where the AST Message Interceptor will post the scan summary

## Build
Executable JAR is compiled using maven
**Java 11 JRE**


