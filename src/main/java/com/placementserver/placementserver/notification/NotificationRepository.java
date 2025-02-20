package com.placementserver.placementserver.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    Notifications findByEndpoint(String endpoint);

    @Query(value = "SELECT * FROM notifications WHERE role = :role",
    nativeQuery = true)
    List<Notifications> getAllSubscription(@Param("role") String role);
}
