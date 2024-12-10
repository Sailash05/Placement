package com.placementserver.placementserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.placementserver.placementserver.models.Student;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnStudent;
import com.placementserver.placementserver.services.StudentService;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/getstudent")
	public ResponseEntity<DataResponse<ReturnStudent>> getStudents(@RequestParam("rollno") long rollNo) {
		
		DataResponse<ReturnStudent> response = studentService.getStudent(rollNo);
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
		
	}
	
	@PostMapping("/addstudent")
	public ResponseEntity<DataResponse<String>> addStudent(@RequestBody Student student) {
		DataResponse<String> response = studentService.addStudent(student);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/loginstudent")
	public ResponseEntity<DataResponse<String>> loginStudent(@RequestBody Student student) {

		DataResponse<String> dataResponse = studentService.loginStudent(student);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}

	@PutMapping("/updatestudent")
	public ResponseEntity<DataResponse<String>> updateStudent(@RequestBody Student student) {
		DataResponse<String> dataResponse = studentService.updateStudent(student);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse,HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}

	@PutMapping("/resetrequest")
	public ResponseEntity<DataResponse<String>> resetRequest(@RequestBody Student student) {
		DataResponse<String> dataResponse = studentService.resetRequest(student);
		if(dataResponse.getCondition().equals("Success")) {
			return new ResponseEntity<>(dataResponse,HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(dataResponse, HttpStatus.valueOf(404));
		}
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<DataResponse<String>> resetPassword(@RequestBody Student student) {
		DataResponse<String> response = studentService.resetPassword(student);
		if(response.getCondition().equals("Success")) {
			return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response,HttpStatusCode.valueOf(403));
		}
	}
}
