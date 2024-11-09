package com.placementserver.placementserver.models;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class FacultyPrincipal implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private Faculty faculty;

	public FacultyPrincipal(Faculty faculty) {
		super();
		this.faculty = faculty;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singleton(new SimpleGrantedAuthority("FACULTY"));
	}

	@Override
	public String getPassword() {
		return faculty.getPassword();
	}

	@Override
	public String getUsername() {
		return String.valueOf(faculty.getMobileno());
	}
	
	
}
