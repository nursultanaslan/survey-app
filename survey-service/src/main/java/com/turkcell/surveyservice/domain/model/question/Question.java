package com.turkcell.surveyservice.domain.model.question;

import com.turkcell.surveyservice.domain.model.option.Option;

import java.util.List;
import java.util.UUID;

public class Question {

    private UUID questionId;
    private String text;
    private List<Option> options;
}
