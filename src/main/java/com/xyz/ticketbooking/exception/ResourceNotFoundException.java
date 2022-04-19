package com.xyz.ticketbooking.exception;

import lombok.Getter;

/**
 * Custom Exception class.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String id = ErrorCodes.TICKET_BOOKING_CUSTOM_ERROR_001;

    public ResourceNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}