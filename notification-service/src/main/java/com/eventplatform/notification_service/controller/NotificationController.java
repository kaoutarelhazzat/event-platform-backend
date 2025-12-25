package com.eventplatform.notification_service.controller;

import com.eventplatform.notification_service.dto.NotificationRequest;
import com.eventplatform.notification_service.dto.NotificationResponse;
import com.eventplatform.notification_service.entity.model.Notification;
import com.eventplatform.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        Notification notification = notificationService.createNotification(request);
        return ResponseEntity.ok(toResponse(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                notificationService.getByUser(userId)
                        .stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    private NotificationResponse toResponse(Notification n) {
        // ton DTO Response attend LocalDateTime, alors que l’entity est OffsetDateTime
        LocalDateTime sentAtLocal = n.getSentAt() == null ? null : n.getSentAt().toLocalDateTime();

        return new NotificationResponse(
                n.getId(),
                n.getMessage(),
                n.getStatus(),
                sentAtLocal
        );
    }
}
