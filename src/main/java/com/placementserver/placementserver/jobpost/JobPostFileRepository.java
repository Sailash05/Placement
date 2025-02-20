package com.placementserver.placementserver.jobpost;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostFileRepository extends JpaRepository<JobPostFile, Long> {

    @Query(value = "SELECT filename FROM jobpost_file WHERE jobpostid = :id",
    nativeQuery = true)
    List<String> getFiles(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM jobpost_file WHERE jobpostid = :jobpostid",
            nativeQuery = true)
    int deleteJobPostFile(@Param("jobpostid") long jobpostid);

    @Query(value = "SELECT filename FROM jobpost_file WHERE jobpostid = :jobpostid",
    nativeQuery = true)
    List<String> getFileName(@Param("jobpostid") long jobpostid);
}
