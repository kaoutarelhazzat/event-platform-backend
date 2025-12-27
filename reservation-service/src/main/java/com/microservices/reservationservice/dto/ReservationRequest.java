package com.microservices.reservationservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull
    public Long eventId;

    @NotNull
    public Long userId;

    @Min(1)
    @Max(4)
    public int tickets;
}
