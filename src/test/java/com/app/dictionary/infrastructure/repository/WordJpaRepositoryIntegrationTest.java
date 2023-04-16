package com.app.dictionary.infrastructure.repository;

import com.app.dictionary.DictionaryApplication;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DictionaryApplication.class)
@Transactional
@ActiveProfiles("test")
class WordJpaRepositoryIntegrationTest {

    @Autowired
    private WordRepository wordJpaRepository;

    @Test
    void shouldGetDistinctUUIDs() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        UUID uuid5 = UUID.randomUUID();
        UUID uuid6 = UUID.randomUUID();

        wordJpaRepository.save(Word.create(uuid1, Language.POLISH, "worda"));
        wordJpaRepository.save(Word.create(uuid1, Language.ENGLISH, "wordb"));
        wordJpaRepository.save(Word.create(uuid2, Language.POLISH, "wordc"));
        wordJpaRepository.save(Word.create(uuid2, Language.ENGLISH, "wordd"));
        wordJpaRepository.save(Word.create(uuid3, Language.POLISH, "worde"));
        wordJpaRepository.save(Word.create(uuid3, Language.ENGLISH, "wordr"));
        wordJpaRepository.save(Word.create(uuid4, Language.POLISH, "wordt"));
        wordJpaRepository.save(Word.create(uuid4, Language.ENGLISH, "wordy"));
        wordJpaRepository.save(Word.create(uuid5, Language.POLISH, "wordi"));
        wordJpaRepository.save(Word.create(uuid5, Language.ENGLISH, "woror"));
        wordJpaRepository.save(Word.create(uuid6, Language.POLISH, "wordk"));
        wordJpaRepository.save(Word.create(uuid6, Language.ENGLISH, "wordl"));

        assertEquals(List.of(uuid1, uuid2), wordJpaRepository.getDistinctUUIDs(2, 0));
        assertEquals(wordJpaRepository.getDistinctUUIDs(2, 2), List.of(uuid3, uuid4));
        assertEquals(wordJpaRepository.getDistinctUUIDs(2, 4), List.of(uuid5, uuid6));
    }

}