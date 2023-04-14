package com.app.dictionary.application.word;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WordUtilsTest {

    @Test
    void shouldMapToLanguage() {
        assertNotNull(WordUtils.toLanguage("POLISH"));
    }

    @Test
    void shouldThrowExceptionWhenMapToLanguage() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.toLanguage("qwe"));
    }

    @Test
    void shouldMapToWordGetDTO() {
        assertNotNull(WordUtils.mapToWordGetDTO(Word.create(UUID.randomUUID(), Language.POLISH, "val")));
    }

}