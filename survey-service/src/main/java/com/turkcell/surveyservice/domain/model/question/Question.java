package com.turkcell.surveyservice.domain.model.question;

import com.turkcell.surveyservice.domain.exception.DuplicateOptionTextException;
import com.turkcell.surveyservice.domain.exception.OptionLimitExceededException;
import com.turkcell.surveyservice.domain.model.option.Option;
import com.turkcell.surveyservice.domain.model.option.OptionId;

import java.util.Collections;
import java.util.List;

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

    //domain behaviors
    public void addOption(String text) {
        checkOptionSize(options);
        checkDuplicateOption(text);
        options.add(new Option(OptionId.generate(), text));
    }

    public void removeOption(OptionId optionId) {
        options.removeIf(option -> option.id().equals(optionId));
    }


    //aynı question içerisinde duplicate option olamaz.
    public void checkDuplicateOption(String optionText) {
        for (Option option : options){
            if (option.text().equalsIgnoreCase(optionText)) {
                throw new DuplicateOptionTextException("Duplicate option text: " + optionText);
            }
        }
    }

    //domain invariants-iş kuralı
    //option sayısı 10dan büyük olamaz.
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


    //getters
    public QuestionId id() {
        return id;
    }

    public String text() {
        return text;
    }

    public List<Option> options() {
        return Collections.unmodifiableList(options);
    }
}
