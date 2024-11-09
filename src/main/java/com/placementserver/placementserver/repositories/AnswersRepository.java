package com.placementserver.placementserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Answers;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long>{
	
	@Query("SELECT a FROM Answers a WHERE a.student.rollno = :rollno AND a.questionid = :questionid")
    Answers findByRollnoAndQuestionid(@Param("rollno") long rollno, @Param("questionid") long questionid);

    @Query("SELECT a FROM Answers a WHERE a.student.rollno = :rollno")
    List<Answers> findByRollno(@Param("rollno") long rollno);
    
    @Query(value = "SELECT answer.markpercentage,answer.correct,answer.wrong,answer.questionid,"
    		+ " student.rollno,student.name,student.department,student.year,student.semester"
    		+ " FROM answer JOIN student ON answer.rollno = student.rollno"
    		+ " WHERE (:department = 'ALL' OR student.department = :department) AND"
    		+ " (:year = 0 OR student.year = :year) AND"
    		+ " (:questionid = 0 OR answer.questionid = :questionid)"
    		+ " ORDER BY answer.markpercentage DESC",
    		nativeQuery = true)
    List<Object[]> findFilteredAnswers(@Param("department") String department,
    								   @Param("year") short year,
    								   @Param("questionid") long questionid);
    
    @Query(value = "SELECT student.rollno,student.department,student.name,student.year,student.semester,"
    		+ " questiontitle.questionid,questiontitle.name"
    		+ " FROM student CROSS JOIN questiontitle"
    		+ " LEFT JOIN answer ON student.rollno = answer.rollno"
    		+ " AND questiontitle.questionid = answer.questionid"
    		+ " WHERE (answer.ansid IS NULL) AND"
    		+ " (:department = 'ALL' OR student.department = :department) AND"
    		+ " (:year = 0 OR student.year = :year) AND"
    		+ " (:questionid = 0 OR questiontitle.questionid = :questionid)",
    		nativeQuery=true)
	List<Object[]> findDefaultersAnswers(String department, short year, long questionid);
}
