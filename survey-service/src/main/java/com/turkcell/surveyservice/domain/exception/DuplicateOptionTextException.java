package com.turkcell.surveyservice.domain.exception;

public class DuplicateOptionTextException extends RuntimeException {
    public DuplicateOptionTextException(String message) {
        super(message);
    }
}
