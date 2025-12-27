package com.microservices.eventservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponse {
    public Long id;
    public String title;
    public String description;
    public LocalDateTime date;
    public String location;
    public String type;
    public BigDecimal price;
    public int capacity;
    public int remainingTickets;
    public Long organizerId;
    public List<Long> participants;
}
