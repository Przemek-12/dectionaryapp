package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WordUtils {

    protected Language toLanguage(String lang) {
        if (!Language.contains(lang)) {
            throw new IllegalArgumentException("Invalid language.");
        }
        return Language.valueOf(lang);
    }

    protected WordGetDTO mapToWordGetDTO(Word word) {
        return WordGetDTO.builder()
                .id(word.getId())
                .sharedUUID(word.getSharedUUID().toString())
                .language(word.getLanguage().toString())
                .value(word.getValue())
                .build();
    }

}
