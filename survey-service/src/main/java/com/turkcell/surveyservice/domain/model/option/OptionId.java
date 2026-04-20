package com.turkcell.surveyservice.domain.model.option;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record OptionId(UUID value) implements Serializable {
    public OptionId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static OptionId generate() {
        return new OptionId(UUID.randomUUID());
    }
}
