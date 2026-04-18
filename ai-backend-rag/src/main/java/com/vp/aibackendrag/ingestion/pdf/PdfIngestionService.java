package com.vp.aibackendrag.ingestion.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PdfIngestionService {

    private static final Logger log = LoggerFactory.getLogger(PdfIngestionService.class);
    private static final String PDF_DIRECTORY = "data/pdfs";

    public void ingestPdfs() throws Exception {
        File[] pdffiles = new File(PDF_DIRECTORY).listFiles();
        for (File pdfFile : pdffiles) {
            if (pdfFile.isFile() && pdfFile.getName().endsWith(".pdf")) {
                log.info("Ingesting PDF: " + pdfFile.getName());
                ingestSinglePdf(pdfFile);
            }
        }
    }

    private void ingestSinglePdf(File pdfFile) throws IOException {
        log.info("Ingesting PDF: {}", pdfFile.getName());
        try(PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            log.info("---Extracted Text ({}) -----", pdfFile.getName());
            log.info(text);
        }
    }
}
