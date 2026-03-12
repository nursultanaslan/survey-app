package com.turkcell.surveyservice.domain.model.option;

//child entity
public class Option {

    private final OptionId id;
    private String text;

    public Option(OptionId id, String text) {
        validateOptionText(text);
        this.id = id;
        this.text = text;
    }

    public void changeText(String newText) {
        validateOptionText(text);
        this.text = newText;
    }

    public static void validateOptionText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text must not be null or empty");
        }
        if (text.length() < 2) {
            throw new IllegalArgumentException("Text must have at least 2 characters");
        }
        if (text.length() > 50) {
            throw new IllegalArgumentException("Text must have at most 50 characters");
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
