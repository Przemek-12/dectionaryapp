package com.app.dictionary.application.report;

import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.application.word.WordReadService;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.repository.PendingWordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private WordReadService wordReadService;

    @Mock
    private PendingWordRepository pendingWordRepository;

    @Test
    void shouldGenerateReport() {
        UUID uuid_1 = UUID.randomUUID();
        UUID uuid_2 = UUID.randomUUID();
        UUID uuid_3 = UUID.randomUUID();
        UUID uuid_4 = UUID.randomUUID();

        DictionaryListResponse dictResponsePage0 = DictionaryListResponse.builder()
                .itemsOnPage(2)
                .totalPages(2)
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
        DictionaryListResponse dictResponsePage1 = DictionaryListResponse.builder()
                .itemsOnPage(2)
                .totalPages(2)
                .dictionary(List.of(
                        List.of(
                                WordGetDTO.builder()
                                        .value("wordsdf")
                                        .language(Language.POLISH.toString())
                                        .sharedUUID(uuid_3.toString())
                                        .build(),
                                WordGetDTO.builder()
                                        .value("wordsdf")
                                        .language(Language.ENGLISH.toString())
                                        .sharedUUID(uuid_3.toString())
                                        .build()
                        ),
                        List.of(
                                WordGetDTO.builder()
                                        .value("wodafgh")
                                        .language(Language.POLISH.toString())
                                        .sharedUUID(uuid_4.toString())
                                        .build(),
                                WordGetDTO.builder()
                                        .value("worafgh")
                                        .language(Language.ENGLISH.toString())
                                        .sharedUUID(uuid_4.toString())
                                        .build()
                        )
                ))
                .build();

        when(wordReadService.getAllWordsCount()).thenReturn(10);
        when(pendingWordRepository.countRows()).thenReturn(10);
        when(wordReadService.getAll(5, 0)).thenReturn(dictResponsePage0);
        when(wordReadService.getAll(5, 1)).thenReturn(dictResponsePage1);

        Report expectedReport = Report.builder()
                .allWordsCount(10)
                .pendingWordsCount(10)
                .wordsByLengthByLangs(Map.of(
                        "POLISH", Map.of(
                                4,1,
                                7,2,
                                5,1
                        ),
                        "ENGLISH", Map.of(
                                4,1,
                                7,2,
                                5,1
                        )
                ))
                .wordsAvgLengthByLangs(Map.of(
                        "POLISH", 5.75F,
                        "ENGLISH", 5.75F
                ))
                .build();

        Report generatedReport = reportService.generateReport();

        assertEquals(expectedReport, generatedReport);
    }

}