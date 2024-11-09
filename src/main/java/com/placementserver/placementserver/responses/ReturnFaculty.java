package com.placementserver.placementserver.responses;

import com.placementserver.placementserver.models.Faculty;

public class ReturnFaculty {
	
	private String name;
	private String department;
	private long mobileno;
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
	public ReturnFaculty(Faculty faculty) {
		super();
		this.name = faculty.getName();
		this.department = faculty.getDepartment();
		this.mobileno = faculty.getMobileno();
	}
	
}
