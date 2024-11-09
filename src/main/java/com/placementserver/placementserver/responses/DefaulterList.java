package com.placementserver.placementserver.responses;

public class DefaulterList {
	
	private long rollno;
	private String department;
	private String name;
	private short year;
	private short semester;
	private long questionId;
	private String questionName;
	
	public long getRollno() {
		return rollno;
	}
	public void setRollno(long rollno) {
		this.rollno = rollno;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public short getSemester() {
		return semester;
	}
	public void setSemester(short semester) {
		this.semester = semester;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	
	public DefaulterList() {
		super();
	}
	
	public DefaulterList(long rollno, String department, String name, short year, short semester, long questionId,
			String questionName) {
		super();
		this.rollno = rollno;
		this.department = department;
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.questionId = questionId;
		this.questionName = questionName;
	}
}
