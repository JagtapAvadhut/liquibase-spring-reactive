package com.reactive.liquibase.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Customer related errors (1000-1999)
    CUSTOMER_NOT_FOUND("ERR_1001", "Customer not found"),
    CUSTOMER_ALREADY_EXISTS("ERR_1002", "Customer already exists"),
    INVALID_CUSTOMER_DATA("ERR_1003", "Invalid customer data"),
     CUSTOMER_INTERNAL_ERROR("ERR_1004", "Failed to save customer"),
    // Authentication/Authorization errors (2000-2999)
    UNAUTHORIZED_ACCESS("ERR_2001", "Unauthorized access"),
    INVALID_TOKEN("ERR_2002", "Invalid or expired token"),
    INSUFFICIENT_PERMISSIONS("ERR_2003", "Insufficient permissions"),

    // Validation errors (3000-3999)
    INVALID_INPUT("ERR_3001", "Invalid input data"),
    MISSING_REQUIRED_FIELD("ERR_3002", "Required field is missing"),
    INVALID_FORMAT("ERR_3003", "Invalid data format"),

    // Database errors (4000-4999)
    DATABASE_ERROR("ERR_4001", "Database operation failed"),
    DUPLICATE_KEY("ERR_4002", "Duplicate key violation"),
    CONNECTION_ERROR("ERR_4003", "Database connection error"),

    // System errors (5000-5999)
    INTERNAL_SERVER_ERROR("ERR_5001", "Internal server error"),
    SERVICE_UNAVAILABLE("ERR_5002", "Service temporarily unavailable"),
    EXTERNAL_API_ERROR("ERR_5003", "External API communication error"),

    // Business logic errors (6000-6999)
    INSUFFICIENT_FUNDS("ERR_6001", "Insufficient funds"),
    LIMIT_EXCEEDED("ERR_6002", "Operation limit exceeded"),
    INVALID_STATUS_TRANSITION("ERR_6003", "Invalid status transition");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
