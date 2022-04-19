package com.xyz.ticketbooking.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception handling for all requests.
 * Further, ResourceNotFoundException is custom exception created for 404.
 */
@ControllerAdvice("com.xyz.ticketbooking")
@Slf4j
public class ExceptionHandlerController {

    //404 - Not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShowNotFound(final ResourceNotFoundException ex) {
        log.error("Not Found Exception: " + ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ex.getId());
    }

    //400 - Bad request
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInputException(IllegalArgumentException ex) {
        log.error("Invalid Input Exception: " + ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ErrorCodes.TICKET_BOOKING_BAD_REQUEST_ERROR_001);
    }

    // 500 - All other error as Internal error
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception: " + ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.TICKET_BOOKING_SERVER_ERROR_002);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Throwable ex, HttpStatus httpStatus, String errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode, ex.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, httpStatus);
    }
}
