package com.placementserver.placementserver.models;

public class StudentAnswer {
	
	private float markPercentage;
	private int correct;
	private int wrong;
	private long questionid;
	private long rollno;
	private String name;
	private String department;
	private short year;
	private short semester;
	
	public float getMarkPercentage() {
		return markPercentage;
	}
	public void setMarkPercentage(float markPercentage) {
		this.markPercentage = markPercentage;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getWrong() {
		return wrong;
	}
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public long getRollno() {
		return rollno;
	}
	public void setRollno(long rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	
	public StudentAnswer() {
		super();
	}
	
	public StudentAnswer(float markPercentage, int correct, int wrong, long questionid, long rollno, String name,
			String department, short year, short semester) {
		super();
		this.markPercentage = markPercentage;
		this.correct = correct;
		this.wrong = wrong;
		this.questionid = questionid;
		this.rollno = rollno;
		this.name = name;
		this.department = department;
		this.year = year;
		this.semester = semester;
	}
}
