package com.placementserver.placementserver.services;

import com.placementserver.placementserver.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.placementserver.placementserver.models.Faculty;
import com.placementserver.placementserver.repositories.FacultyRepository;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.ReturnFaculty;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private EmailService emailService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Value("${GEMINI_API_KEY}")
	private String geminiApiKey;

	@Autowired
	private RestTemplate restTemplate;

	public DataResponse<ReturnFaculty> getFaculty(long mobileNo) {
		Faculty response = facultyRepository.findByMobileno(mobileNo);
		if (response != null) {
			return new DataResponse<>("Success", "Faculty found", new ReturnFaculty(response));
		}
		return new DataResponse<>("Failed", "Faculty not found", new ReturnFaculty(new Faculty()));
	}

	public DataResponse<String> addFaculty(Faculty faculty) {

		if (facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Faculty Already Exist", new String());
		}

		faculty.setPassword(encoder.encode(faculty.getPassword()));
		facultyRepository.save(faculty);

		return new DataResponse<>("Success", "Faculty Added Successfully", new String());
	}


	public DataResponse<String> loginFaculty(Faculty faculty) {
		String token = new String();
		if (!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Faculty Not Found", new String());
		}
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(String.valueOf(faculty.getMobileno()), faculty.getPassword()));

		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(String.valueOf(faculty.getMobileno()), "faculty", "login");
		} else {
			token = "failed";
		}
		return new DataResponse<>("Success", "Collect your JWT Token", token);
	}

	public DataResponse<String> updateFaculty(Faculty faculty) {
		if (!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Faculty Not Found", "");
		}
		int noOfRowsAffected = facultyRepository.updateFaculty(faculty.getMobileno(),
				faculty.getName(),
				faculty.getDepartment(),
				faculty.getEmail());
		if (noOfRowsAffected >= 1) {
			return new DataResponse<>("Success", "Faculty Details Updated", "");
		}
		return new DataResponse<>("Failed", "Wrong update", "");
	}

	public DataResponse<String> resetRequest(Faculty faculty) {
		if (!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Faculty Not Found", "");
		}
		Faculty response = facultyRepository.findByMobileno(faculty.getMobileno());
		if (response.getEmail() == null && faculty.getEmail() == null) {
			return new DataResponse<>("Failed", "Email not found", "");
		}
		String email = faculty.getEmail() == null ? response.getEmail() : faculty.getEmail();
		String name = facultyRepository.findByMobileno(faculty.getMobileno()).getName();
		String token = jwtService.generateResetToken(String.valueOf(faculty.getMobileno()), "faculty", "reset request");
		emailService.sendFacultyResetRequest(name, faculty.getMobileno(), email, token);
		return new DataResponse<>("Success", "Reset token is sent to your email", "");
	}

	public DataResponse<String> resetPassword(Faculty faculty) {
		if (!facultyRepository.existsById(faculty.getMobileno())) {
			return new DataResponse<>("Failed", "Student Not Found", "");
		}
		if (faculty.getPassword() == null || faculty.getPassword().isEmpty()) {
			return new DataResponse<>("Failed", "Password cannot be null or empty", "");
		}
		Faculty response = facultyRepository.findByMobileno(faculty.getMobileno());
		response.setPassword(encoder.encode(faculty.getPassword()));
		facultyRepository.save(response);
		return new DataResponse<>("Success", "Password changed", "");
	}

	public DataResponse<String> addAdmin(long mobileno, String adminCode) {
		if (!adminCode.equals("A5-W4XP-3EKU")) {
			return new DataResponse<>("Failed", "Wrong admin code", "");
		}
		int n = facultyRepository.addAdmin(mobileno);
		if (n == 1) {
			return new DataResponse<>("Success", "Admin added", "");
		}
		return new DataResponse<>("Failed", "Something wrong", "");
	}

	public DataResponse<String> removeAdmin(long mobileno) {
		int n = facultyRepository.removeAdmin(mobileno);
		if (n == 1) {
			return new DataResponse<>("Success", "Admin removed", "");
		} else {
			return new DataResponse<>("Failed", "Something wrong", "");
		}
	}


	public DataResponse<List<Map<String, Object>>> generateQuestions(String topic, long count) {
		String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String prompt = String.format("Generate %d multiple-choice questions with 4 options for the topic '%s'. " +
						"Include the question, 4 options (a, b, c, d), and specify the correct answer in JSON format.",
				count, topic);

		try {
			Map<String, Object> textPart = Map.of("text", prompt);
			Map<String, Object> part = Map.of("parts", List.of(textPart));
			Map<String, Object> content = Map.of("contents", List.of(part));
			Map<String, Object> requestBody = Map.of(
					"model", "models/gemini-2.0-flash",
					"contents", List.of(part)
			);

			ObjectMapper objectMapper = new ObjectMapper();
			String requestBodyJson = objectMapper.writeValueAsString(requestBody);

			HttpEntity<String> request = new HttpEntity<>(requestBodyJson, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					apiUrl,
					HttpMethod.POST,
					request,
					String.class
			);

			List<Map<String, Object>> mcqs = extractMCQs(response.getBody());

			System.out.println(response.getBody());

			return new DataResponse<>("Success","Get questions", mcqs);

			/*for (Map<String, Object> mcq : mcqs) {
				System.out.println("Question: " + mcq.get("question"));
				System.out.println("Options:");
				Map<String, String> options = (Map<String, String>) mcq.get("options");
				System.out.println("a) " + options.get("a"));
				System.out.println("b) " + options.get("b"));
				System.out.println("c) " + options.get("c"));
				System.out.println("d) " + options.get("d"));
				System.out.println("Correct Answer: " + mcq.get("answer"));
				System.out.println();
			}*/
		} catch (Exception e) {
			//e.printStackTrace();
			return new DataResponse<>("Failed", "Something went wrong", null);
		}
	}


	public List<Map<String, Object>> extractMCQs(String responseBody) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(responseBody);

		// Extract the text containing the JSON array of MCQs
		String mcqJsonString = rootNode.path("candidates")
				.path(0)
				.path("content")
				.path("parts")
				.path(0)
				.path("text")
				.asText();

		// Remove markdown code block indicators (```json and ```)
		mcqJsonString = mcqJsonString.replace("```json", "").replace("```", "").trim();

		// Parse the MCQ array
		JsonNode mcqArray = objectMapper.readTree(mcqJsonString);

		// Convert to List of Maps (question, options, answer)
		List<Map<String, Object>> mcqs = new ArrayList<>();
		for (JsonNode mcqNode : mcqArray) {
			Map<String, Object> mcq = new HashMap<>();
			mcq.put("question", mcqNode.path("question").asText());

			// Extract options (a, b, c, d)
			Map<String, String> options = new HashMap<>();
			JsonNode optionsNode = mcqNode.path("options");
			options.put("a", optionsNode.path("a").asText());
			options.put("b", optionsNode.path("b").asText());
			options.put("c", optionsNode.path("c").asText());
			options.put("d", optionsNode.path("d").asText());
			mcq.put("options", options);

			String correctAnswer = "";
			if (mcqNode.has("answer")) {
				correctAnswer = mcqNode.path("answer").asText();
			} else if (mcqNode.has("correct_answer")) {
				correctAnswer = mcqNode.path("correct_answer").asText();
			}
			mcq.put("answer", correctAnswer.isEmpty() ? "N/A" : correctAnswer);

			mcqs.add(mcq);
		}

		return mcqs;
	}
}