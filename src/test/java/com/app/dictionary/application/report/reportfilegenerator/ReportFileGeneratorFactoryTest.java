package com.app.dictionary.application.report.reportfilegenerator;

import com.app.dictionary.application.dto.FileFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReportFileGeneratorFactoryTest {

    private ReportFileGenerator pdfReportFileGenerator = mock(ReportFileGenerator.class);
    private ReportFileGeneratorFactory reportFileGeneratorFactory =
            new ReportFileGeneratorFactory(pdfReportFileGenerator);

    @BeforeEach
    void setUp() {
        reportFileGeneratorFactory.init();
    }

    @Test
    void shouldThrowExceptionIfUnsupportedFileType() {
        assertThrows(UnsupportedOperationException.class,
                () -> reportFileGeneratorFactory.getReportFileGenerator(null));
    }

    @Test
    void shouldGetReportFileGenerator() {
        assertEquals(pdfReportFileGenerator, reportFileGeneratorFactory.getReportFileGenerator(FileFormat.PDF));
    }

}