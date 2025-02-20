package com.placementserver.placementserver.event;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFileRepository extends JpaRepository<EventFile, Long> {

    @Query(value ="SELECT filename FROM event_file WHERE eventid = :id",
    nativeQuery = true)
    List<String> getFiles(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM event_file WHERE eventid = :eventid",
    nativeQuery = true)
    int deleteEventFile(@Param("eventid") long eventid);

    @Query(value = "SELECT filename FROM event_file WHERE eventid = :eventid",
    nativeQuery = true)
    List<String> getFileName(@Param("eventid") long eventid);
}
