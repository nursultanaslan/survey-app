package com.turkcell.surveyservice.domain.exception;

public class InsufficientOptionsException extends RuntimeException {
    public InsufficientOptionsException(String message) {
        super(message);
    }
}
