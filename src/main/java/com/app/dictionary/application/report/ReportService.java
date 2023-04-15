package com.app.dictionary.application.report;

import com.app.dictionary.application.dto.FileFormat;
import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.exception.ReportFileGenerationException;
import org.springframework.core.io.Resource;

public interface ReportService {

    Report generateReport();
    Resource generateReportAsFile(FileFormat fileFormat) throws ReportFileGenerationException;
}
