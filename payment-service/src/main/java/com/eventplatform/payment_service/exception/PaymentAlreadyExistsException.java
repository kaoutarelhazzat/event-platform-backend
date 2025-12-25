package com.eventplatform.payment_service.exception;

public class PaymentAlreadyExistsException extends RuntimeException {

    public PaymentAlreadyExistsException(String referenceId) {
        super("Payment already exists for reference: " + referenceId);
    }
}
