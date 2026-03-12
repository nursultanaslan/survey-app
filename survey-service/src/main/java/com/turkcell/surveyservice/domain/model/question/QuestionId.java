package com.turkcell.surveyservice.domain.model.question;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record QuestionId(UUID value) implements Serializable {
    public QuestionId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static QuestionId generate() {
        return new QuestionId(UUID.randomUUID());
    }
}
