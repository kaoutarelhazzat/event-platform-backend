package com.eventplatform.notification_service.repository;

import com.eventplatform.notification_service.entity.enums.NotificationStatus;
import com.eventplatform.notification_service.entity.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findAllByStatus(NotificationStatus status);
}
