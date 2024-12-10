package com.placementserver.placementserver.responses;

public class DataResponse<T> {
	
	private String condition;
	private String message;
	private T datas;

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
	public T getDatas() {
		return datas;
	}
	public void setDatas(T datas) {
		this.datas = datas;
	}
	public DataResponse() {
		super();
	}
	public DataResponse(String condition, String message, T datas) {
		super();
		this.condition = condition;
		this.message = message;
		this.datas = datas;
	}
}
