package com.microservices.reservationservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> business(BusinessException ex, HttpServletRequest req) {
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "BUSINESS_ERROR", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ReservationNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(404, "NOT_FOUND", ex.getMessage(), req.getRequestURI()));
    }
}
