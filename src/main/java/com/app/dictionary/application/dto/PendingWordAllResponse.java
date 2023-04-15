package com.app.dictionary.application.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class PendingWordAllResponse {

    private List<PendingWordDTO> words;

}
