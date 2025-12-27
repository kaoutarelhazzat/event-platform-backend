package com.microservices.reservationservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations", schema = "reservation_schema")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Long userId;

    private int tickets;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    void init() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ReservationStatus.PENDING;
        }
    }

    // getters & setters
    public Long getId() { return id; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public int getTickets() { return tickets; }
    public void setTickets(int tickets) { this.tickets = tickets; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
