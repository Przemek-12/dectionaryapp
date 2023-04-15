package com.app.dictionary.application.word;

import com.app.dictionary.application.dto.TranslationRequest;
import com.app.dictionary.application.dto.WordGetDTO;
import com.app.dictionary.application.pendingword.PendingWordWriteService;
import com.app.dictionary.domain.entity.Language;
import com.app.dictionary.domain.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordTranslationServiceImpl implements WordTranslationService {

    private static final String REGEX_NOT_LETTERS = "[^a-zA-Z]+";
    private static final String WORD_MATCHER_CHAR = "\\b";

    private final PendingWordWriteService pendingWordWriteService;
    private final WordReadService wordReadService;

    @Override
    public WordGetDTO translateWord(TranslationRequest translationRequest) {
        Language fromLang = WordUtils.toLanguage(translationRequest.getFromLang());
        Word wordToTranslate = wordReadService
                .findWordByLanguageAndValueOrElseThrowException(fromLang, translationRequest.getValue());
        Language toLang = WordUtils.toLanguage(translationRequest.getToLang());
        return WordUtils.mapToWordGetDTO(wordReadService
                .findWordByLanguageAndSharedUUIDOrElseThrowException(toLang, wordToTranslate.getSharedUUID()));
    }

    @Override
    public String translateSentence(TranslationRequest translationRequest) {
        String sentence = translationRequest.getValue();
        Set<String> wordsInSentence = Arrays.stream(translationRequest.getValue()
                .split(REGEX_NOT_LETTERS)).collect(Collectors.toSet());
        Language fromLang = WordUtils.toLanguage(translationRequest.getFromLang());
        Language toLang = WordUtils.toLanguage(translationRequest.getToLang());
        for (String wordInSentence : wordsInSentence) {
            sentence = translateSingleWordInSentence(sentence, fromLang, toLang, wordInSentence);
        }
        return sentence;
    }

    private String translateSingleWordInSentence(String sentence, Language fromLang, Language toLang,
                                                 String wordInSentence) {
        Optional<Word> wordToTranslateOpt = wordReadService.findWordByLanguageAndValueOpt(fromLang, wordInSentence);
        if (wordToTranslateOpt.isPresent()) {
            Word translatedWord = wordReadService.findWordByLanguageAndSharedUUIDOrElseThrowException(toLang,
                    wordToTranslateOpt.get().getSharedUUID());
            String wordMatcherRegex = WORD_MATCHER_CHAR + wordToTranslateOpt.get().getValue() + WORD_MATCHER_CHAR;
            return sentence.replaceAll(wordMatcherRegex, translatedWord.getValue());
        }
        addWordAsPending(fromLang, wordInSentence);
        return sentence;
    }

    private void addWordAsPending(Language lang, String word) {
        pendingWordWriteService.addPendingWord(lang, word);
    }
}
