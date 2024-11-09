package com.placementserver.placementserver.responses;

public class TokenResponse {
	
	private String condition;
	private String message;
	private String jwtToken;
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public TokenResponse(String condition, String message, String jwtToken) {
		super();
		this.condition = condition;
		this.message = message;
		this.jwtToken = jwtToken;
	}
}
