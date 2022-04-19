package com.xyz.ticketbooking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the standard response of all rest endpoints for any error.
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String errorCode;
    private String message;

}
