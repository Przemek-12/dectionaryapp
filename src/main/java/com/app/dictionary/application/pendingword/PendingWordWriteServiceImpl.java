package com.app.dictionary.application.pendingword;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PendingWordWriteServiceImpl implements PendingWordWriteService {

    private final PendingWordRepository pendingWordRepository;

    @Override
    public void addPendingWord(Language lang, String word) {
        if(!pendingWordRepository.existsByLanguageAndValue(lang, word)) {
            pendingWordRepository.save(PendingWord.create(lang, word));
            log.info(String.format("New PendingWord added. lang: %s, value: %s", lang, word));
        }
    }

    @Override
    public void deletePendingWordIfExists(Language lang, String word) {
        if(pendingWordRepository.existsByLanguageAndValue(lang, word)) {
            pendingWordRepository.deleteByLanguageAndValue(lang, word);
            log.info(String.format("PendingWord deleted. lang: %s, value: %s", lang, word));
        }
    }

}
