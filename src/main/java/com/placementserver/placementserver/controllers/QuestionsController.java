package com.placementserver.placementserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.placementserver.placementserver.models.Questions;
import com.placementserver.placementserver.models.QuestionsTitle;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.Response;
import com.placementserver.placementserver.services.QuestionsService;

@RestController
@RequestMapping("/questions")
public class QuestionsController {
	
	@Autowired
	private QuestionsService questionsService;
	
	@PostMapping("/addquestionsfile")
	public ResponseEntity<ApiResponse> addQuestionsFile(@RequestParam("name") String name, @RequestParam("questions") MultipartFile file) {
		
		ApiResponse response = questionsService.addQuestionsFile(name, file);
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		
		return new ResponseEntity<>(response, HttpStatus.valueOf(409));
	}
	
	
	@GetMapping("/getquestions")
	public ResponseEntity<Response<Questions>> getQuestions(@RequestParam("questionid") long questionId) {
		
		Response<Questions> response = questionsService.getQuestions(questionId);
	    
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
	}
	
	@GetMapping("/getquestionstitle")
	public ResponseEntity<Response<QuestionsTitle>> getQuestionsTitle() {
		
		Response<QuestionsTitle> response = questionsService.getQuestionsTitle();
		
		if("Success".equals(response.getCondition())) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(201));
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.valueOf(409));
		}
	}
}