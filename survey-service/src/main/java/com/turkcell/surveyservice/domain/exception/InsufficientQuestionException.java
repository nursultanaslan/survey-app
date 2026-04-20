package com.turkcell.surveyservice.domain.exception;

public class InsufficientQuestionException extends RuntimeException {
    public InsufficientQuestionException(String message) {
        super(message);
    }
}
