package com.microservices.reservationservice.repository;

import com.microservices.reservationservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByEventId(Long eventId);
}
