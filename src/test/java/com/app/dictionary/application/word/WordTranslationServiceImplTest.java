package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.TranslationRequest;
import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.application.pendingword.PendingWordWriteService;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordTranslationServiceImplTest {

    @InjectMocks
    private WordTranslationServiceImpl wordTranslationService;

    @Mock
    private PendingWordWriteService pendingWordWriteService;

    @Mock
    private WordReadService wordReadService;

    private static final UUID UUID_1 = UUID.randomUUID();
    private static final UUID UUID_2 = UUID.randomUUID();
    private static final UUID UUID_3 = UUID.randomUUID();
    private static final UUID UUID_4 = UUID.randomUUID();

    private static final Map<Language, Map<UUID, String>> DICTIONARY = Map.of(
            Language.POLISH, Map.of(
                    UUID_1, "worda",
                    UUID_2, "wordc",
                    UUID_3, "wordf",
                    UUID_4, "wordh"
            ),
            Language.ENGLISH, Map.of(
                    UUID_1, "wordb",
                    UUID_2, "wordd",
                    UUID_3, "wordg",
                    UUID_4, "wordj"
            )
    );

    @Test
    void shouldTranslateWord() {
        final String expectedWord = "translated";

        when(wordReadService.findWordByLanguageAndValueOrElseThrowException(any(Language.class), anyString()))
                .thenReturn(Word.create(UUID.randomUUID(), Language.ENGLISH, "val"));
        when(wordReadService.findWordByLanguageAndSharedUUIDOrElseThrowException(any(Language.class), any(UUID.class)))
                .thenReturn(Word.create(UUID.randomUUID(), Language.POLISH, expectedWord));

        TranslationRequest translationRequest = TranslationRequest.builder()
                .toLang(Language.POLISH.toString())
                .fromLang(Language.ENGLISH.toString())
                .value("val")
                .build();

        WordGetDTO response = wordTranslationService.translateWord(translationRequest);

        assertThat(response.getValue()).isEqualTo(expectedWord);
    }

    @Test
    void shouldTranslateSentence() {
        final String sentenceToTranslate = "wordaaaa, worda-wordc, wordaaaaqwe wordf/ wordh.";
        final String expectedTranslation = "wordaaaa, wordb-wordd, wordaaaaqwe wordg/ wordj.";

        TranslationRequest translationRequest = TranslationRequest.builder()
                .fromLang(Language.POLISH.toString())
                .toLang(Language.ENGLISH.toString())
                .value(sentenceToTranslate)
                .build();

        when(wordReadService.findWordByLanguageAndValueOpt(any(Language.class), anyString()))
                .thenAnswer(i -> {
                    Language lang = i.getArgument(0);
                    String wordVal = i.getArgument(1);
                    if (DICTIONARY.get(lang).containsValue(wordVal)) {
                        UUID uuid = DICTIONARY.get(lang)
                                .entrySet()
                                .stream()
                                .filter(entry -> wordVal.equals(entry.getValue()))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .get();
                        return Optional.of(Word.create(uuid, lang, wordVal));
                    }
                    return Optional.empty();
                });

        when(wordReadService.findWordByLanguageAndSharedUUIDOrElseThrowException(any(Language.class), any(UUID.class)))
                .thenAnswer(i -> {
                    Language lang = i.getArgument(0);
                    UUID uuid = i.getArgument(1);
                    return Word.create(uuid, lang, DICTIONARY.get(lang).get(uuid));
                });


        String response = wordTranslationService.translateSentence(translationRequest);

        assertThat(response).isEqualTo(expectedTranslation);
        verify(pendingWordWriteService, times(2))
                .addPendingWord(any(), any());
        verify(pendingWordWriteService, times(1))
                .addPendingWord(eq(Language.POLISH), argThat(arg -> arg.equals("wordaaaa")));
        verify(pendingWordWriteService, times(1))
                .addPendingWord(eq(Language.POLISH), argThat(arg -> arg.equals("wordaaaaqwe")));
    }

}