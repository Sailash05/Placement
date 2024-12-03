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
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnFaculty;
import com.placementserver.placementserver.services.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/getfaculty")
	public ResponseEntity<DataResponse<ReturnFaculty>> getFaculty(@RequestParam("mobileno") long mobileno) {
		
		DataResponse<ReturnFaculty> response = facultyService.getFaculty(mobileno);
				
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
		
		
	}
	
	@PostMapping("/addfaculty")
	public ResponseEntity<DataResponse<String>> addFaculty(@RequestBody Faculty faculty) {

		DataResponse<String> response = facultyService.addFaculty(faculty);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/loginfaculty")
	public ResponseEntity<DataResponse<String>> loginFaculty(@RequestBody Faculty faculty) {
		DataResponse<String> dataResponse = facultyService.loginFaculty(faculty);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}
}
