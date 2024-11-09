package com.placementserver.placementserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placementserver.placementserver.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	Student findByRollno(long rollno);
	Student findByRollnoAndPassword(long rollno, String password);

}