package com.placementserver.placementserver.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Student;
import com.placementserver.placementserver.repositories.StudentRepository;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnStudent;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public DataResponse<ReturnStudent> getStudent(long rollNo) {
		
		Student response = studentRepository.findByRollno(rollNo);
		
		if(response != null) {
			return new DataResponse<>("Success","Student Found",new ReturnStudent(response));
		}
		return new DataResponse<>("Failed","Student not found",new ReturnStudent(new Student()));
	}
	
	public DataResponse<String> addStudent(Student students) {		
		
		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		if((month >= 1) && (month <=6)) {
			students.setSemester((short) (students.getYear()*2));
		}
		else {
			students.setSemester((short) (students.getYear()*2 - 1));
		}
		
		if (studentRepository.existsById(students.getRollno())) {
	        return new DataResponse<>("Failed", "Student already exist",new String());
	    }
		
		students.setPassword(encoder.encode(students.getPassword()));
		
	    studentRepository.save(students);
	    
	    return new DataResponse<>("Success", "Student added successfully",new String());
	}
	
	public DataResponse<String> loginStudent(Student students) {
		String token = new String();
		if(!studentRepository.existsById(students.getRollno())) {
			return new DataResponse<>("Failed","Student Not Found",new String());
		}
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(students.getRollno()),students.getPassword()));
		if(authentication.isAuthenticated()) {
			token =  jwtService.generateToken(String.valueOf(students.getRollno()));
		}
		else {
			token =  "failed";
		}		
		return new DataResponse<>("Success","Collect your JWT Token",token);
	}
}
