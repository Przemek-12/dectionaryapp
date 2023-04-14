package com.app.dictionary.domain.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Language {

    POLISH, ENGLISH;

    public static final List<String> strValuesAsList = Arrays.stream(Language.values()).map(Objects::toString).toList();

    public static boolean contains(String value) {
        return strValuesAsList.stream().anyMatch(lang->lang.equals(value));
    }

}
