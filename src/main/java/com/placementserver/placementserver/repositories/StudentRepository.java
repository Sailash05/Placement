package com.placementserver.placementserver.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	Student findByRollno(long rollno);

	@Modifying
	@Transactional
	@Query(value = "UPDATE student SET name = :name, year = :year, department = :department," +
			" email = :email, mobileno = :mobileno WHERE rollno = :rollno",
	nativeQuery = true)
	int updateStudent(@Param("rollno") long rollno,
					  @Param("name") String name,
					  @Param("year") short year,
					  @Param("department") String department,
					  @Param("email") String email,
					  @Param("mobileno") long mobileno);
}