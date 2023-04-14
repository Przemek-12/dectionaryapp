package com.app.dictionary.infrastructure.repository;

import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingWordJpaRepository extends PendingWordRepository, JpaRepository<PendingWord, Long> {
}
