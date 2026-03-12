package com.turkcell.surveyservice.domain.model.question;

import com.turkcell.surveyservice.domain.exception.OptionLimitExceededException;
import com.turkcell.surveyservice.domain.model.option.Option;
import com.turkcell.surveyservice.domain.model.option.OptionId;

import java.util.List;
import java.util.UUID;

//child entity
public class Question {

    private final QuestionId id;
    private String text;
    private List<Option> options;

    public Question(QuestionId id, String text, List<Option> options) {
        validateQuestionText(text);
        this.id = id;
        this.text = text;
        this.options = options;
    }


    public void addOption(String text) {
        checkOptionSize(options);
        options.add(new Option(OptionId.generate(), text));
    }

    public static void checkOptionSize(List<Option> options) {
        if (options.size() >= 10) {
            throw new OptionLimitExceededException("Question has more than 10 options");
        }
    }

    //question metni boş olamaz.
    public static void validateQuestionText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Question text must not be null or empty!");
        }
    }

    //aynı question içerisinde duplicate option olamaz.
    public static void checkDuplicateOption(UUID optionId) {

    }


    //getters
    public QuestionId id() {
        return id;
    }

    public String text() {
        return text;
    }

    public List<Option> options() {
        return options;
    }
}
