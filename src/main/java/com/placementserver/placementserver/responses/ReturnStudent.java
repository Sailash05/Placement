package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Student;

public class ReturnStudent {
	
	private long rollno;
	private String name;
	private short year;
	private String department;
	private short semester;
	private String email;
	private long mobileno;

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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobileno() {
		return mobileno;
	}
	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
	}

	public ReturnStudent(Student student) {
		this.rollno = student.getRollno();
		this.name = student.getName();
		this.year = student.getYear();
		this.department = student.getDepartment();
		this.semester = student.getSemester();
		this.email = student.getEmail();
		this.mobileno = student.getMobileno();
	}
}
