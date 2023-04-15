package com.app.dictionary.application.report;

import com.app.dictionary.application.dto.FileType;
import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.exception.ReportFileGenerationException;
import com.app.dictionary.application.report.reportfilegenerator.ReportFileGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReportService {

    Report generateReport();
    Resource generateReportAsFile(FileType fileType) throws ReportFileGenerationException;
}
