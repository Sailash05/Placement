package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Student;

public class StudentResponse {
	
	private String condition;
	private String message;
	private ReturnStudent returnStudent;
	public StudentResponse(String condition, String message, Student students) {
		super();
		this.condition = condition;
		this.message = message;
		this.returnStudent = new ReturnStudent(students);
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
	public ReturnStudent getReturnStudent() {
		return returnStudent;
	}
	public void setReturnStudent(ReturnStudent returnStudent) {
		this.returnStudent = returnStudent;
	}
}