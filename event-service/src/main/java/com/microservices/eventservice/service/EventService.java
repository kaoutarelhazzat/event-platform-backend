package com.microservices.eventservice.service;

import com.microservices.eventservice.dto.EventRequest;
import com.microservices.eventservice.dto.EventResponse;
import com.microservices.eventservice.entity.Event;
import com.microservices.eventservice.exception.EventNotFoundException;
import com.microservices.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public EventResponse create(EventRequest req) {
        Event e = new Event();
        e.setTitle(req.title);
        e.setDescription(req.description);
        e.setDate(req.date);
        e.setLocation(req.location);
        e.setType(req.type);
        e.setPrice(req.price);
        e.setCapacity(req.capacity);
        e.setRemainingTickets(req.capacity);
        e.setOrganizerId(req.organizerId);

        return map(repository.save(e));
    }

    @Transactional(readOnly = true)
    public List<EventResponse> findAll() {
        return repository.findAll().stream().map(this::map).toList();
    }

    @Transactional(readOnly = true)
    public EventResponse findById(Long id) {
        Event e = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return map(e);
    }

    /* ================================
       🎟️ GESTION DES TICKETS
       ================================ */

    public void decreaseTickets(Long id, int count) {
        Event e = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        if (e.getRemainingTickets() < count) {
            throw new IllegalStateException("Not enough remaining tickets");
        }

        e.setRemainingTickets(e.getRemainingTickets() - count);
        repository.save(e);
    }

    public void increaseTickets(Long id, int count) {
        Event e = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        e.setRemainingTickets(e.getRemainingTickets() + count);
        repository.save(e);
    }

    /* ================================
       🔁 MAPPING
       ================================ */

    private EventResponse map(Event e) {
        EventResponse r = new EventResponse();
        r.id = e.getId();
        r.title = e.getTitle();
        r.description = e.getDescription();
        r.date = e.getDate();
        r.location = e.getLocation();
        r.type = e.getType();
        r.price = e.getPrice();
        r.capacity = e.getCapacity();
        r.remainingTickets = e.getRemainingTickets();
        r.organizerId = e.getOrganizerId();
        r.participants = (e.getParticipants() == null)
                ? List.of()
                : e.getParticipants();
        return r;
    }
}
