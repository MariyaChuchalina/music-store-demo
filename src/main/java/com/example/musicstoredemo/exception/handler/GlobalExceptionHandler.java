package com.example.musicstoredemo.exception.handler;

import com.example.musicstoredemo.exception.AccessDeniedException;
import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.exception.UserNotFoundException;
import com.example.musicstoredemo.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DELIMITER = "=";

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException itemNotFoundException, WebRequest request) {

        log.error("Failed to find the requested item", itemNotFoundException);
        return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request.getDescription(false).split(DELIMITER)[1]);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException, WebRequest request) {

        log.error("Failed to find the requested user", userNotFoundException);
        return buildErrorResponse(userNotFoundException, HttpStatus.NOT_FOUND, request.getDescription(false).split(DELIMITER)[1]);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException accessDeniedException, WebRequest request) {

        log.error("Access denied", accessDeniedException);
        return buildErrorResponse(accessDeniedException, HttpStatus.FORBIDDEN, request.getDescription(false).split(DELIMITER)[1]);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {

        log.error("Unknown error occurred", exception);
        return buildErrorResponse("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false).split(DELIMITER)[1]);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildErrorResponse(ex, status, request.getContextPath());
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      String path) {
        return buildErrorResponse(exception.getMessage(), httpStatus, path);
    }

    private ResponseEntity<Object> buildErrorResponse(String message,
                                                      HttpStatus httpStatus,
                                                      String path) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message, path);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
