package com.microservices.eventservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventRequest {

    @NotBlank
    public String title;

    public String description;

    @NotNull
    public LocalDateTime date;

    @NotBlank
    public String location;

    @NotBlank
    public String type;

    @NotNull
    @Min(0)
    public BigDecimal price;

    @Min(1)
    public int capacity;

    @NotNull
    public Long organizerId;
}
