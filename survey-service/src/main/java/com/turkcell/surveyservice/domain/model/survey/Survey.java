package com.turkcell.surveyservice.domain.model.survey;

import com.turkcell.surveyservice.domain.exception.QuestionNotFoundException;
import com.turkcell.surveyservice.domain.model.question.Question;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

//rich survey domain model
public class Survey {

    private final SurveyId id;
    private String title;
    private String description;

    private SurveyStatus status;
    private List<Question> questions;

    private Instant timestamp;

    //controlled constructor
    private Survey(SurveyId id, String title, String description, SurveyStatus status, List<Question> questions, Instant timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.questions = questions;
        this.timestamp = timestamp;
    }

    //create -> new object
    public static Survey create(String title, String description, List<Question> questions) {
        return new Survey(
                SurveyId.generate(),
                title,
                description,
                SurveyStatus.getDefault(),
                questions,
                Instant.now()
                );
    }

    //rehydrate -> existing object
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

    //worker methods-domain behaviors
    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public void addOption(UUID questionId, String optionText) {

        Question question = questions()
                .stream()
                .filter(q -> q.questionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        question.addOption(optionText);
    }

    public void removeOption(Question question) {
        questions.remove(question);
    }

    public void openSurvey() {
        this.status = SurveyStatus.OPEN;
    }

    public void closeSurvey() {
        this.status = SurveyStatus.CLOSED;
    }


    //validate methods-domain invariants
    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        if (title.trim().length() > 255) {
            throw new IllegalArgumentException("Title cannot be longer than 255 characters");
        }
    }

    public static void validateStatus(SurveyStatus status) {
        if (status == SurveyStatus.CLOSED) {
            throw new IllegalArgumentException("Kapalı ankette işlem yapılamaz!");
        }
    }



    //getters
    public SurveyId id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public SurveyStatus status() {
        return status;
    }

    public List<Question> questions() {
        return Collections.unmodifiableList(questions);
    }

    public Instant timestamp() {
        return timestamp;
    }
}
