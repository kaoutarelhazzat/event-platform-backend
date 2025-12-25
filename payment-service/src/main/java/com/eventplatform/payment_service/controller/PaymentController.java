package com.eventplatform.payment_service.controller;

import com.eventplatform.payment_service.dto.PaymentRequest;
import com.eventplatform.payment_service.dto.PaymentResponse;
import com.eventplatform.payment_service.entity.model.Payment;
import com.eventplatform.payment_service.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(toResponse(paymentService.createPayment(request)));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<PaymentResponse> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(paymentService.confirmPayment(id)));
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<PaymentResponse> fail(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(paymentService.failPayment(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(paymentService.getPayment(id)));
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getReferenceId(),
                payment.getAmount().doubleValue(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUserId()
        );
    }
}
