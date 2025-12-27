package com.microservices.reservationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "event-service")
public interface EventClient {

    @GetMapping("/events/{id}")
    EventDto getEvent(@PathVariable Long id);


    @PatchMapping("/events/{id}/tickets/decrease")
    void decreaseTickets(@PathVariable Long id, @RequestParam int count);

    @PatchMapping("/events/{id}/tickets/increase")
    void increaseTickets(@PathVariable Long id, @RequestParam int count);

    class EventDto {
        public Long id;
        public BigDecimal price;
        public int remainingTickets;
    }
}
