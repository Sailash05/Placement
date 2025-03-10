package com.placementserver.placementserver.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.placementserver.placementserver.Schedule.DynamicTaskSchedulerService;
import com.placementserver.placementserver.models.questionrequest.QuestionRequestDTO;
import com.placementserver.placementserver.notification.NotificationService;
import com.placementserver.placementserver.repositories.AnswersRepository;
import com.placementserver.placementserver.repositories.FacultyRepository;
import com.placementserver.placementserver.responses.DefaulterList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;


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

	@Autowired
	private DynamicTaskSchedulerService schedulerService;

	@Autowired
	private AnswersRepository answersRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private NotificationService notificationService;
	
	public DataResponse<String> addQuestionsFile(String name, String dateAndTime, MultipartFile file) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, formatter);
		
		QuestionsTitle questionsTitle = new QuestionsTitle(name, dateAndTime, file.getOriginalFilename());
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

			String taskid = "Assessment_"+questionsTitle.getQuestionid();
			Runnable taskLogic = () -> sendDefaultersFile(questionsTitle.getQuestionid(), questionsTitle.getName());
			schedulerService.scheduleTask(taskid, dateTime, taskLogic);

			notificationService.sendNotification("New Assessment!", questionsTitle.getName()+" is added","STUDENT");
	        return new DataResponse<>("Success","Questions added",new String());
		}
		catch (IOException e) {
            return new DataResponse<>("Failed","Failed to Add",new String());
        }
	}

	public DataResponse<String> addQuestion(QuestionRequestDTO questionRequestDTO) {

		QuestionsTitle questionsTitle = new QuestionsTitle(questionRequestDTO.getName(),
				questionRequestDTO.getDateTime(),
				"N/A");

		QuestionsTitle savedQuestionTitle = questionsTitleRepository.save(questionsTitle);

		List<Questions> questions = questionRequestDTO.getQuestions();

		for(Questions question: questions) {
			question.setQuestionid(savedQuestionTitle.getQuestionid());
			questionsRepository.save(question);
		}

		return new DataResponse<>("Success","Questions Added","");
	}

	public DataResponse<List<Questions>> getQuestions(long questionNo) {
		
		List<Questions> response = questionsRepository.findByQuestionid(questionNo);
		
		if(response.size() == 0) {
			return new DataResponse<List<Questions>>("Failed","Wrong Question Id",response);
		}
		
		return new DataResponse<List<Questions>>("Success","Questions Received",response);
		
	}
	
	public DataResponse<List<QuestionsTitle>> getQuestionsTitle() {
		
		List<QuestionsTitle> response = questionsTitleRepository.getAllQuestionTitle();
		
		if(response.size() == 0) {
			return new DataResponse<List<QuestionsTitle>>("Failed","No Questions title available",response);
		}
		
		return new DataResponse<List<QuestionsTitle>>("Success","Questions tile received",response);
	}


	private void sendDefaultersFile(long questionid, String name) {

		List<Object[]> response = answersRepository.findDefaultersAnswers("ALL", (short)0, questionid);

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

		byte[] excelFile = generateExcelFile(defaultersList);
		List<String> emails = facultyRepository.getAllFacultyEmails();
		emailService.sendDefaulters(emails, name, excelFile);
		//sendEmailWithAttachment(excelFile, "Defaulters_List.xlsx");
		notificationService.sendNotification("Defaulters List","The defaulters list has been sent to your email.","FACULTY");
	}

	private byte[] generateExcelFile(List<DefaulterList> defaultersList) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Defaulters");

			// Creating header row
			Row headerRow = sheet.createRow(0);
			String[] headers = {"Roll No", "Department", "Name", "Year", "Semester", "Question ID", "Question Name"};

			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				CellStyle style = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				style.setFont(font);
				cell.setCellStyle(style);
			}

			// Filling data rows
			int rowNum = 1;
			for (DefaulterList defaulter : defaultersList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(defaulter.getRollno());
				row.createCell(1).setCellValue(defaulter.getDepartment());
				row.createCell(2).setCellValue(defaulter.getName());
				row.createCell(3).setCellValue(defaulter.getYear());
				row.createCell(4).setCellValue(defaulter.getSemester());
				row.createCell(5).setCellValue(defaulter.getQuestionId());
				row.createCell(6).setCellValue(defaulter.getQuestionName());
			}

			// Auto-size columns
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}

			// Writing to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return outputStream.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


    public DataResponse<String> deleteAssessment(long questionid) {
		questionsTitleRepository.deleteAssessment(questionid);
		questionsRepository.deleteAssessment(questionid);
		answersRepository.deleteAssessment(questionid);
		return new DataResponse<>("Success","Assessment Deleted","");
	}
}
