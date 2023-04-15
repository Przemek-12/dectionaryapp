package com.app.dictionary.application.report;

import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.application.dto.FileFormat;
import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.report.reportfilegenerator.ReportFileGeneratorFactory;
import com.app.dictionary.application.word.WordReadService;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.repository.PendingWordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private static final int itemsPerPage = 5;

    private final WordReadService wordReadService;
    private final PendingWordRepository pendingWordRepository;
    private final ReportFileGeneratorFactory reportFileGeneratorFactory;

    @Override
    public Report generateReport() {
        log.info("Generation of report started.");
        int allWordsCount = wordReadService.getAllWordsCount();
        int pendingWordsCount = pendingWordRepository.countRows();
        Map<String, Map<Integer, Integer>> wordsByLengthByLangs = getWordsByLengthByLangs();
        Map<String, Float> wordsAvgLengthByLangs = getWordsAvgLengthByLangs(wordsByLengthByLangs);
        log.info("Generation of report finished.");
        return Report.builder()
                .allWordsCount(allWordsCount)
                .pendingWordsCount(pendingWordsCount)
                .wordsByLengthByLangs(wordsByLengthByLangs)
                .wordsAvgLengthByLangs(wordsAvgLengthByLangs)
                .build();
    }

    @Override
    public Resource generateReportAsFile(FileFormat fileFormat) {
        return reportFileGeneratorFactory.getReportFileGenerator(fileFormat).generateReport(generateReport());
    }

    private Map<String, Map<Integer, Integer>> getWordsByLengthByLangs() {
        Map<String, Map<Integer, Integer>> wordsByLengthByLangs = new HashMap<>();
        Language.strValuesAsList.forEach(lang -> wordsByLengthByLangs.put(lang, new HashMap<>()));
        DictionaryListResponse firstPage = wordReadService.getAll(itemsPerPage, 0);
        populateWordsByLengthByLangs(wordsByLengthByLangs, firstPage);
        int totalPages = firstPage.getTotalPages();
        if (totalPages > 1) {
            for (int i = 1; i < totalPages; i++) {
                DictionaryListResponse page = wordReadService.getAll(itemsPerPage, i);
                populateWordsByLengthByLangs(wordsByLengthByLangs, page);
            }
        }
        return wordsByLengthByLangs;
    }

    private void populateWordsByLengthByLangs(Map<String, Map<Integer, Integer>> wordsByLengthByLangs,
                                              DictionaryListResponse dictionaryPage) {
        dictionaryPage.getDictionary().forEach(words -> words.forEach(word -> {
            Map<Integer, Integer> wordsByLength = wordsByLengthByLangs.get(word.getLanguage());
            int wordLength = word.getValue().length();
            if (wordsByLength.containsKey(wordLength)) {
                int wordLengthCount = wordsByLength.get(wordLength);
                wordLengthCount++;
                wordsByLength.put(wordLength, wordLengthCount);
            } else {
                wordsByLength.put(wordLength, 1);
            }
        }));
    }


    private Map<String, Float> getWordsAvgLengthByLangs(Map<String, Map<Integer, Integer>> wordsByLengthByLangs) {
        Map<String, Float> wordsAvgLengthByLangs = new HashMap<>();
        wordsByLengthByLangs.forEach((key, value) -> {
            int totalLengthOfAllWords = value.entrySet().stream()
                    .map(wordsByLengthEntry -> wordsByLengthEntry.getKey() * wordsByLengthEntry.getValue())
                    .reduce(0, Integer::sum);
            int totalWordsCount = value.values().stream().reduce(0, Integer::sum);
            float avgLength = (float) totalLengthOfAllWords / (float) totalWordsCount;
            wordsAvgLengthByLangs.put(key, avgLength);
        });
        return wordsAvgLengthByLangs;
    }

}
