package com.turkcell.surveyservice.domain.model.survey;

import com.turkcell.surveyservice.domain.exception.InvalidOptionTextException;

public record Option(String text) {

    public Option {
        if (text == null || text.isBlank()) {
            throw new InvalidOptionTextException("Text must not be null or empty");
        }
        if (text.length() < 2) {
            throw new InvalidOptionTextException("Text must have at least 2 characters");
        }
        if (text.length() > 50) {
            throw new InvalidOptionTextException("Text must have at most 50 characters");
        }
    }
}
