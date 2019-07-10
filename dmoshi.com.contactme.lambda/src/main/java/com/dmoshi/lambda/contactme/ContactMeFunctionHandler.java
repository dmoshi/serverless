package com.dmoshi.lambda.contactme;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.dmoshi.lambda.contactme.model.ContactMe;

public class ContactMeFunctionHandler
		implements  RequestHandler<ContactMe, Object>{

	AmazonSNS snsClient = null;

	public ContactMeFunctionHandler() {
		String region = System.getenv("REGION_NAME");
		AmazonSNSClientBuilder snsClientBuilder = AmazonSNSClientBuilder.standard();
		snsClientBuilder.setRegion(region);
		snsClientBuilder.setCredentials(DefaultAWSCredentialsProviderChain.getInstance());
		snsClient = snsClientBuilder.build();
	}

	@Override
	public Object handleRequest(ContactMe input, Context context) {
		String topicArn = System.getenv("SNS_TOPIC_ARN");
		String subject = System.getenv("SUBJECT");
		Response response = new Response();
		try {
			if (input == null) {
				response.setStatusCode(400);
				response.setMessage("Request body is null");
			} else {
				context.getLogger().log("Body: " + input.toString());
				context.getLogger().log("Input: " + input);
				final PublishRequest publishRequest = new PublishRequest(topicArn,
						input.getSendersEmail() + " : " + input.getMessage());
				publishRequest.setSubject(subject);
				final PublishResult publishResponse = snsClient.publish(publishRequest);
				if (publishResponse.getMessageId() != null) {
					response.setStatusCode(200);
					response.setMessage("sent successfully");
					response.setMessageId(publishResponse.getMessageId());
				}

			}
		} catch (Throwable ex) {
			context.getLogger().log("Notification not sent. Error message: " + ex.getMessage());
			response.setStatusCode(500);
				response.setMessage("EXCEPTION : " + ex.getMessage());
			 
			ex.printStackTrace();

		}
		return response;
	}
	
	class Response {
		String messageId;
		int  statusCode;
		String message;
		
		public String getMessageId() {
			return messageId;
		}
		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int status) {
			this.statusCode = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
	}
}
