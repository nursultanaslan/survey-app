package com.turkcell.surveyservice.domain.model.survey;

public enum SurveyStatus {
    OPEN,
    CLOSED;

    public static SurveyStatus getDefault(){
        return OPEN;
    }
}
