package com.eventplatform.payment_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {

    @NotNull
    private Long userId;

    // Optionnel pour notification-service (ignoré côté réception)
    private String referenceId;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String channel;

    public NotificationRequest() {}

    public NotificationRequest(Long userId,
                               String referenceId,
                               String title,
                               String message,
                               String channel) {
        this.userId = userId;
        this.referenceId = referenceId;
        this.title = title;
        this.message = message;
        this.channel = channel;
    }

    public Long getUserId() { return userId; }
    public String getReferenceId() { return referenceId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getChannel() { return channel; }
}
