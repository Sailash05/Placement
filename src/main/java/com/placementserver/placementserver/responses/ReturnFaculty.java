package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Faculty;

public class ReturnFaculty {
	
	private String name;
	private String department;
	private long mobileno;
	private String email;
	private String role;

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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public ReturnFaculty(Faculty faculty) {
		super();
		this.name = faculty.getName();
		this.department = faculty.getDepartment();
		this.mobileno = faculty.getMobileno();
		this.email = faculty.getEmail();
		this.role = faculty.getRole();
	}
	
}
