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
	
	public Faculty() {
		super();
	}
	
	public Faculty(long mobileno, String name, String password, String department) {
		super();
		this.mobileno = mobileno;
		this.name = name;
		this.password = password;
		this.department = department;
	}
	
}
