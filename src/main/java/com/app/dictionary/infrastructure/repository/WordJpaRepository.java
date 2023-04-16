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

    @Query(
            value = "SELECT DISTINCT shared_uuid FROM word order by id limit ?1 offset ?2",
            nativeQuery = true)
    List<UUID> getDistinctUUIDs(int limit, int offset);

    @Query(
            value = "SELECT COUNT(DISTINCT shared_uuid) FROM word",
            nativeQuery = true)
    int countDistinctUUIDs();
}
