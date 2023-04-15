package com.app.dictionary.application.report.reportfilegenerator;

import com.app.dictionary.application.dto.FileFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;

@Component
@RequiredArgsConstructor
public class ReportFileGeneratorFactory {

    private static final EnumMap<FileFormat, ReportFileGenerator> reportGeneratorsByTypes =
            new EnumMap<>(FileFormat.class);

    private final ReportFileGenerator pdfReportFileGenerator;

    @PostConstruct
    protected void init() {
        reportGeneratorsByTypes.put(FileFormat.PDF, pdfReportFileGenerator);
    }

    public ReportFileGenerator getReportFileGenerator(FileFormat fileFormat) {
        if (!reportGeneratorsByTypes.containsKey(fileFormat)) {
            throw new UnsupportedOperationException(String.format("Unsupported file type: %s", fileFormat));
        }
        return reportGeneratorsByTypes.get(fileFormat);
    }

}
