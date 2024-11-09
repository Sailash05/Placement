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

import com.placementserver.placementserver.models.Student;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.StudentResponse;
import com.placementserver.placementserver.responses.TokenResponse;
import com.placementserver.placementserver.services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/getstudent")
	public ResponseEntity<StudentResponse> getStudents(@RequestParam("rollno") long rollNo) {
		
		StudentResponse response = studentService.getStudent(rollNo);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/addstudent")
	public ResponseEntity<ApiResponse> addStudent(@RequestBody Student student) {
		
		ApiResponse response = studentService.addStudent(student);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@PostMapping("/loginstudent")
	public ResponseEntity<TokenResponse> loginStudent(@RequestBody Student student) {
		
		String token = studentService.loginStudent(student);
		TokenResponse tokenResponse = new TokenResponse("Success","Collect your JWT Token",token);
		
		return new ResponseEntity<>(tokenResponse, HttpStatus.valueOf(201));
	}
}
