package com.microservices.reservationservice.service;

import com.microservices.reservationservice.client.EventClient;
import com.microservices.reservationservice.dto.ReservationRequest;
import com.microservices.reservationservice.dto.ReservationResponse;
import com.microservices.reservationservice.entity.Reservation;
import com.microservices.reservationservice.entity.ReservationStatus;
import com.microservices.reservationservice.exception.BusinessException;
import com.microservices.reservationservice.exception.ReservationNotFoundException;
import com.microservices.reservationservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    private static final int MAX_TICKETS = 4;

    private final ReservationRepository repository;
    private final EventClient eventClient;

    public ReservationService(ReservationRepository repository, EventClient eventClient) {
        this.repository = repository;
        this.eventClient = eventClient;
    }

    public ReservationResponse create(ReservationRequest req) {

        if (req.tickets > MAX_TICKETS) {
            throw new BusinessException("Maximum 4 tickets per reservation allowed");
        }

        EventClient.EventDto event = eventClient.getEvent(req.eventId);

        if (event == null) {
            throw new BusinessException("Event not found");
        }

        if (event.remainingTickets < req.tickets) {
            throw new BusinessException("Not enough remaining tickets");
        }

        // Décrément tickets event
        eventClient.decreaseTickets(req.eventId, req.tickets);

        Reservation r = new Reservation();
        r.setEventId(req.eventId);
        r.setUserId(req.userId);
        r.setTickets(req.tickets);
        r.setAmount(event.price.multiply(BigDecimal.valueOf(req.tickets)));
        r.setStatus(ReservationStatus.PENDING);

        return map(repository.save(r));
    }

    public ReservationResponse cancel(Long id) {
        Reservation r = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (r.getStatus() == ReservationStatus.CANCELLED) {
            throw new BusinessException("Reservation already cancelled");
        }

        eventClient.increaseTickets(r.getEventId(), r.getTickets());
        r.setStatus(ReservationStatus.CANCELLED);

        return map(repository.save(r));
    }

    public List<ReservationResponse> findAll() {
        return repository.findAll().stream().map(this::map).toList();
    }

    public List<ReservationResponse> findByUser(Long userId) {
        return repository.findByUserId(userId).stream().map(this::map).toList();
    }

    public List<ReservationResponse> findByEvent(Long eventId) {
        return repository.findByEventId(eventId).stream().map(this::map).toList();
    }

    private ReservationResponse map(Reservation r) {
        ReservationResponse resp = new ReservationResponse();
        resp.id = r.getId();
        resp.eventId = r.getEventId();
        resp.userId = r.getUserId();
        resp.tickets = r.getTickets();
        resp.amount = r.getAmount();
        resp.status = r.getStatus();
        resp.createdAt = r.getCreatedAt();
        return resp;
    }
}
