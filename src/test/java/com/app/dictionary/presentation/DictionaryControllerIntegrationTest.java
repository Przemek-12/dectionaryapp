package com.app.dictionary.presentation;

import com.app.dictionary.DictionaryApplication;
import com.app.dictionary.TestUtils;
import com.app.dictionary.application.dto.*;
import com.app.dictionary.domain.entity.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DictionaryApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class DictionaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_addWord_getAll_translate_report() throws Exception {
        testAddWord();
        testGetAllWords();
        testTranslate();
        testReport();
    }

    private void testAddWord() throws Exception {
        AddWordRequest addWordRequest = AddWordRequest.builder()
                .words(List.of(
                        AddWordDTO.builder()
                                .language(Language.POLISH.toString())
                                .value("wordPol")
                                .build(),
                        AddWordDTO.builder()
                                .language(Language.ENGLISH.toString())
                                .value("wordEng")
                                .build()
                ))
                .build();
        
        mockMvc.perform(post("/dictionary/word")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.mapToJson(addWordRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    private void testGetAllWords() throws Exception {
        String dictAllResponse = mockMvc.perform(get("/dictionary/word/all")
                        .queryParam("itemsPerPage", "1")
                        .queryParam("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DictionaryListResponse dictResponse = TestUtils.mapFromJson(dictAllResponse, DictionaryListResponse.class);

        assertEquals(1, dictResponse.getItemsOnPage());
        assertEquals(1, dictResponse.getTotalPages());

        assertThat(dictResponse.getDictionary().get(0).stream()
                .map(WordGetDTO::getValue)
                .collect(Collectors.toList())).containsAll(List.of("wordPol", "wordEng"));
        assertThat(dictResponse.getDictionary().get(0).stream()
                .map(WordGetDTO::getLanguage)
                .collect(Collectors.toList())).containsAll(List.of("POLISH", "ENGLISH"));
    }

    private void testTranslate() throws Exception {
        TranslationRequest translationRequest = TranslationRequest.builder()
                .value("wordPol")
                .fromLang("POLISH")
                .toLang("ENGLISH")
                .build();
        
        String response = mockMvc.perform(post("/dictionary/translate/word")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.mapToJson(translationRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        WordGetDTO wordGetDTO = TestUtils.mapFromJson(response, WordGetDTO.class);

        assertEquals("ENGLISH", wordGetDTO.getLanguage());
        assertEquals("wordEng", wordGetDTO.getValue());
    }

    private void testReport() throws Exception {
        String response = mockMvc.perform(get("/dictionary/report"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        Report report = TestUtils.mapFromJson(response, Report.class);

        Report expectedReport = Report.builder()
                .allWordsCount(1)
                .pendingWordsCount(0)
                .wordsByLengthByLangs(Map.of(
                        "POLISH", Map.of(
                                7,1
                        ),
                        "ENGLISH", Map.of(
                                7,1
                        )
                ))
                .wordsAvgLengthByLangs(Map.of(
                        "POLISH", 7F,
                        "ENGLISH", 7F
                ))
                .build();
        
        assertEquals(expectedReport, report);
    }
}