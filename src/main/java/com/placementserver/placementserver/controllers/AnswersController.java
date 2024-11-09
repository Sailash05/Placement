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

import com.placementserver.placementserver.models.Answers;
import com.placementserver.placementserver.models.StudentAnswer;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.DefaulterList;
import com.placementserver.placementserver.responses.Response;
import com.placementserver.placementserver.responses.ReturnAnswer;
import com.placementserver.placementserver.services.AnswersService;

@RestController
@RequestMapping("/answers")
public class AnswersController {
	
	@Autowired
	private AnswersService answersService;
	
	@PostMapping("/addmark")
	public ResponseEntity<ApiResponse> addMark(@RequestBody Answers answers) {
		 
		ApiResponse response = answersService.addMark(answers);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	@GetMapping("/getmark")
	public ResponseEntity<Response<ReturnAnswer>> getMark(@RequestParam("rollno") long rollNo) {
		
		Response<ReturnAnswer> response = answersService.getMark(rollNo);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
	}
	
	@GetMapping("/getmarkfilter")
	public ResponseEntity<Response<StudentAnswer>> getMarkFilter(@RequestParam("dept") String department,
											 @RequestParam("year") short year,
											 @RequestParam("qid") long questionid,
											 @RequestParam("top") int top) {
		
		Response<StudentAnswer> response =  answersService.getMarkFilter(department, year, questionid, top);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
	}
	
	@GetMapping("/getdefaulters")
	public ResponseEntity<Response<DefaulterList>> getDefaulters(@RequestParam("dept") String department,
							 @RequestParam("year") short year,
							 @RequestParam("qid") long questionid) {
		
		Response<DefaulterList> response = answersService.getDefaulters(department, year, questionid);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
		
	}
}
