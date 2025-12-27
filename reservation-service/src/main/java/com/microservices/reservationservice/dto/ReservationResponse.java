package com.microservices.reservationservice.dto;

import com.microservices.reservationservice.entity.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationResponse {

    public Long id;
    public Long eventId;
    public Long userId;
    public int tickets;
    public BigDecimal amount;
    public ReservationStatus status;
    public LocalDateTime createdAt;
}
