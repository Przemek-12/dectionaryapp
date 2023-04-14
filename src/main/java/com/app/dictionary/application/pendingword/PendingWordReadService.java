package com.app.dictionary.application.pendingword;

import com.app.dictionary.application.dto.PendingWordDTO;

import java.util.List;

public interface PendingWordReadService {

    List<PendingWordDTO> findAll();

}
