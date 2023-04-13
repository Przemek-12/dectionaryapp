package com.app.dictionary.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class WordGetDTO {

    private Long id;
    private String sharedUUID;
    private String language;
    private String value;
}
