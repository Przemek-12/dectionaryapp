package com.app.dictionary.domain.repository;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.PendingWord;

import java.util.List;
import java.util.Optional;

public interface PendingWordRepository {

    PendingWord save(PendingWord pendingWord);
    List<PendingWord> findAll();
    boolean existsByLanguageAndValue(Language language, String value);
    void deleteByLanguageAndValue(Language language, String value);
    int countRows();

}
