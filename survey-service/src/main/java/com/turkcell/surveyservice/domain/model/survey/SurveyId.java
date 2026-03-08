package com.turkcell.surveyservice.domain.model.survey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record SurveyId(UUID value) implements Serializable {
    public SurveyId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static SurveyId generate(){
        return new SurveyId(UUID.randomUUID());
    }
}
