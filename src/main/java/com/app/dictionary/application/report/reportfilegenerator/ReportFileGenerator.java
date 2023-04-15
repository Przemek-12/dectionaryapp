package com.app.dictionary.application.report.reportfilegenerator;

import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.exception.ReportFileGenerationException;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;

public interface ReportFileGenerator {
    Resource generateReport(Report report) throws ReportFileGenerationException;
}
