package com.app.dictionary.application.pendingword;

import com.app.dictionary.application.dto.PendingWordAllResponse;
import com.app.dictionary.application.dto.PendingWordDTO;
import com.app.dictionary.domain.entity.PendingWord;
import com.app.dictionary.domain.repository.PendingWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PendingWordReadServiceImpl implements PendingWordReadService {

    private final PendingWordRepository pendingWordRepository;

    @Override
    public PendingWordAllResponse findAll() {
        return PendingWordAllResponse.builder()
                .words(pendingWordRepository.findAll().stream().map(this::mapToPendingWordDTO).toList())
                .build();
    }

    private PendingWordDTO mapToPendingWordDTO(PendingWord pendingWord) {
        return PendingWordDTO.builder()
                .language(pendingWord.getLanguage().toString())
                .value(pendingWord.getValue())
                .build();
    }
}
