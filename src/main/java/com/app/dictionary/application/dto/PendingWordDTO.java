package com.app.dictionary.application.dto;

import com.app.dictionary.domain.entity.Language;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class PendingWordDTO {

    private String language;
    private String value;
}
