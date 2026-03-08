package com.turkcell.surveyservice.domain.model.survey;

import com.turkcell.surveyservice.domain.model.question.Question;

import java.time.Instant;
import java.util.List;

public class Survey {

    private SurveyId id;
    private String title;
    private String description;

    private SurveyStatus status;
    private List<Question> questions;

    private Instant timestamp;

    private Survey(SurveyId id, String title, String description, SurveyStatus status, List<Question> questions, Instant timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.questions = questions;
        this.timestamp = timestamp;
    }

    public static Survey create(String title, String description, SurveyStatus status, List<Question> questions, Instant timestamp) {
        return new Survey(
                SurveyId.generate(),
                title,
                description,
                SurveyStatus.getDefault(),
                questions,
                timestamp
                );
    }

    public static Survey rehydrate(SurveyId id, String title, String description, SurveyStatus status, List<Question> questions, Instant timestamp) {
        return new Survey(
                id,
                title,
                description,
                status,
                questions,
                timestamp
        );
    }

}
