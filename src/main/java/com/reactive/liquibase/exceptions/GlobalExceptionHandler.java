package com.reactive.liquibase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {  // Fixed typo in class name

    // Handle CustomerNotFoundException
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Mono<ErrorResponse>> handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            ServerWebExchange exchange) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("clientIP", exchange.getRequest().getRemoteAddress().toString());
        additionalDetails.put("requestHeaders", exchange.getRequest().getHeaders().toSingleValueMap());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getDefaultMessage(),
                LocalDateTime.now(),
                path,
                method,
                additionalDetails
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Mono.just(errorResponse));
    }

    // Handle Unexpected RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Mono<ErrorResponse>> handleRuntimeException(
            RuntimeException ex,
            ServerWebExchange exchange) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("clientIP", exchange.getRequest().getRemoteAddress().toString());
        additionalDetails.put("requestHeaders", exchange.getRequest().getHeaders().toSingleValueMap());

        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred. Please try again later.",
                "INTERNAL_SERVER_ERROR",
                "Internal Server Error",
                LocalDateTime.now(),
                path,
                method,
                additionalDetails
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(errorResponse));
    }

    // Handle Generic Exception (Covers All Other Unhandled Exceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mono<ErrorResponse>> handleGenericException(
            Exception ex,
            ServerWebExchange exchange) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("clientIP", exchange.getRequest().getRemoteAddress().toString());
        additionalDetails.put("requestHeaders", exchange.getRequest().getHeaders().toSingleValueMap());

        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected system error occurred.",
                "UNKNOWN_ERROR",
                "Internal server error occurred",
                LocalDateTime.now(),
                path,
                method,
                additionalDetails
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(errorResponse));
    }
}
