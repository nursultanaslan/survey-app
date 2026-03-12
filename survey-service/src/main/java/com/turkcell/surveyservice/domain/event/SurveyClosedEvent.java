package com.turkcell.surveyservice.domain.event;

import com.turkcell.surveyservice.domain.model.survey.SurveyId;
import com.turkcell.surveyservice.domain.model.survey.SurveyStatus;

import java.time.Instant;

public record SurveyClosedEvent(SurveyId id, SurveyStatus status, Instant timestamp) {
}
