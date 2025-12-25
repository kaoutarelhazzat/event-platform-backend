package com.eventplatform.payment_service.service.client;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class NotificationRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String channel;

    public NotificationRequest() {}

    public NotificationRequest(Long userId, String title, String message, String channel) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.channel = channel;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getChannel() {
        return channel;
    }
}
