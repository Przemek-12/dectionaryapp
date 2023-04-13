package com.app.dictionary.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class AddWordDTO {

    private String language;
    private String value;

}
