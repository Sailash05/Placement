package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Student;
import com.placementserver.placementserver.models.StudentPrincipal;
import com.placementserver.placementserver.repositories.StudentRepository;

@Service
public class StudentUserDetailsService implements UserDetailsService{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String rollNumber) throws UsernameNotFoundException {
		long rollNo = Long.parseLong(rollNumber);
		Student response = studentRepository.findByRollno(rollNo);
		if(response == null) {
			//System.out.println("user Not found");
			throw new UsernameNotFoundException("user not found");
		}
		
		return new StudentPrincipal(response);
	}

}