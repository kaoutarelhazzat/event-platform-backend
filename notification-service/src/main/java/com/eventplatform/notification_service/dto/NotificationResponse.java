package com.eventplatform.notification_service.dto;

import com.eventplatform.notification_service.entity.enums.NotificationStatus;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String message;
    private NotificationStatus status;
    private LocalDateTime sentAt;

    public NotificationResponse(Long id, String message,
                                NotificationStatus status,
                                LocalDateTime sentAt) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
