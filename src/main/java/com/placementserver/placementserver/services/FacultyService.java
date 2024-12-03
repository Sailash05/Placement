package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.repositories.FacultyRepository;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnFaculty;

@Service
public class FacultyService {
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public DataResponse<ReturnFaculty> getFaculty(long mobileNo) {
		
		Faculty response = facultyRepository.findByMobileno(mobileNo);
		
		if(response != null) {
			return new DataResponse<>("Success", "Faculty found", new ReturnFaculty(response));
		}
		
		return new DataResponse<>("Failed", "Faculty not found",new ReturnFaculty(new Faculty()));
	}
	
	public DataResponse<String> addFaculty(Faculty faculty) {
		
		if(facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Faculty already exist",new String());
		}
		
		faculty.setPassword(encoder.encode(faculty.getPassword()));
		facultyRepository.save(faculty);
		
		return new DataResponse<>("Success", "Faculty added successfully",new String());
	}
	
	
	public DataResponse<String> loginFaculty(Faculty faculty) {
		String token = new String();
		if(!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed","Faculty Not Found",new String());
		}
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(faculty.getMobileno()),faculty.getPassword()));
		
		if(authentication.isAuthenticated()) {
			token =  jwtService.generateToken(String.valueOf(faculty.getMobileno()));
		}
		else {
			token =  "failed";
		}
		return new DataResponse<>("Success","Collect your JWT Token",token);
	}
}
