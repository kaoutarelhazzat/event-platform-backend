package com.eventplatform.payment_service.dto;

import com.eventplatform.payment_service.entity.enums.PaymentStatus;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private String referenceId;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private Long userId;

    public PaymentResponse(Long id,
                           String referenceId,
                           Double amount,
                           PaymentStatus status,
                           LocalDateTime createdAt,
                           Long userId) {
        this.id = id;
        this.referenceId = referenceId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }
}
