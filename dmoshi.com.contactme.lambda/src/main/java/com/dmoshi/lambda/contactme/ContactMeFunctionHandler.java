package com.dmoshi.lambda.contactme;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.dmoshi.lambda.contactme.model.ContactMe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactMeFunctionHandler
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private final ObjectMapper mapper = new ObjectMapper();
	AmazonSNS snsClient = null;

	public ContactMeFunctionHandler() {
		String region = System.getenv("REGION_NAME");
		AmazonSNSClientBuilder snsClientBuilder = AmazonSNSClientBuilder.standard();
		snsClientBuilder.setRegion(region);
		snsClientBuilder.setCredentials(DefaultAWSCredentialsProviderChain.getInstance());
		snsClient = snsClientBuilder.build();
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		String topicArn = System.getenv("SNS_TOPIC_ARN");
		String subject = System.getenv("SUBJECT");
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		Map<String, String> headers = new HashMap<>();
		headers.put("Access-Control-Allow-Origin", "*");
		headers.put("Access-Control-Allow-Credentials", "false");
		response.setHeaders(headers);
		try {
			String eventRecv = event.getBody();
			if (eventRecv == null) {
				response.setStatusCode(400);
				response.setBody(mapper.writeValueAsString("Request body is null"));
			} else {
				context.getLogger().log("Body: " + eventRecv);
				ContactMe input = mapper.readValue(eventRecv, ContactMe.class);
				context.getLogger().log("Input: " + input);
				final PublishRequest publishRequest = new PublishRequest(topicArn,
						input.getSendersEmail() + " : " + input.getMessage());
				publishRequest.setSubject(subject);
				final PublishResult publishResponse = snsClient.publish(publishRequest);
				if (publishResponse.getMessageId() != null) {
					response.setStatusCode(200);
					response.setBody(mapper.writeValueAsString(publishResponse.toString()));
				}

			}
		} catch (Throwable ex) {

			context.getLogger().log("Notification not sent. Error message: " + ex.getMessage());
			response.setStatusCode(500);
			try {
				response.setBody(mapper.writeValueAsString("EXCEPTION : " + ex.getMessage()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();

		}
		return response;
	}
}
