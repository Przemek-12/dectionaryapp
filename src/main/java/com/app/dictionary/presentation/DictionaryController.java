package com.app.dictionary.presentation;

import com.app.dictionary.application.dto.*;
import com.app.dictionary.application.report.ReportService;
import com.app.dictionary.application.word.WordReadService;
import com.app.dictionary.application.word.WordTranslationService;
import com.app.dictionary.application.word.WordWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/dictionary")
@RestController
@RequiredArgsConstructor
public class DictionaryController {

    private final WordWriteService wordWriteService;
    private final WordReadService wordReadService;
    private final WordTranslationService wordTranslationService;
    private final ReportService reportService;

    @PostMapping("/word")
    public void addWord(@RequestBody AddWordRequest addWordRequest) {
        wordWriteService.addWords(addWordRequest);
    }

    @GetMapping("/word/all")
    public DictionaryListResponse getAll(@RequestParam int itemsPerPage, @RequestParam int page) {
        return wordReadService.getAll(itemsPerPage, page);
    }

    @PostMapping("/translate/word")
    public WordGetDTO translate(@RequestBody TranslationRequest translationRequest) {
        return wordTranslationService.translateWord(translationRequest);
    }

    @PostMapping("/translate/sentence")
    public String translateSentence(@RequestBody TranslationRequest translationRequest) {
        return wordTranslationService.translateSentence(translationRequest);
    }

    @GetMapping("/report")
    public Report getReport() {
        return reportService.generateReport();
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<Resource> getReportAsPDF(@RequestParam FileFormat fileFormat) {
        Resource resource = reportService.generateReportAsFile(fileFormat);
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition disposition = ContentDisposition
                .attachment()
                .filename(Objects.requireNonNull(resource.getFilename()))
                .build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
