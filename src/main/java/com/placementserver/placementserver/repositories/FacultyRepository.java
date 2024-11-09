package com.placementserver.placementserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>{
	
	Faculty findByMobileno(long mobileno);
	
}
