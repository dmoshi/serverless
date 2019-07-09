package com.dmoshi.lambda.contactme.model;

import java.io.Serializable;

public class ContactMe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sendersEmail;
	private String message;
	
	
	public String getSendersEmail() {
		return sendersEmail;
	}
	public void setSendersEmail(String sendersEmail) {
		this.sendersEmail = sendersEmail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
