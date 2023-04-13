package com.app.dictionary.domain.repository;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordRepository {

    Word save(Word word);
    List<Word> findBySharedUUIDIn(List<UUID> uuids);
    List<UUID> getDistinctUUIDs();
    Optional<Word> findByLanguageAndValue(Language language, String value);
    Optional<Word> findByLanguageAndSharedUUID(Language language, UUID sharedUUID);
}
