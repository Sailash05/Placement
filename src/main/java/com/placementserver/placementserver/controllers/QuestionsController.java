package com.placementserver.placementserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.placementserver.placementserver.models.Questions;
import com.placementserver.placementserver.models.QuestionsTitle;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.services.QuestionsService;

@RestController
@RequestMapping("/questions")
public class QuestionsController {
	
	@Autowired
	private QuestionsService questionsService;
	
	@PostMapping("/addquestionsfile")
	public ResponseEntity<DataResponse<String>> addQuestionsFile(@RequestParam("name") String name,
																 @RequestParam("dateTime") String dateAndTime,
																 @RequestParam("questions") MultipartFile file) {
		
		DataResponse<String> response = questionsService.addQuestionsFile(name, dateAndTime, file);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(400));
		}
	}
	
	@GetMapping("/getquestions")
	public ResponseEntity<DataResponse<List<Questions>>> getQuestions(@RequestParam("questionid") long questionId) {
		
		DataResponse<List<Questions>> response = questionsService.getQuestions(questionId);
	    
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
	}
	
	@GetMapping("/getquestionstitle")
	public ResponseEntity<DataResponse<List<QuestionsTitle>>> getQuestionsTitle() {
		
		DataResponse<List<QuestionsTitle>> response = questionsService.getQuestionsTitle();
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
	}

	@DeleteMapping("/deleteassessment")
	public ResponseEntity<DataResponse<String>> deleteAssessment(@RequestParam("questionid") long questionid) {

		DataResponse<String> response = questionsService.deleteAssessment(questionid);

		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(200));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(404));
		}
	}
}