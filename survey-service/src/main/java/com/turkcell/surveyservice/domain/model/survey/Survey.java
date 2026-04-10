package com.turkcell.surveyservice.domain.model.survey;

import com.turkcell.surveyservice.domain.event.SurveyClosedEvent;
import com.turkcell.surveyservice.domain.event.SurveyPublishedEvent;
import com.turkcell.surveyservice.domain.exception.*;
import com.turkcell.surveyservice.domain.model.question.Question;
import com.turkcell.surveyservice.domain.model.question.QuestionId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//rich survey domain model - aggregate root
public class Survey {

    private final SurveyId id;
    private String title;
    private String description;

    private SurveyStatus status;
    private List<Question> questions;

    private Instant createdAt;
    private Instant updatedAt;

    // controlled constructor
    private Survey(SurveyId id, String title, String description,
            SurveyStatus status, List<Question> questions,
            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.questions = new ArrayList<>(questions);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // create -> new object
    // aggregate oluşturulurken invariant korunur.
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
                null);
    }

    // rehydrate -> existing object
    public static Survey rehydrate(SurveyId id, String title, String description, SurveyStatus status,
            List<Question> questions, Instant createdAt, Instant updatedAt) {
        return new Survey(
                id,
                title,
                description,
                status,
                questions,
                createdAt,
                updatedAt);
    }

    // worker methods-domain behaviors
    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
        this.updatedAt = Instant.now();
    }

    public void changeDescription(String newDescription) {
        validateDescription(newDescription);
        this.description = newDescription;
        this.updatedAt = Instant.now();
    }

    public void addQuestion(String questionText) {
        checkStatus();
        this.questions.add(new Question(QuestionId.generate(), questionText, new ArrayList<>()));
        this.updatedAt = Instant.now();
    }

    public void addOption(QuestionId questionId, String optionText) {
        checkStatus();
        Question question = questions()
                .stream()
                .filter(q -> q.id().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        question.addOption(optionText);
        this.updatedAt = Instant.now();
    }

    public void removeQuestion(QuestionId questionId) {
        checkStatus();
        boolean removed = questions.removeIf(question -> question.id().equals(questionId));
        if (!removed) {
            throw new QuestionNotFoundException("Question not found");
        }
        this.updatedAt = Instant.now();
    }

    public void removeOption(QuestionId questionId, String optionText) {
        checkStatus();
        Question question = questions
                .stream()
                .filter(q -> q.id().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        question.removeOption(optionText);
        this.updatedAt = Instant.now();
    }

    public SurveyClosedEvent closeSurvey() {
        if (this.status == SurveyStatus.OPEN) {
            this.status = SurveyStatus.CLOSED;
        } else {
            throw new InvalidSurveyStatusException("Anket kapatılamaz!");
        }
        this.updatedAt = Instant.now();
        return new SurveyClosedEvent(
                id,
                status,
                Instant.now());
    }

    public SurveyPublishedEvent publishSurvey() {
        // kontroller...
        for (Question question : questions) {
            if (question.options().size() < 2) {
                throw new InsufficientOptionsException("Question must contain at least 2 options");
            }
        }
        if (this.status == SurveyStatus.DRAFT) {
            this.status = SurveyStatus.OPEN;
        } else {
            throw new InvalidSurveyStatusException("Yalnızca DRAFT durumundaki anketler yayınlanabilir.");
        }
        this.updatedAt = Instant.now();
        return new SurveyPublishedEvent(
                id,
                status,
                Instant.now());
    }

    public void checkStatus() {
        if (this.status != SurveyStatus.DRAFT) {
            throw new InvalidSurveyStatusException("Ankette işlem yapılamaz!");
        }
    }

    // validate methods-domain invariants
    public static void validateTitle(String title) {
        if (title == null || title.isBlank())
            throw new InvalidTitleException("Survey title cannot be empty");
        if (title.trim().length() > 50) {
            throw new InvalidTitleException("Survey title cannot be longer than 50 characters");
        }
    }

    public static void validateDescription(String description) {
        if (description == null || description.isBlank())
            throw new InvalidDescriptionException("Description cannot be empty");
        if (description.length() > 255) {
            throw new InvalidDescriptionException("Description cannot be longer than 255 characters");
        }
    }

    // getters
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

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
