package com.placementserver.placementserver.responses;

public class ApiResponse {
	
	private String condition;
	private String message;
	
	public ApiResponse(String condition, String message) {
		super();
		this.condition = condition;
		this.message = message;
	}

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
	
	
}
