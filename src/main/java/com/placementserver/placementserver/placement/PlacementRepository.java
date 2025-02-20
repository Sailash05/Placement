package com.placementserver.placementserver.placement;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

    @Query(value = "SELECT * FROM placement WHERE passed_out_year= :year",
    nativeQuery = true)
    List<Placement> getPlacedStudents(short year);

    @Modifying
    @Transactional
    @Query(value = "UPDATE placement SET name= :name, department= :department," +
            "passed_out_year= :passedOutYear, company_name= :companyName, " +
            "lpa= :lpa WHERE rollno= :rollno",
    nativeQuery = true)
    int updatePlacedStudent(long rollno, String name, String department, short passedOutYear, String companyName, float lpa);
}
