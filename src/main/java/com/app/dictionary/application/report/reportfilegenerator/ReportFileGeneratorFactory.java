package com.app.dictionary.application.report.reportfilegenerator;

import com.app.dictionary.application.dto.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;

@Component
@RequiredArgsConstructor
public class ReportFileGeneratorFactory {

    private static final EnumMap<FileType, ReportFileGenerator> reportGeneratorsByTypes =
            new EnumMap<>(FileType.class);

    private final ReportFileGenerator pdfReportFileGenerator;

    @PostConstruct
    protected void init() {
        reportGeneratorsByTypes.put(FileType.PDF, pdfReportFileGenerator);
    }

    public ReportFileGenerator getReportFileGenerator(FileType fileType) {
        if (!reportGeneratorsByTypes.containsKey(fileType)) {
            throw new UnsupportedOperationException(String.format("Unsupported file type: %s", fileType));
        }
        return reportGeneratorsByTypes.get(fileType);
    }

}
