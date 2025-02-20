package com.placementserver.placementserver.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.QuestionsTitle;

import java.util.List;

@Repository
public interface QuestionsTitleRepository extends JpaRepository<QuestionsTitle, Long>{

	QuestionsTitle findByQuestionid(long questionid);

    @Query(value = "SELECT * FROM questiontitle ORDER BY questionid DESC LIMIT 50",
    nativeQuery = true)
    List<QuestionsTitle> getAllQuestionTitle();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM questiontitle WHERE questionid = :questionid", nativeQuery = true)
    void deleteAssessment(@Param("questionid") long questionid);
}
