package com.turkcell.surveyservice.domain.port;

import com.turkcell.surveyservice.domain.model.survey.Survey;

public interface SurveyRepository {

    Survey save(Survey survey);
}
