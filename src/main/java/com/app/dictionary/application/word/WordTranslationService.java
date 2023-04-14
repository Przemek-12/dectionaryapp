package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.TranslationRequest;
import com.app.dictionary.application.dto.WordGetDTO;

public interface WordTranslationService {

    WordGetDTO translateWord(TranslationRequest build);
    String translateSentence(TranslationRequest translationRequest);

}
