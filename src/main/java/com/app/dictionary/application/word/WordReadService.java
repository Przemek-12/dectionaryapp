package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;

import java.util.Optional;
import java.util.UUID;

public interface WordReadService {

    DictionaryListResponse getAll(int itemsPerPage, int page);

    Word findWordByLanguageAndValueOrElseThrowException(Language language, String value);

    Word findWordByLanguageAndSharedUUIDOrElseThrowException(Language language, UUID uuid);

    Optional<Word> findWordByLanguageAndValueOpt(Language language, String value);

    int getAllWordsCount();
}
