package com.placementserver.placementserver.controllers;

import java.util.List;

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
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.DefaulterList;
import com.placementserver.placementserver.responses.ReturnAnswer;
import com.placementserver.placementserver.responses.UnfinishedResponse;
import com.placementserver.placementserver.services.AnswersService;

@RestController
@RequestMapping("/answers")
public class AnswersController {
	
	@Autowired
	private AnswersService answersService;
	
	@PostMapping("/addmark")
	public ResponseEntity<DataResponse<String>> addMark(@RequestBody Answers answers) {
		 
		DataResponse<String> response = answersService.addMark(answers);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(400));
	}
	
	@GetMapping("/getmark")
	public ResponseEntity<DataResponse<List<ReturnAnswer>>> getMark(@RequestParam("rollno") long rollNo) {
		
		DataResponse<List<ReturnAnswer>> response = answersService.getMark(rollNo);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
	}
	
	@GetMapping("/getunfinished")
	public ResponseEntity<DataResponse<List<UnfinishedResponse>>> getUnfinished(@RequestParam("rollno") long rollno) {
		DataResponse<List<UnfinishedResponse>> response = answersService.getUnfinished(rollno);
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
		
	}
	
	@GetMapping("/getmarkfilter")
	public ResponseEntity<DataResponse<List<StudentAnswer>>> getMarkFilter(@RequestParam("dept") String department,
											 @RequestParam("year") short year,
											 @RequestParam("qid") long questionid,
											 @RequestParam("top") int top) {
		
		DataResponse<List<StudentAnswer>> response =  answersService.getMarkFilter(department, year, questionid, top);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
	}
	
	@GetMapping("/getdefaulters")
	public ResponseEntity<DataResponse<List<DefaulterList>>> getDefaulters(@RequestParam("dept") String department,
							 @RequestParam("year") short year,
							 @RequestParam("qid") long questionid) {
		
		DataResponse<List<DefaulterList>> response = answersService.getDefaulters(department, year, questionid);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
	}
}
