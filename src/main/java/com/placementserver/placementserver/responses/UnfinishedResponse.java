package com.placementserver.placementserver.responses;

public class UnfinishedResponse {
	
	private long questionId;
	private String name;
	private String fileName;
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public UnfinishedResponse() {
		super();
	}
	public UnfinishedResponse(long questionId, String name, String fileName) {
		super();
		this.questionId = questionId;
		this.name = name;
		this.fileName = fileName;
	}
	
}
