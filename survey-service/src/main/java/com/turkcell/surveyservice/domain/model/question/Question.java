package com.turkcell.surveyservice.domain.model.question;

import com.turkcell.surveyservice.domain.model.option.Option;

import java.util.List;
import java.util.UUID;

public class Question {

    private UUID questionId;
    private String text;
    private List<Option> options;

    public Question(UUID questionId, String text, List<Option> options) {
        this.questionId = questionId;
        this.text = text;
        this.options = options;
    }


    public void addOption(String text) {
        if (options.size() >= 10) {
            throw new IllegalStateException("Question already has more than 10 options");
        }
        options.add(new Option(UUID.randomUUID(), text));
    }

    //getters
    public UUID questionId() {
        return questionId;
    }

    public String text() {
        return text;
    }

    public List<Option> options() {
        return options;
    }
}
