package com.placementserver.placementserver.responses;

import java.util.List;

public class Response<T> {
	
	private String condition;
	private String message;
	private List<T> datas;
	
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
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	public Response() {
		super();
	}
	
	public Response(String condition, String message, List<T> datas) {
		super();
		this.condition = condition;
		this.message = message;
		this.datas = datas;
	}
	
	
}
