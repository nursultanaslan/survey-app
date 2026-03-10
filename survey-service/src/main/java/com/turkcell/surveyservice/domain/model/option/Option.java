package com.turkcell.surveyservice.domain.model.option;

import java.util.UUID;

public class Option {

    private UUID optionId;
    private String text;

    public Option(UUID optionId, String text) {
        this.optionId = optionId;
        this.text = text;
    }

    //getters
    public UUID optionId() {
        return optionId;
    }

    public String text() {
        return text;
    }
}
