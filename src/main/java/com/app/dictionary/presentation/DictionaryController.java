package com.app.dictionary.presentation;

import com.app.dictionary.application.word.WordReadService;
import com.app.dictionary.application.dto.AddWordRequest;
import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.application.dto.TranslationRequest;
import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.application.word.WordTranslationService;
import com.app.dictionary.application.word.WordWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dictionary")
@RestController
@RequiredArgsConstructor
public class DictionaryController {

    private final WordWriteService wordWriteService;
    private final WordReadService wordReadService;
    private final WordTranslationService wordTranslationService;

    @PostMapping("/word")
    public void addWord(@RequestBody AddWordRequest addWordRequest) {
        wordWriteService.addWords(addWordRequest);
    }

    @PostMapping("/translate/word")
    public WordGetDTO translate(@RequestBody TranslationRequest translationRequest) {
        return wordTranslationService.translateWord(translationRequest);
    }

    @PostMapping("/translate/sentence")
    public Object translateSentence(@RequestBody TranslationRequest translationRequest) {
        return wordTranslationService.translateSentence(translationRequest);
    }

    @GetMapping("/all")
    public DictionaryListResponse getAll() {
        return wordReadService.getAll();
    }
//
//    public Object getReport() {
//
//    }
//
//    public Object getReportAsPDF() {
//
//    }


}
