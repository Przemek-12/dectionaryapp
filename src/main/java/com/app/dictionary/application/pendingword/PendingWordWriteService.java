package com.app.dictionary.application.pendingword;

import com.app.dictionary.domain.entity.Language;

public interface PendingWordWriteService {
    void addPendingWord(Language lang, String word);

    void deletePendingWordIfExists(Language lang, String word);
}
