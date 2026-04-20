package com.turkcell.surveyservice.domain.model.option;

import com.turkcell.surveyservice.domain.exception.InvalidOptionTextException;

//child entity
public class Option {

    private final OptionId id;
    private String text;

    public Option(OptionId id, String text) {
        validateOptionText(text);
        this.id = id;
        this.text = text;
    }

    public void changeOptionText(String newText) {
        validateOptionText(newText);
        this.text = newText;
    }

    public static void validateOptionText(String text) {
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

    //getters
    public OptionId id() {
        return id;
    }

    public String text() {
        return text;
    }
}
