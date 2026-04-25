package com.vp.aibackendrag.ingestion;

import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IngestionOrchestratorServiceTest {

    @Autowired
    private IngestionOrchestratorService ingestionOrchestratorService;

    @Test
    public void ingestAll() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();
        System.out.println("Total documents ingested: " + docs.size());
        docs.forEach(doc -> System.out.println(" - " + doc.getSource() + ": " + doc.getContent().substring(0, Math.min(100, doc.getContent().length())) + "..."));
    }
}
