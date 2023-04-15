package com.app.dictionary.application.dto;

import lombok.*;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Report {

    private int allWordsCount;
    private int pendingWordsCount;
    private Map<String, Map<Integer, Integer>> wordsByLengthByLangs;
    private Map<String, Float> wordsAvgLengthByLangs;

}
