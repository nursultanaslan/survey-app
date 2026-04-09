package com.turkcell.surveyservice.domain.exception;

public class InvalidOptionTextException extends RuntimeException {
    public InvalidOptionTextException(String message) {
        super(message);
    }
}
