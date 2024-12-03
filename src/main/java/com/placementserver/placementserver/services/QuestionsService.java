package com.placementserver.placementserver.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.placementserver.placementserver.models.Questions;
import com.placementserver.placementserver.models.QuestionsTitle;
import com.placementserver.placementserver.repositories.QuestionsRepository;
import com.placementserver.placementserver.repositories.QuestionsTitleRepository;
import com.placementserver.placementserver.responses.DataResponse;

@Service
public class QuestionsService {
	
	@Autowired
	private QuestionsTitleRepository questionsTitleRepository;
	
	@Autowired
	private QuestionsRepository questionsRepository;
	
	public DataResponse<String> addQuestionsFile(String name, MultipartFile file) {
		
		QuestionsTitle questionsTitle = new QuestionsTitle(name, file.getOriginalFilename());
		questionsTitleRepository.save(questionsTitle);
		
		long questionId = questionsTitle.getQuestionid();
		String[] rowValue = new String[7];
		
		try (InputStream inputStream = file.getInputStream();
	             Workbook workbook = new XSSFWorkbook(inputStream)) {
			
			Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	        	int i=0;
	        	String temp = null;
	        	for (Cell cell : row) {
	        		 switch (cell.getCellType()) {
	                 case NUMERIC:
	                	 temp = String.valueOf(cell.getNumericCellValue());
	                     break;
	                 case STRING:
	                	 temp = cell.getStringCellValue();
	                     break;
	                 case BOOLEAN:
	                	 temp = String.valueOf(cell.getBooleanCellValue());
	                     break;
	                 case FORMULA:
	                	 temp = cell.getCellFormula();
	                     break;
	                 case BLANK:
	                	 temp = null;
	                 default:
	                     temp = null;
	             }
	        		
	        	rowValue[i++] = temp;		
	        	}
	        	if("FILLUP".equals(rowValue[2])) {
	        		Questions questions = new Questions(questionId,rowValue[0],rowValue[1],rowValue[2]);
	        		questionsRepository.save(questions);
	        	}
	        	else {
	        		Questions questions = new Questions(questionId,rowValue[0],rowValue[1],rowValue[2],rowValue[3],rowValue[4],rowValue[5],rowValue[6]);
	        		questionsRepository.save(questions);

	        	}
	        	
	        }
	        
	        return new DataResponse<>("Success","Questions added",new String());
	        
		}
		catch (IOException e) {
            return new DataResponse<>("Failed","Failed to add",new String());
        }
	}
	
	
	
	public DataResponse<List<Questions>> getQuestions(long questionNo) {
		
		List<Questions> response = questionsRepository.findByQuestionid(questionNo);
		
		if(response.size() == 0) {
			return new DataResponse<List<Questions>>("Failed","Wrong Question Id",response);
		}
		
		return new DataResponse<List<Questions>>("Success","Questions Received",response);
		
	}
	
	public DataResponse<List<QuestionsTitle>> getQuestionsTitle() {
		
		List<QuestionsTitle> response = questionsTitleRepository.findAll();
		
		if(response.size() == 0) {
			return new DataResponse<List<QuestionsTitle>>("Failed","No Questions title available",response);
		}
		
		return new DataResponse<List<QuestionsTitle>>("Success","Questions tile received",response);
	}
	
	
	
}
