package com.app.dictionary.infrastructure.repository;

import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WordJpaRepository extends WordRepository, JpaRepository<Word, Long> {

    @Query("SELECT DISTINCT w.sharedUUID FROM Word w")
    List<UUID> getDistinctUUIDs();

}
