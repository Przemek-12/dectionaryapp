package com.app.dictionary.presentation;

import com.app.dictionary.application.dto.PendingWordAllResponse;
import com.app.dictionary.application.dto.PendingWordDTO;
import com.app.dictionary.application.pendingword.PendingWordReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pending-word")
@RequiredArgsConstructor
public class PendingWordController {

    private final PendingWordReadService pendingWordReadService;

    @GetMapping("/all")
    public PendingWordAllResponse getAll() {
        return pendingWordReadService.findAll();
    }

}
