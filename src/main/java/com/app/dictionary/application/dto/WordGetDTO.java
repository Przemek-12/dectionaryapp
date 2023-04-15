package com.app.dictionary.application.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class WordGetDTO {

    private Long id;
    private String sharedUUID;
    private String language;
    private String value;
}
