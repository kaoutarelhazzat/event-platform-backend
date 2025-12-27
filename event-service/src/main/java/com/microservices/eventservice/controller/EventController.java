package com.microservices.eventservice.controller;

import com.microservices.eventservice.dto.EventRequest;
import com.microservices.eventservice.dto.EventResponse;
import com.microservices.eventservice.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse create(@Valid @RequestBody EventRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<EventResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }
}
