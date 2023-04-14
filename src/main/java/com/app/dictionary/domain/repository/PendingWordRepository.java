package com.app.dictionary.domain.repository;

import com.app.dictionary.domain.entity.PendingWord;

import java.util.List;

public interface PendingWordRepository {

    PendingWord save(PendingWord pendingWord);
    List<PendingWord> findAll();

}
