package com.placementserver.placementserver.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>{
	
	Faculty findByMobileno(long mobileno);

	@Modifying
	@Transactional
	@Query(value = "UPDATE faculty SET name = :name, " +
			"department = :department, email = :email " +
			"WHERE mobileno = :mobileno",
	nativeQuery = true)
	int updateFaculty(@Param("mobileno") long mobileno,
					  @Param("name") String name,
					  @Param("department") String department,
					  @Param("email") String email);

	@Query(value = "SELECT email FROM faculty WHERE email IS NOT NULL", nativeQuery = true)
	List<String> getAllFacultyEmails();
}
