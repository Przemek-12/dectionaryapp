package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.AddWordDTO;
import com.app.dictionary.application.dto.AddWordRequest;
import com.app.dictionary.domain.entity.Word;
import com.app.dictionary.domain.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WordWriteServiceImplTest {

    @InjectMocks
    private WordWriteServiceImpl wordWriteService;

    @Mock
    private WordRepository wordRepository;

    @Test
    void shouldThrowExceptionWhenDuplicatesInRequest() {
        AddWordRequest addWordRequest = AddWordRequest.builder()
                .words(List.of(
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val")
                                .build(),
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val")
                                .build(),
                        AddWordDTO.builder()
                                .language("POLISH")
                                .value("val2")
                                .build()
                ))
                .build();

        assertThrows(IllegalArgumentException.class, ()-> wordWriteService.addWords(addWordRequest));
    }

    @Test
    void shouldThrowExceptionWhenWordIsNotInAllLanguages() {
        AddWordRequest addWordRequest = AddWordRequest.builder()
                .words(List.of(
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val")
                                .build()
                ))
                .build();

        assertThrows(IllegalArgumentException.class, ()-> wordWriteService.addWords(addWordRequest));
    }

    @Test
    void shouldThrowExceptionWhenLanguagesDuplicated() {
        AddWordRequest addWordRequest = AddWordRequest.builder()
                .words(List.of(
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val")
                                .build(),
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val2")
                                .build(),
                        AddWordDTO.builder()
                                .language("POLISH")
                                .value("val3")
                                .build()
                ))
                .build();

        assertThrows(IllegalArgumentException.class, ()-> wordWriteService.addWords(addWordRequest));
    }

    @Test
    void shouldAddWords() {
        AddWordRequest addWordRequest = AddWordRequest.builder()
                .words(List.of(
                        AddWordDTO.builder()
                                .language("ENGLISH")
                                .value("val")
                                .build(),
                        AddWordDTO.builder()
                                .language("POLISH")
                                .value("val2")
                                .build()
                ))
                .build();

        wordWriteService.addWords(addWordRequest);

        verify(wordRepository, times(2)).save(any(Word.class));
    }

}