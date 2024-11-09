package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.repositories.FacultyRepository;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.FacultyResponse;

@Service
public class FacultyService {
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public FacultyResponse getFaculty(long mobileNo) {
		
		Faculty response = facultyRepository.findByMobileno(mobileNo);
		
		if(response != null) {
			return new FacultyResponse("Success", "Faculty found", response);
		}
		
		return new FacultyResponse("Failed", "Faculty not found",new Faculty());
	}
	
	public ApiResponse addFaculty(Faculty faculty) {
		
		if(facultyRepository.existsById(faculty.getMobileno())) {
			return new ApiResponse("Failed", "Faculty already exist");
		}
		
		faculty.setPassword(encoder.encode(faculty.getPassword()));
		facultyRepository.save(faculty);
		
		return new ApiResponse("Success", "Faculty added successfully");
	}
	
	
	public String loginFaculty(Faculty faculty) {
		
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(faculty.getMobileno()),faculty.getPassword()));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(String.valueOf(faculty.getMobileno()));
		}
		else {
			return "failed";
		}
	}
}
