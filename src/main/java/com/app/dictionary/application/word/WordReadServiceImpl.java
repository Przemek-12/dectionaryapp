package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.*;
import com.app.dictionary.application.exception.EntityObjectNotFoundException;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WordReadServiceImpl implements WordReadService {

    private final WordRepository wordRepository;

    @Override
    //    TODO pagination
    public DictionaryListResponse getAll() {
        List<UUID> uuids = wordRepository.getDistinctUUIDs();
        List<Word> words = wordRepository.findBySharedUUIDIn(uuids);
        List<List<WordGetDTO>> dictionary = uuids.stream()
                .map(uuid -> words.stream()
                        .filter(word -> word.getSharedUUID().equals(uuid))
                        .map(WordUtils::mapToWordGetDTO)
                        .toList())
                .toList();
        return DictionaryListResponse.builder().dictionary(dictionary).build();

    }

    @Override
    public Word findWordByLanguageAndValueOrElseThrowException(Language language, String value) {
        return wordRepository.findByLanguageAndValue(language, value)
                .orElseThrow(() -> new EntityObjectNotFoundException(Word.class,
                        "Word to translate not exists. val: " + value));
    }

    @Override
    public Word findWordByLanguageAndSharedUUIDOrElseThrowException(Language language, UUID uuid) {
        return wordRepository.findByLanguageAndSharedUUID(language, uuid)
                .orElseThrow(() -> new EntityObjectNotFoundException(Word.class, "Translated word not exists."));
    }

    @Override
    public Optional<Word> findWordByLanguageAndValueOpt(Language language, String value) {
        return wordRepository.findByLanguageAndValue(language, value);
    }

}
