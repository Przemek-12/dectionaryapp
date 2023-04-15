package com.app.dictionary.application.pendingword;

import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PendingWordWriteServiceImplTest {

    @InjectMocks
    private PendingWordWriteServiceImpl pendingWordWriteService;

    @Mock
    private PendingWordRepository pendingWordRepository;

    private void pendingWord_existsByLanguageAndValue(boolean bool) {
        when(pendingWordRepository.existsByLanguageAndValue(any(Language.class), anyString())).thenReturn(bool);
    }

    @Test
    void shouldAddPendingWord() {
        pendingWord_existsByLanguageAndValue(false);

        pendingWordWriteService.addPendingWord(Language.POLISH, "val");

        verify(pendingWordRepository, times(1)).save(any(PendingWord.class));
    }

    @Test
    void shouldNotAddPendingWordIfAlreadyExists() {
        pendingWord_existsByLanguageAndValue(true);

        pendingWordWriteService.addPendingWord(Language.POLISH, "val");

        verify(pendingWordRepository, never()).save(any());
    }

    @Test
    void shouldDeletePendingWord() {
        pendingWord_existsByLanguageAndValue(true);

        pendingWordWriteService.deletePendingWordIfExists(Language.POLISH, "val");

        verify(pendingWordRepository, times(1))
                .deleteByLanguageAndValue(any(Language.class), anyString());
    }

    @Test
    void shouldNotDeletePendingWordIfNotExists() {
        pendingWord_existsByLanguageAndValue(false);

        pendingWordWriteService.deletePendingWordIfExists(Language.POLISH, "val");

        verify(pendingWordRepository, never()).deleteByLanguageAndValue(any(), any());
    }

}