package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.application.exception.EntityObjectNotFoundException;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordReadServiceImplTest {

    @InjectMocks
    private WordReadServiceImpl wordReadService;

    @Mock
    private WordRepository wordRepository;

    @Test
    void shouldGetAll() {
        UUID uuid_1 = UUID.randomUUID();
        UUID uuid_2 = UUID.randomUUID();
        List<UUID> uuids = List.of(uuid_1, uuid_2);
        List<Word> words = List.of(
                Word.create(uuid_1, Language.POLISH, "word"),
                Word.create(uuid_1, Language.ENGLISH, "word"),
                Word.create(uuid_2, Language.POLISH, "worda"),
                Word.create(uuid_2, Language.ENGLISH, "worda")
        );

        when(wordRepository.getDistinctUUIDs(2, 4)).thenReturn(uuids);
        when(wordRepository.countDistinctUUIDs()).thenReturn(7);
        when(wordRepository.findBySharedUUIDIn(uuids)).thenReturn(words);

        DictionaryListResponse response = wordReadService.getAll(2, 2);

        DictionaryListResponse expectedResponse = DictionaryListResponse.builder()
                .itemsOnPage(2)
                .totalPages(4)
                .dictionary(List.of(
                        List.of(
                                WordGetDTO.builder()
                                        .value("word")
                                        .language(Language.POLISH.toString())
                                        .sharedUUID(uuid_1.toString())
                                        .build(),
                                WordGetDTO.builder()
                                        .value("word")
                                        .language(Language.ENGLISH.toString())
                                        .sharedUUID(uuid_1.toString())
                                        .build()
                        ),
                        List.of(
                                WordGetDTO.builder()
                                        .value("worda")
                                        .language(Language.POLISH.toString())
                                        .sharedUUID(uuid_2.toString())
                                        .build(),
                                WordGetDTO.builder()
                                        .value("worda")
                                        .language(Language.ENGLISH.toString())
                                        .sharedUUID(uuid_2.toString())
                                        .build()
                        )
                ))
                .build();

        assertEquals(expectedResponse, response);
    }

    @Test
    void shouldFindWordByLanguageAndValue() {
        when(wordRepository.findByLanguageAndValue(any(Language.class), anyString()))
                .thenReturn(Optional.of(Word.create(UUID.randomUUID(), Language.POLISH, "val")));

        assertNotNull(wordReadService.findWordByLanguageAndValueOrElseThrowException(Language.POLISH, "val"));
    }

    @Test
    void shouldThrowExceptionWhenWordByLanguageAndValueNotFound() {
        when(wordRepository.findByLanguageAndValue(any(Language.class), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(EntityObjectNotFoundException.class,
                () -> wordReadService.findWordByLanguageAndValueOrElseThrowException(Language.POLISH, "val"));
    }

    @Test
    void shouldFindWordByLanguageAndSharedUUID() {
        when(wordRepository.findByLanguageAndSharedUUID(any(Language.class), any(UUID.class)))
                .thenReturn(Optional.of(Word.create(UUID.randomUUID(), Language.POLISH, "val")));

        assertNotNull(wordReadService.findWordByLanguageAndSharedUUIDOrElseThrowException(Language.POLISH,
                UUID.randomUUID()));
    }

    @Test
    void shouldThrowExceptionWhenWordByLanguageAndSharedUUIDNotFound() {
        when(wordRepository.findByLanguageAndSharedUUID(any(Language.class), any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(EntityObjectNotFoundException.class,
                () -> wordReadService
                        .findWordByLanguageAndSharedUUIDOrElseThrowException(Language.POLISH, UUID.randomUUID()));
    }

}