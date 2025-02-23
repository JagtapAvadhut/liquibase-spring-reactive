package com.reactive.liquibase.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private String status;
    private LocalDateTime timestamp;
    private String path;
    private String method;
//    private String trace;
    private Map<String, Object> additionalDetails;
}