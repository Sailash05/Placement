package com.placementserver.placementserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.QuestionsTitle;

@Repository
public interface QuestionsTitleRepository extends JpaRepository<QuestionsTitle, Long>{

	QuestionsTitle findByQuestionid(long questionid);
	
}
