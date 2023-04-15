package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.AddWordDTO;
import com.app.dictionary.application.dto.AddWordRequest;
import com.app.dictionary.application.pendingword.PendingWordWriteService;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordWriteServiceImpl implements WordWriteService {

    private final WordRepository wordRepository;
    private final PendingWordWriteService pendingWordWriteService;

    @Transactional
    @Override
    public void addWords(AddWordRequest addWordRequest) {
        validateRequest(addWordRequest);
        UUID sharedUUID = UUID.randomUUID();
        addWordRequest.getWords().forEach(addWordDTO -> addWord(addWordDTO, sharedUUID));
    }

    private void validateRequest(AddWordRequest addWordRequest) {
        Set<AddWordDTO> wordsSet = new HashSet<>(addWordRequest.getWords());
        List<String> languages = wordsSet.stream().map(AddWordDTO::getLanguage).toList();
        if (addWordRequest.getWords().size() != wordsSet.size()) {
            throw new IllegalArgumentException("Request contains words duplicates.");
        }
        if (new HashSet<>(languages).size() != languages.size()) {
            throw new IllegalArgumentException("Request contains languages duplicates.");
        }
        if (!allLanguagesIn(languages)) {
            throw new IllegalArgumentException("Word is not in all languages.");
        }
    }

    private boolean allLanguagesIn(Collection<String> languages) {
        return languages.containsAll(Language.strValuesAsList);
    }

    private void addWord(AddWordDTO addWordDTO, UUID sharedUUID) {
        Language language = WordUtils.toLanguage(addWordDTO.getLanguage());
        pendingWordWriteService.deletePendingWordIfExists(language, addWordDTO.getValue());
        wordRepository.save(Word.create(sharedUUID, language, addWordDTO.getValue()));
    }

}
