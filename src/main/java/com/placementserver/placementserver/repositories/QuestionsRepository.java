package com.placementserver.placementserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Questions;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long>{
	
	List<Questions> findByQuestionid(Long quesitionid);
}
