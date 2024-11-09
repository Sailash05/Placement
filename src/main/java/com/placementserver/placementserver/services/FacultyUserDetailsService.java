package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.models.FacultyPrincipal;
import com.placementserver.placementserver.repositories.FacultyRepository;

@Service
public class FacultyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private FacultyRepository facultyRepository;

	@Override
	public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
		long mobileNo = Long.parseLong(mobileNumber);
		Faculty response = facultyRepository.findByMobileno(mobileNo);
		if(response == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new FacultyPrincipal(response);
	}
	
	
}