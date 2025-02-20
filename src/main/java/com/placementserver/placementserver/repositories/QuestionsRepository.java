package com.placementserver.placementserver.repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Questions;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long>{
	
	List<Questions> findByQuestionid(Long quesitionid);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM question WHERE questionid = :questionid",
    nativeQuery = true)
    void deleteAssessment(@Param("questionid") long questionid);
}
