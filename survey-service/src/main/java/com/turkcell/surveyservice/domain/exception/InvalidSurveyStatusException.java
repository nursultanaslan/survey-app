package com.turkcell.surveyservice.domain.exception;

public class InvalidSurveyStatusException extends RuntimeException {
    public InvalidSurveyStatusException(String message) {
        super(message);
    }
}
