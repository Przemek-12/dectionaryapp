package com.app.dictionary.application.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


public class ReportFileGenerationException extends RuntimeException {

    @Override
    public String getMessage() {
        return "File report generation failed";
    }

}
