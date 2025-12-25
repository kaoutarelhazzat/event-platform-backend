package com.eventplatform.notification_service.dto;

import jakarta.validation.constraints.NotNull;

public class NotificationRequest {

    @NotNull
    private Long userId;

    @NotNull
    private String title;

    @NotNull
    private String message;

    @NotNull
    private String channel; // EMAIL / SMS

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
