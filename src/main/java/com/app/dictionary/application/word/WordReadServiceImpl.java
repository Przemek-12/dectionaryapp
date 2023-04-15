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
    public DictionaryListResponse getAll(int itemsPerPage, int page) {
        int offset = page * itemsPerPage;
        List<UUID> uuids = wordRepository.getDistinctUUIDs(itemsPerPage, offset);
        List<List<WordGetDTO>> dictionary = getDictionary(uuids);
        int totalCount = wordRepository.countDistinctUUIDs();
        int totalPages = totalCount/itemsPerPage;
        if(totalCount % itemsPerPage != 0){
            totalPages++;
        }
        return DictionaryListResponse.builder()
                .itemsOnPage(uuids.size())
                .totalPages(totalPages)
                .dictionary(dictionary)
                .build();
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

    @Override
    public int getAllWordsCount() {
        return wordRepository.countDistinctUUIDs();
    }

    private List<List<WordGetDTO>> getDictionary(List<UUID> uuids) {
        List<Word> words = wordRepository.findBySharedUUIDIn(uuids);
        return uuids.stream()
                .map(uuid -> words.stream()
                        .filter(word -> word.getSharedUUID().equals(uuid))
                        .map(WordUtils::mapToWordGetDTO)
                        .toList())
                .toList();
    }
}
