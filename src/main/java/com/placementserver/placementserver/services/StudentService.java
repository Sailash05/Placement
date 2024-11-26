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
import com.placementserver.placementserver.responses.ApiResponse;
import com.placementserver.placementserver.responses.StudentResponse;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public StudentResponse getStudent(long rollNo) {
		
		Student response = studentRepository.findByRollno(rollNo);
		
		if(response != null) {
			return new StudentResponse("Success", "Student found", response);
		}
		
		return new StudentResponse("Failed", "Student not found",new Student());
	}
	
	public ApiResponse addStudent(Student students) {		
		
		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		if((month >= 1) && (month <=6)) {
			students.setSemester((short) (students.getYear()*2));
		}
		else {
			students.setSemester((short) (students.getYear()*2 - 1));
		}
		
		if (studentRepository.existsById(students.getRollno())) {
	        return new ApiResponse("Failed", "Student already exist");
	    }
		
		students.setPassword(encoder.encode(students.getPassword()));
		
	    studentRepository.save(students);
	    
	    return new ApiResponse("Success", "Student added successfully");
	}
	
	public String loginStudent(Student students) {
		
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(students.getRollno()),students.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(String.valueOf(students.getRollno()));
		}
		else {
			return "failed";
		}		
	}
}
