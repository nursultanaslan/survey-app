package com.turkcell.surveyservice.domain.model.survey;

import com.turkcell.surveyservice.domain.exception.QuestionNotFoundException;
import com.turkcell.surveyservice.domain.model.option.OptionId;
import com.turkcell.surveyservice.domain.model.question.Question;
import com.turkcell.surveyservice.domain.model.question.QuestionId;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

//rich survey domain model - aggregate root
public class Survey {

    private final SurveyId id;
    private String title;
    private String description;

    private SurveyStatus status;
    private List<Question> questions;

    private Instant createdAt;
    private Instant updatedAt;

    //controlled constructor
    private Survey(SurveyId id, String title, String description, SurveyStatus status, List<Question> questions, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //create -> new object
    public static Survey create(String title, String description, List<Question> questions) {
        validateTitle(title);
        validateDescription(description);
        return new Survey(
                SurveyId.generate(),
                title,
                description,
                SurveyStatus.getDefault(),
                questions,
                Instant.now(),
                null
                );
    }

    //rehydrate -> existing object
    public static Survey rehydrate(SurveyId id, String title, String description, SurveyStatus status, List<Question> questions, Instant createdAt, Instant updatedAt) {
        return new Survey(
                id,
                title,
                description,
                status,
                questions,
                createdAt,
                updatedAt
        );
    }

    //worker methods-domain behaviors
    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changeDescription(String newDescription) {
        validateDescription(newDescription);
        this.description = newDescription;
    }

    public void addQuestion(String questionText) {
        checkStatus(status);
    }

    public void addOption(UUID questionId, String optionText) {
        checkStatus(status);
        Question question = questions()
                .stream()
                .filter(q -> q.id().equals(new QuestionId(questionId)))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        question.addOption(optionText);
    }

    public void removeQuestion(QuestionId questionId) {
        questions().removeIf(question -> question.id().equals(questionId));
    }


    public void removeOption(QuestionId questionId, OptionId optionId) {

    }

    //TODO: question silindiğinde optionlar da silinir.

    public void openSurvey() {
        if (this.status == SurveyStatus.CLOSED) {
            this.status = SurveyStatus.OPEN;
        }
    }

    public void closeSurvey() {
        if (this.status == SurveyStatus.OPEN) {
            this.status = SurveyStatus.CLOSED;
        }
    }

    public void publishSurvey() {
        //kontroller...
        if (options.size() < 2) {
            throw new IllegalArgumentException("Question must contain at least 2 options");
        }

        this.status = SurveyStatus.OPEN;
    }


    //validate methods-domain invariants
    public static void validateTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title cannot be empty");
        if (title.trim().length() > 50) {
            throw new IllegalArgumentException("Title cannot be longer than 50 characters");
        }
    }

    public static void validateDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be empty");
        if (description.length() > 255) {
            throw new IllegalArgumentException("Description cannot be longer than 255 characters");
        }
    }

    public static void checkStatus(SurveyStatus status) {
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
        return questions;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
