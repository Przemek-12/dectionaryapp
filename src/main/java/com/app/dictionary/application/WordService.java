package com.app.dictionary.application;

import com.app.dictionary.application.dto.*;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//    TODO exceptions
public class WordService {

    private final WordRepository wordRepository;

    @Transactional
    public void addWords(AddWordRequest addWordRequest) {
        if (addWordRequest.getWords().size() != new HashSet<>(addWordRequest.getWords()).size()) {
            return;
        }
        UUID sharedUUID = UUID.randomUUID();
        addWordRequest.getWords().forEach(addWordDTO -> addWord(addWordDTO, sharedUUID));
    }

//    TODO pagination
    public DictionaryListResponse getAll() {
        List<UUID> uuids = wordRepository.getDistinctUUIDs();
        List<Word> words = wordRepository.findBySharedUUIDIn(uuids);
        List<List<WordGetDTO>> dictionary = uuids.stream()
                .map(uuid -> words.stream()
                        .filter(word -> word.getSharedUUID().equals(uuid))
                        .map(word -> WordGetDTO.builder()
                                .id(word.getId())
                                .sharedUUID(word.getSharedUUID().toString())
                                .language(word.getLanguage().toString())
                                .value(word.getValue())
                                .build())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return DictionaryListResponse.builder().dictionary(dictionary).build();

    }

    public WordGetDTO translateWord(TranslationRequest translationRequest) {
        Language fromLang = Language.valueOf(translationRequest.getFromLang());
        Word wordToTranslate = wordRepository.findByLanguageAndValue(fromLang, translationRequest.getWordValue())
                .orElseThrow(()->new RuntimeException());
        Language toLang = Language.valueOf(translationRequest.getToLang());
        return wordRepository.findByLanguageAndSharedUUID(toLang, wordToTranslate.getSharedUUID())
                .map(word -> WordGetDTO.builder()
                        .id(word.getId())
                        .sharedUUID(word.getSharedUUID().toString())
                        .language(word.getLanguage().toString())
                        .value(word.getValue())
                        .build())
                .orElseThrow(()->new RuntimeException());
    }

    private void addWord(AddWordDTO addWordDTO, UUID sharedUUID) {
        Language language = Language.valueOf(addWordDTO.getLanguage());
        wordRepository.save(Word.create(sharedUUID, language, addWordDTO.getValue()));
    }


}
