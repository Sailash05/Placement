package com.placementserver.placementserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="faculty")
public class Faculty {
	
	@Id
	@Column(name="mobileno")
	private long mobileno;
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="department")
	private String department;
	@Column(name="email")
	private String email;
	
	public long getMobileno() {
		return mobileno;
	}
	public void setMobileNo(long mobileno) {
		this.mobileno = mobileno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Faculty() {
		super();
	}

	public Faculty(String email, String department, String password, String name, long mobileno) {
		this.email = email;
		this.department = department;
		this.password = password;
		this.name = name;
		this.mobileno = mobileno;
	}
}
