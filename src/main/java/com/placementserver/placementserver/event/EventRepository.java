package com.placementserver.placementserver.event;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.DialectOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query(value = "SELECT COUNT(*) FROM event",
    nativeQuery = true)
    long getCount();

    @Query(value = "SELECT * FROM event ORDER BY eventid DESC LIMIT 10 OFFSET :value",
    nativeQuery = true)
    List<Event> getEvent(@Param("value") long value);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM event WHERE eventid = :eventid",
    nativeQuery = true)
    int deleteEvent(@Param("eventid") long eventid);
}
