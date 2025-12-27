package com.microservices.eventservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events", schema = "event_schema")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime date;
    private String location;
    private String type;

    private BigDecimal price;

    private int capacity;
    private int remainingTickets;

    // référence auth-service (ID uniquement)
    private Long organizerId;

    @ElementCollection
    @CollectionTable(
            name = "event_participants",
            schema = "event_schema",
            joinColumns = @JoinColumn(name = "event_id")
    )
    @Column(name = "participant_id")
    private List<Long> participants = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (remainingTickets <= 0 && capacity > 0) {
            remainingTickets = capacity;
        }
        if (participants == null) {
            participants = new ArrayList<>();
        }
    }

    // Getters / Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getRemainingTickets() { return remainingTickets; }
    public void setRemainingTickets(int remainingTickets) { this.remainingTickets = remainingTickets; }

    public Long getOrganizerId() { return organizerId; }
    public void setOrganizerId(Long organizerId) { this.organizerId = organizerId; }

    public List<Long> getParticipants() { return participants; }
    public void setParticipants(List<Long> participants) { this.participants = participants; }
}
