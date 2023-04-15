package com.app.dictionary.presentation;

import com.app.dictionary.DictionaryApplication;
import com.app.dictionary.TestUtils;
import com.app.dictionary.application.dto.PendingWordAllResponse;
import com.app.dictionary.application.dto.PendingWordDTO;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(classes = DictionaryApplication.class)
@AutoConfigureMockMvc
@Transactional
class PendingWordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PendingWordRepository pendingWordRepository;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        pendingWordRepository.save(PendingWord.create(Language.POLISH, "word"));

        String response = mockMvc.perform(get("/pending-word/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PendingWordAllResponse expectedResponse =PendingWordAllResponse.builder()
                .words(List.of(PendingWordDTO.builder()
                        .language("POLISH")
                        .value("word")
                        .build()))
                .build();

        assertEquals(expectedResponse, TestUtils.mapFromJson(response, PendingWordAllResponse.class));
    }

}