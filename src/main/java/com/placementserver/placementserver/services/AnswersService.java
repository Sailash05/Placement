package com.placementserver.placementserver.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Answers;
import com.placementserver.placementserver.models.QuestionsTitle;
import com.placementserver.placementserver.models.Student;
import com.placementserver.placementserver.models.StudentAnswer;
import com.placementserver.placementserver.repositories.AnswersRepository;
import com.placementserver.placementserver.repositories.QuestionsTitleRepository;
import com.placementserver.placementserver.repositories.StudentRepository;
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.DefaulterList;
import com.placementserver.placementserver.responses.Response;
import com.placementserver.placementserver.responses.ReturnAnswer;
import com.placementserver.placementserver.responses.UnfinishedResponse;

@Service
public class AnswersService {
	
	@Autowired
	private AnswersRepository answersRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private QuestionsTitleRepository questionsTitleRepository;
	
	public ApiResponse addMark(Answers answers) {

		Student student = studentRepository.findByRollno(answers.getStudent().getRollno());
			
		if(student == null) {
			return new ApiResponse("Failed", "Student not found");
		}
		
		answers.setStudent(student);
		
		Answers response = answersRepository.findByRollnoAndQuestionid(answers.getStudent().getRollno(), answers.getQuestionid());
		
		if(response != null) {
			return new ApiResponse("Failed","Already summited the answer for this question id");
		}
		
		QuestionsTitle questionsTitle = questionsTitleRepository.findByQuestionid(answers.getQuestionid());
		
		if(questionsTitle == null) {
			return new ApiResponse("Failed","The question id is not matched");
		}
		
		answersRepository.save(answers);
		return new ApiResponse("Success","Answers was summitted");
	}
	
	public Response<ReturnAnswer> getMark(long rollno) {
		
		List<Object[]> response = answersRepository.findByRollno(rollno);
		
		List<ReturnAnswer> ansResponse = new ArrayList<>();
		
		for(Object[] answers:response) {
			ReturnAnswer returnAnswer = new ReturnAnswer(
					((Number) answers[0]).longValue(),
					((Number) answers[1]).floatValue(),
					((Number) answers[2]).intValue(),
					((Number) answers[3]).intValue(),
					(String) answers[4]);
			ansResponse.add(returnAnswer);
		}
		
		
		
//		if(ansResponse.isEmpty()) {
//			return new Response<ReturnAnswer>("Failed","No Marks Available",ansResponse);
//		}
		
		return new Response<ReturnAnswer>("Success","Marks Obtained",ansResponse);
	}

	public Response<StudentAnswer> getMarkFilter(String department, short year, long questionid, int top) {
		
		List<Object[]> response = answersRepository.findFilteredAnswers(department, year, questionid);
		
		List<StudentAnswer> studentAnswers = new ArrayList<>();
		
		int count = 0;
		
		for (Object[] result : response) {
	        StudentAnswer studentAnswer = new StudentAnswer(
	            ((Number) result[0]).floatValue(),  	   // markPercentage
	            ((Number) result[1]).intValue(),    	  // correct
	            ((Number) result[2]).intValue(),    	 // wrong
	            ((Number) result[3]).longValue(),    	//questionid
	            ((Number) result[4]).longValue(),      // rollno
	            (String) result[5],                   // name
	            (String) result[6],                  // department
	            ((Number) result[7]).shortValue(),  // year
	            ((Number) result[8]).shortValue()  // semester
	        );
	        studentAnswers.add(studentAnswer);
	        
	        if(++count == top)
	        	break;
	    }
		return new Response<StudentAnswer>("Success","Received the marks of students list",studentAnswers);
	}

	public Response<DefaulterList> getDefaulters(String department, short year, long questionid) {
		
		List<Object[]> response = answersRepository.findDefaultersAnswers(department, year, questionid);
		
		List<DefaulterList> defaultersList = new ArrayList<>();
	
		
		for (Object[] result : response) {
			DefaulterList defaulterList = new DefaulterList(
	            ((Number) result[0]).longValue(),        // rollno
	            ((String) result[1]),    			    // department
	            ((String) result[2]),    			   // name
	            ((Number) result[3]).shortValue(),	  // year
	            ((Number) result[4]).shortValue(),   // semester
	            ((Number) result[5]).longValue(),	// name
	            (String) result[6] 				   // question name                
	        );
			defaultersList.add(defaulterList);
	    }
		return new Response<DefaulterList>("Success","The list of defaulters", defaultersList);
	}

	public Response<UnfinishedResponse> getUnfinished(long rollno) {
		List<Object[]> response =  answersRepository.findUnfinished(rollno);
		
		List<UnfinishedResponse> result = new ArrayList<>();
		
		if(response.size() == 0) {
			return new Response<UnfinishedResponse>("Success","All questions are answered",result);
		}
		
		for(Object[] obj: response) {
			UnfinishedResponse ufa = new UnfinishedResponse(((Number) obj[0]).longValue(),(String)obj[1],(String)obj[2]);
			result.add(ufa);
		}
		
		return new Response<UnfinishedResponse>("Success","Get the unfinished assessment list",result);
	}
}
