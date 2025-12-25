package com.eventplatform.payment_service.repository;

import com.eventplatform.payment_service.entity.enums.PaymentStatus;
import com.eventplatform.payment_service.entity.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReferenceId(String referenceId);

    List<Payment> findAllByStatus(PaymentStatus status);

    boolean existsByReferenceId(String referenceId);
}
