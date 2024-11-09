package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Student;

public class ReturnStudent {
	
	private long rollno;
	private String name;
	private short year;
	private String department;
	private short semester;
	
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
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public short getSemester() {
		return semester;
	}
	public void setSemester(short semester) {
		this.semester = semester;
	}
	
	public ReturnStudent(Student students) {
		super();
		this.rollno = students.getRollno();
		this.name = students.getName();
		this.year = students.getYear();
		this.department = students.getDepartment();
		this.semester = students.getSemester();
	}
	
}
