package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	 @Autowired
    private FacultyUserDetailsService facultyUserDetailsService;

    @Autowired
    private StudentUserDetailsService studentUserDetailsService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Logic to decide if the username is for faculty or student
        if (username.matches("\\d{10}")) { // Assuming mobile number for faculty is 10 digits
            return facultyUserDetailsService.loadUserByUsername(username);  // Faculty
        } else {
            return studentUserDetailsService.loadUserByUsername(username);  // Student
        }
	}

}
