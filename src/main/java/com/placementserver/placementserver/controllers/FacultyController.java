package com.placementserver.placementserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.FacultyResponse;
import com.placementserver.placementserver.responses.TokenResponse;
import com.placementserver.placementserver.services.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/getfaculty")
	public ResponseEntity<FacultyResponse> getFaculty(@RequestParam("mobileno") long mobileno) {
		
		FacultyResponse response = facultyService.getFaculty(mobileno);
				
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/addfaculty")
	public ResponseEntity<ApiResponse> addFaculty(@RequestBody Faculty faculty) {

		ApiResponse response = facultyService.addFaculty(faculty);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/loginfaculty")
	public ResponseEntity<TokenResponse> loginFaculty(@RequestBody Faculty faculty) { //ResponseEntity<ApiResponse>
		
		String token = facultyService.loginFaculty(faculty);
		TokenResponse tokenResponse = new TokenResponse("Success","Collect your JWT Token",token);
		
		return new ResponseEntity<>(tokenResponse, HttpStatus.valueOf(201));
	}
}
