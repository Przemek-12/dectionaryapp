package com.app.dictionary.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void shouldReturnFalseWhenNotContainsLang() {
        assertFalse(Language.contains("qwe"));
    }

    @Test
    void shouldReturnTrueWhenContainsLang() {
        assertTrue(Language.contains("POLISH"));
    }

}