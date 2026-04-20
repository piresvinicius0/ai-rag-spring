package com.vp.aibackendrag.ingestion;

import com.vp.aibackendrag.ingestion.db.DatabaseIngestionService;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import com.vp.aibackendrag.ingestion.pdf.PdfIngestionService;
import com.vp.aibackendrag.ingestion.wiki.WikiIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestionOrchestratorService {

    private final PdfIngestionService pdfIngestionService;
    private final WikiIngestionService wikiIngestionService;
    private final DatabaseIngestionService databaseIngestionService;

    public List<IngestedDocument> ingestAll() throws Exception {
        List<IngestedDocument> docs = new ArrayList<>();
        docs.addAll(pdfIngestionService.ingestPdfs());
        docs.addAll(wikiIngestionService.ingestWikiFiles());
        docs.addAll(databaseIngestionService.ingestDatabaseContent());
        return docs;
    }

}
