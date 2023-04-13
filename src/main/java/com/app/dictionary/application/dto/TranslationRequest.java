package com.app.dictionary.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TranslationRequest {

    private String fromLang;
    private String toLang;
    private String wordValue;

}
