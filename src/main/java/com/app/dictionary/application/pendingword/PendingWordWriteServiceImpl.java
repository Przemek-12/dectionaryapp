package com.app.dictionary.application.pendingword;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PendingWordWriteServiceImpl implements PendingWordWriteService {

    private final PendingWordRepository pendingWordRepository;

    @Override
    public void addPendingWord(Language lang, String word) {
        pendingWordRepository.save(PendingWord.create(lang, word));
    }

}
