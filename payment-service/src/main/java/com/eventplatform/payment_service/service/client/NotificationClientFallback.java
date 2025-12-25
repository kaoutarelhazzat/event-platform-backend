package com.eventplatform.payment_service.service.client;

import com.eventplatform.payment_service.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationClientFallback implements NotificationClient {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationClientFallback.class);

    @Override
    public void sendNotification(NotificationRequest request) {
        log.warn(
                "Notification-service indisponible. Fallback exécuté pour userId={}, reference={}",
                request.getUserId(),
                request.getReferenceId()
        );
        // Aucun throw ici → paiement continue
    }
}
