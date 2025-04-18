package com.placementserver.placementserver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {
	
	@Id
	@Column(name="rollno")
	private long rollno;
	@Column(name="name")
	private String name;
	@Column(name="year")
	private short year;
	@Column(name="semester")
	private short semester;
	@Column(name="department")
	private String department;
	@Column(name="password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "mobileno")
	private long mobileno;
	@Column(name = "leetcode_username")
	private String leetcodeUserName;

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
	public short getSemester() {
		return semester;
	}
	public void setSemester(short semester) {
		this.semester = semester;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getMobileno() {
		return mobileno;
	}
	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLeetcodeUserName() {
		return leetcodeUserName;
	}
	public void setLeetcodeUserName(String leetcodeUserName) {
		this.leetcodeUserName = leetcodeUserName;
}

	public Student() {
		super();
	}

	public Student(long rollno, String name, short year, short semester, String department, String password, String email, long mobileno, String leetcodeUserName) {
		this.rollno = rollno;
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.department = department;
		this.password = password;
		this.email = email;
		this.mobileno = mobileno;
		this.leetcodeUserName = leetcodeUserName;
	}
}
