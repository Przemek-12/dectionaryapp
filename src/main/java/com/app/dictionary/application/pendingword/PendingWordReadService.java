package com.app.dictionary.application.pendingword;

import com.app.dictionary.application.dto.PendingWordAllResponse;
import com.app.dictionary.application.dto.PendingWordDTO;

import java.util.List;

public interface PendingWordReadService {

    PendingWordAllResponse findAll();

}
