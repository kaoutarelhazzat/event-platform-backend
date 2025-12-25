package com.eventplatform.notification_service.service;

import com.eventplatform.notification_service.dto.NotificationRequest;
import com.eventplatform.notification_service.entity.enums.NotificationChannel;
import com.eventplatform.notification_service.entity.enums.NotificationStatus;
import com.eventplatform.notification_service.entity.model.Notification;
import com.eventplatform.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());

        // channel String -> enum
        NotificationChannel channel = NotificationChannel.valueOf(request.getChannel());
        notification.setChannel(channel);

        // On simule "envoyé" directement (logique simple étape 7)
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(OffsetDateTime.now());

        return notificationRepository.save(notification);
    }

    public List<Notification> getByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
