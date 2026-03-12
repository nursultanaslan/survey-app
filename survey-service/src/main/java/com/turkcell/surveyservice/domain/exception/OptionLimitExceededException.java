package com.turkcell.surveyservice.domain.exception;

public class OptionLimitExceededException extends RuntimeException {
    public OptionLimitExceededException(String message) {
        super(message);
    }
}
