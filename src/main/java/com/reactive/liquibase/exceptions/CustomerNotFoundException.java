package com.reactive.liquibase.exceptions;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private static final Log LOG = LogFactory.getLog(CustomerNotFoundException.class);
    private static final String NOT_FOUND = "Customer Not Found Exception : %d";
    private static final String ALREADY_PRESENT = "Customer Already Present Exception : %s";

    public CustomerNotFoundException(Long customerId, ErrorCode errorCode, HttpStatus httpStatus) {
        super(String.format(NOT_FOUND, customerId));
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        LOG.error(getMessage());
    }

    public CustomerNotFoundException(String customerEmail, ErrorCode errorCode, HttpStatus httpStatus) {
        super(String.format(ALREADY_PRESENT, customerEmail));
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        LOG.error(getMessage());
    }

    public CustomerNotFoundException(String customerEmail, ErrorCode errorCode) {
        this(customerEmail, errorCode, HttpStatus.NOT_FOUND);
    }

    public CustomerNotFoundException(Long customerId, ErrorCode errorCode) {
        this(customerId, errorCode, HttpStatus.NOT_FOUND);
    }

    public static <T> Mono<T> error(Object customerId, ErrorCode errorCode) {
        if (customerId instanceof String email) {
            return Mono.error(new CustomerNotFoundException(email, errorCode));
        } else if (customerId instanceof Long id) {
            return Mono.error(new CustomerNotFoundException(id, errorCode));
        } else {
            return Mono.error(new CustomerNotFoundException(String.valueOf(customerId), errorCode));
        }
    }

    public static <T> Mono<T> error(String customerEmail, ErrorCode errorCode) {
        return Mono.error(new CustomerNotFoundException(customerEmail, errorCode));
    }

    public static <T> Mono<T> error(String customerEmail, ErrorCode errorCode, HttpStatus status) {
        return Mono.error(new CustomerNotFoundException(customerEmail, errorCode, status));
    }
    public static <T> Mono<T> error(Long customerId, ErrorCode errorCode, HttpStatus status) {
        return Mono.error(new CustomerNotFoundException(customerId, errorCode, status));
    }
}
