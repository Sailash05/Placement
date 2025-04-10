package com.placementserver.placementserver.controllers;

import com.placementserver.placementserver.models.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnFaculty;
import com.placementserver.placementserver.services.FacultyService;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@PutMapping("/updatefaculty")
	public ResponseEntity<DataResponse<String>> updateFaculty(@RequestBody Faculty faculty) {
		DataResponse<String> dataResponse = facultyService.updateFaculty(faculty);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}

	@PutMapping("/resetrequest")
	public ResponseEntity<DataResponse<String>> resetRequest(@RequestBody Faculty faculty) {
		DataResponse<String> dataResponse = facultyService.resetRequest(faculty);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse,HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<DataResponse<String>> resetPassword(@RequestBody Faculty faculty) {
		DataResponse<String> response = facultyService.resetPassword(faculty);
		if(response.getCondition().equals("Success")) {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response,HttpStatusCode.valueOf(403));
		}
	}

	@PutMapping("/addadmin")
	public ResponseEntity<DataResponse<String>> addAdmin(@RequestParam("mobileno") long mobileno,
														 @RequestParam("admin-code") String adminCode) {
		DataResponse<String> response = facultyService.addAdmin(mobileno, adminCode);
		if(response.getCondition().equals("Success")) {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(202));
		}
		else {
			return new ResponseEntity<>(response,HttpStatusCode.valueOf(403));
		}
	}

	@PutMapping("/removeadmin")
	public ResponseEntity<DataResponse<String>> removeAdmin(@RequestParam("mobileno") long mobileno) {

		DataResponse<String> response = facultyService.removeAdmin(mobileno);
		if(response.getCondition().equals("Success")) {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(202));
		}
		else {
			return new ResponseEntity<>(response,HttpStatusCode.valueOf(403));
		}
	}

	@GetMapping("/autogenerate")
	public ResponseEntity<DataResponse<List<Map<String, Object>>>> generateQuestions(@RequestParam("topic") String topic,
																		   @RequestParam("count") long count) {

		DataResponse<List<Map<String, Object>>> response = facultyService.generateQuestions(topic, count);
		if(response.getCondition().equals("Success")) {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
		}
	}
}
