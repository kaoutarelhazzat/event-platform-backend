package com.eventplatform.payment_service.service;

import com.eventplatform.payment_service.dto.NotificationRequest;
import com.eventplatform.payment_service.dto.PaymentRequest;
import com.eventplatform.payment_service.entity.enums.PaymentStatus;
import com.eventplatform.payment_service.entity.model.Payment;
import com.eventplatform.payment_service.exception.PaymentAlreadyExistsException;
import com.eventplatform.payment_service.repository.PaymentRepository;
import com.eventplatform.payment_service.service.client.NotificationClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.*;

@Service
@Transactional
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final NotificationClient notificationClient;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public PaymentService(PaymentRepository paymentRepository,
                          NotificationClient notificationClient) {
        this.paymentRepository = paymentRepository;
        this.notificationClient = notificationClient;
    }

    public Payment createPayment(PaymentRequest request) {
        if (paymentRepository.existsByReferenceId(request.getReferenceId())) {
            throw new PaymentAlreadyExistsException(request.getReferenceId());
        }

        Payment payment = new Payment();
        payment.setReferenceId(request.getReferenceId());
        payment.setAmount(request.getAmount());
        payment.setUserId(request.getUserId());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public Payment confirmPayment(Long id) {
        Payment payment = getPayment(id);
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment saved = paymentRepository.save(payment);

        safeNotify(saved,
                "Paiement confirmé",
                "Votre paiement " + saved.getReferenceId() + " a été confirmé.",
                "EMAIL");

        return saved;
    }

    public Payment failPayment(Long id) {
        Payment payment = getPayment(id);
        payment.setStatus(PaymentStatus.FAILED);
        Payment saved = paymentRepository.save(payment);

        safeNotify(saved,
                "Paiement échoué",
                "Le paiement " + saved.getReferenceId() + " a échoué.",
                "EMAIL");

        return saved;
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "notifyFallback")
    @Retry(name = "notificationService")
    @TimeLimiter(name = "notificationService")
    public CompletionStage<Void> notifyAsync(NotificationRequest request) {
        return CompletableFuture.runAsync(
                () -> notificationClient.sendNotification(request),
                executor
        );
    }

    private void safeNotify(Payment payment,
                            String title,
                            String message,
                            String channel) {

        NotificationRequest req = new NotificationRequest(
                payment.getUserId(),
                payment.getReferenceId(),
                title,
                message,
                channel
        );

        try {
            notifyAsync(req).toCompletableFuture().get();
        } catch (Exception ex) {
            log.warn(
                    "Notification failed but payment OK paymentId={}, cause={}",
                    payment.getId(),
                    ex.getMessage()
            );
        }
    }


    private CompletionStage<Void> notifyFallback(NotificationRequest req, Throwable ex) {
        log.warn("Fallback notification userId={}", req.getUserId());
        return CompletableFuture.completedFuture(null);
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
