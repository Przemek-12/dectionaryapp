package com.app.dictionary.presentation;

import com.app.dictionary.application.WordService;
import com.app.dictionary.application.dto.AddWordRequest;
import com.app.dictionary.application.dto.DictionaryListResponse;
import com.app.dictionary.application.dto.TranslationRequest;
import com.app.dictionary.application.dto.WordGetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dictionary")
@RestController
@RequiredArgsConstructor
public class DictionaryController {

    private final WordService wordService;

    @GetMapping("/translate")
    public WordGetDTO translate(@RequestParam String fromLang, @RequestParam String toLang,
                                @RequestParam String wordValue) {
        return wordService.translateWord(TranslationRequest.builder()
                        .fromLang(fromLang)
                        .toLang(toLang)
                        .wordValue(wordValue)
                .build());
    }
//
//    public Object translateSentence() {
//
//    }

    @PostMapping("/word")
    public void addWord(@RequestBody AddWordRequest addWordRequest) {
        wordService.addWords(addWordRequest);
    }

    @GetMapping("/all")
    public DictionaryListResponse getAll() {
        return wordService.getAll();
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
