package com.turkcell.surveyservice.domain.model.question;

import com.turkcell.surveyservice.domain.exception.DuplicateOptionTextException;
import com.turkcell.surveyservice.domain.exception.InsufficientOptionsException;
import com.turkcell.surveyservice.domain.exception.OptionLimitExceededException;
import com.turkcell.surveyservice.domain.exception.OptionNotFoundException;
import com.turkcell.surveyservice.domain.model.option.Option;
import com.turkcell.surveyservice.domain.model.option.OptionId;

import java.util.ArrayList;
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
        this.options = new ArrayList<>(options);
    }

    // domain behaviors
    public void addOption(String text) {
        checkOptionSize();
        checkDuplicateOption(text);
        options.add(new Option(OptionId.generate(), text));
    }

    public void removeOption(OptionId optionId) {
        boolean removed = options.removeIf(option -> option.id().equals(optionId));
        if (!removed) {
            throw new OptionNotFoundException("Option not found!");
        }
    }

    public void ensurePublishable() {
        if (this.options.size() < 2) {
            throw new InsufficientOptionsException("Question must contain at least 2 options");
        }
    }

    // domain invariants-iş kuralı
    // question metni boş olamaz.
    public static void validateQuestionText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Question text must not be null or empty!");
        }
    }

    //helper methods
    // option sayısı 10dan büyük olamaz.
    private void checkOptionSize() {
        if (this.options.size() >= 10) {
            throw new OptionLimitExceededException("Question has more than 10 options");
        }
    }

    // aynı question içerisinde duplicate option olamaz.
    private void checkDuplicateOption(String optionText) {
        for (Option option : options) {
            if (option.text().equals(optionText)) {
                throw new DuplicateOptionTextException("Duplicate option.");
            }
        }
    }

    // getters
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
