package com.eventplatform.payment_service.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull
    private String referenceId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Long userId;

    public String getReferenceId() {
        return referenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getUserId() {
        return userId;
    }
}
