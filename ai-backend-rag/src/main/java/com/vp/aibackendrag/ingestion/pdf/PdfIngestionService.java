package com.vp.aibackendrag.ingestion.pdf;

import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PdfIngestionService {

    private static final Logger log = LoggerFactory.getLogger(PdfIngestionService.class);
    private static final String PDF_DIRECTORY = "data/pdfs";

    public List<IngestedDocument> ingest(String fileName) throws IOException {
        File pdfFile = new File(PDF_DIRECTORY + "/" + fileName);
        IngestedDocument doc = ingestSinglePdf(pdfFile);
        return Collections.singletonList(doc);
    }

    public List<IngestedDocument> ingestPdfs() throws Exception {
        File[] pdffiles = new File(PDF_DIRECTORY).listFiles();
        List<IngestedDocument> docs = new ArrayList<>();

        for (File pdfFile : pdffiles) {
            if (pdfFile.isFile() && pdfFile.getName().endsWith(".pdf")) {
                docs.add(ingestSinglePdf(pdfFile));
            }
        }
        return docs;
    }

    private IngestedDocument ingestSinglePdf(File pdfFile) throws IOException {
        try(PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return new IngestedDocument("PDF", text, Map.of("fileName", pdfFile.getName(), "identity", "PDF#" + pdfFile.getName()));
        }
    }
}
