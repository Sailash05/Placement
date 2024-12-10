package com.placementserver.placementserver.services;

import com.placementserver.placementserver.models.Student;
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

	@Autowired
	private EmailService emailService;
	
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
			token =  jwtService.generateToken(String.valueOf(faculty.getMobileno()),"faculty","login");
		}
		else {
			token =  "failed";
		}
		return new DataResponse<>("Success","Collect your JWT Token",token);
	}

    public DataResponse<String> updateFaculty(Faculty faculty) {
		if(!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed","Faculty not found","");
		}
		int noOfRowsAffected = facultyRepository.updateFaculty(faculty.getMobileno(),
				faculty.getName(),
				faculty.getDepartment(),
				faculty.getEmail());
		if(noOfRowsAffected >= 1) {
			return new DataResponse<>("Success","Faculty details updated","");
		}
		return new DataResponse<>("Failed","Wrong update","");
    }

	public DataResponse<String> resetRequest(Faculty faculty) {
		if(!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed","Student Not Found", "");
		}
		Faculty response = facultyRepository.findByMobileno(faculty.getMobileno());
		if(response.getEmail() == null && faculty.getEmail() == null) {
			return new DataResponse<>("Failed","Email not found","");
		}
		String email = faculty.getEmail() == null ? response.getEmail() : faculty.getEmail();
		String name = facultyRepository.findByMobileno(faculty.getMobileno()).getName();
		String token =  jwtService.generateResetToken(String.valueOf(faculty.getMobileno()),"faculty","reset request");
		emailService.sendResetRequest(name,faculty.getMobileno(),email,token);
		return new DataResponse<>("Success","Reset token is sent to your email","");
	}

	public DataResponse<String> resetPassword(Faculty faculty) {
		if(!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed","Student Not Found","");
		}
		if (faculty.getPassword() == null || faculty.getPassword().isEmpty()) {
			return new DataResponse<>("Failed", "Password cannot be null or empty", "");
		}
		Faculty response = facultyRepository.findByMobileno(faculty.getMobileno());
		response.setPassword(encoder.encode(faculty.getPassword()));
		facultyRepository.save(response);
		return new DataResponse<>("Success","Password changed","");
	}
}
