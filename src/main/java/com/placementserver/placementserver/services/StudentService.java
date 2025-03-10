package com.placementserver.placementserver.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

	@Autowired
	private EmailService emailService;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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
		if(month <=6) {
			students.setSemester((short) (students.getYear()*2));
		}
		else {
			students.setSemester((short) (students.getYear()*2 - 1));
		}
		
		if (studentRepository.existsById(students.getRollno())) {
	        return new DataResponse<>("Failed", "Student Already Exist", "");
	    }
		
		students.setPassword(encoder.encode(students.getPassword()));
		
	    studentRepository.save(students);
	    
	    return new DataResponse<>("Success", "Student Added Successfully", "");
	}

	public DataResponse<String> loginStudent(Student students) {
		String token;
		if(!studentRepository.existsById(students.getRollno())) {
			return new DataResponse<>("Failed","Student Not Found", "");
		}
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(students.getRollno()),students.getPassword()));
		if(authentication.isAuthenticated()) {
			token =  jwtService.generateToken(String.valueOf(students.getRollno()), "student","login");
		}
		else {
			token =  "failed";
		}		
		return new DataResponse<>("Success","Collect your JWT Token",token);
	}

	public DataResponse<String> updateStudent(Student student) {
		if(!studentRepository.existsById(student.getRollno())) {
			return new DataResponse<>("Failed","Student Not Found", "");
		}
		int noOfRowsAffected = studentRepository.updateStudent(student.getRollno(),
				student.getName(),
				student.getYear(),
				student.getDepartment(),
				student.getEmail(),
				student.getMobileno());
		if(noOfRowsAffected >= 1) {
			return new DataResponse<>("Success","Student Details Updated","");
		}
		return new DataResponse<>("Failed","Wrong update","");
	}

	public DataResponse<String> resetRequest(Student student) {
		if(!studentRepository.existsById(student.getRollno())) {
			return new DataResponse<>("Failed","Student Not Found", "");
		}
		Student response = studentRepository.findByRollno(student.getRollno());
		if(response.getEmail() == null && student.getEmail() == null) {
			return new DataResponse<>("Failed","Email not found","");
		}
		String email = response.getEmail() == null ? student.getEmail() : response.getEmail();
		String name = studentRepository.findByRollno(student.getRollno()).getName();
		String token =  jwtService.generateResetToken(String.valueOf(student.getRollno()),"student","reset request");
		emailService.sendResetRequest(name,student.getRollno(),email,token);
		return new DataResponse<>("Success","Reset token is sent to your email","");
	}

	public DataResponse<String> resetPassword(Student student) {
		if(!studentRepository.existsById(student.getRollno())) {
			return new DataResponse<>("Failed","Student Not Found","");
		}
		if (student.getPassword() == null || student.getPassword().isEmpty()) {
			return new DataResponse<>("Failed", "Password cannot be null or empty", "");
		}
		Student response = studentRepository.findByRollno(student.getRollno());
		response.setPassword(encoder.encode(student.getPassword()));
		studentRepository.save(response);
		return new DataResponse<>("Success","Your Password has been Changed","");
	}

	@Scheduled(cron = "0 0 0 1 6 *")
	public void oddSem() {
		studentRepository.increaseSemester();
		studentRepository.increaseYear();
	}

	@Scheduled(cron = "0 0 0 1 1 *")
	public void evenSem() {
		studentRepository.increaseSemester();
	}
}