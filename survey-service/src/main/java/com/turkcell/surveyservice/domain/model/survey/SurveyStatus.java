package com.turkcell.surveyservice.domain.model.survey;

public enum SurveyStatus {
    DRAFT, //ilk create anında draft olarak işaretlensin.
    OPEN,  //publish edildikten sonra open durumuna geçsin.
    CLOSED;

    public static SurveyStatus getDefault(){
        return DRAFT;
    }
}
