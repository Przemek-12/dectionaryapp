package com.app.dictionary.application.report.reportfilegenerator;

import com.app.dictionary.application.dto.Report;
import com.app.dictionary.application.exception.ReportFileGenerationException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PDFReportFileGenerator implements ReportFileGenerator {

    private static final Chunk linebreak = new Chunk(new DottedLineSeparator());
    private static final Paragraph paragraph = new Paragraph();

    @Override
    public Resource generateReport(Report report) throws ReportFileGenerationException {
        try {
            log.info("Generation of pdf report started.");
            Resource resource = generatePDFReport(report);
            log.info("Generation of pdf report finished.");
            return resource;
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new ReportFileGenerationException();
        }
    }

    private Resource generatePDFReport(Report report) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        String fileName = UUID.randomUUID() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        document.add(new Phrase("REPORT"));
        addLineBreak(document);
        document.add(new Phrase("allWordsCount: " + report.getAllWordsCount()));
        addLineBreak(document);
        document.add(new Phrase("pendingWordsCount: " + report.getPendingWordsCount()));
        addLineBreak(document);
        addWordsByLengthByLangs(document, report);
        addLineBreak(document);
        addWordsAvgLengthByLangs(document, report);
        document.close();
        return new FileSystemResource(fileName);
    }

    private void addWordsAvgLengthByLangs(Document document, Report report) throws DocumentException {
        document.add(new Phrase("wordsAvgLengthByLangs: "));
        document.add(paragraph);
        for(Map.Entry<String, Float> wordAvgLengthByLang: report.getWordsAvgLengthByLangs().entrySet()) {
            document.add(new Phrase(wordAvgLengthByLang.getKey()
                    + ": " + wordAvgLengthByLang.getValue().toString()));
            document.add(paragraph);
        }
    }

    private void addWordsByLengthByLangs(Document document, Report report) throws DocumentException {
        document.add(new Phrase("wordsByLengthByLangs: "));
        document.add(paragraph);
        for(Map.Entry<String, Map<Integer, Integer>> entry: report.getWordsByLengthByLangs().entrySet()){
            document.add(new Phrase(entry.getKey()));
            document.add(paragraph);
            for(Map.Entry<Integer, Integer> wordByLength: entry.getValue().entrySet()) {
                document.add(new Phrase(wordByLength.getKey().toString()
                        + " - " + wordByLength.getValue().toString()));
                document.add(paragraph);
            }
        }
    }

    private void addLineBreak(Document document) throws DocumentException {
        document.add(new Paragraph());
        document.add(linebreak);
        document.add(new Paragraph());
    }

}
