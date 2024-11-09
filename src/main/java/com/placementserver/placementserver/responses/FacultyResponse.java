package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Faculty;

public class FacultyResponse {
	
	private String condition;
	private String message;
	private ReturnFaculty returnFaculty;
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
	public ReturnFaculty getReturnFaculty() {
		return returnFaculty;
	}
	public void setReturnFaculty(ReturnFaculty returnFaculty) {
		this.returnFaculty = returnFaculty;
	}
	public FacultyResponse(String condition, String message, Faculty faculty) {
		super();
		this.condition = condition;
		this.message = message;
		this.returnFaculty = new ReturnFaculty(faculty);
	}
	
}
