package com.microservices.reservationservice.controller;

import com.microservices.reservationservice.dto.ReservationRequest;
import com.microservices.reservationservice.dto.ReservationResponse;
import com.microservices.reservationservice.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest req) {
        return service.create(req);
    }

    @PatchMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @GetMapping
    public List<ReservationResponse> all() {
        return service.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<ReservationResponse> byUser(@PathVariable Long userId) {
        return service.findByUser(userId);
    }

    @GetMapping("/event/{eventId}")
    public List<ReservationResponse> byEvent(@PathVariable Long eventId) {
        return service.findByEvent(eventId);
    }
}
