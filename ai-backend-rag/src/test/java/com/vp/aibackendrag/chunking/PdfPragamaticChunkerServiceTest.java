package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorServiceTest;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PdfPragamaticChunkerServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PdfPragamaticChunkerServiceTest.class);

    @Autowired
    private PdfPragamaticChunkerService service;

    @Autowired
    private IngestionOrchestratorService ingestionOrchestratorService;

    @Test
    public void shouldIngestPdf() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();

        var pdfDoc = docs.stream().filter(d -> d.getSource().equals("PDF"))
                .findFirst()
                .orElseThrow();

        List<Chunk> chunks = service.chunk(pdfDoc);
        log.info("PDF Source: {}", pdfDoc.getSource());
        log.info("Total Chunks: {}", chunks.size());

        for (Chunk chunk : chunks) {
            log.info("----CHUNK: {}", chunk.getChunkindex());
            log.info("----Metadata: {}", chunk.getMetadata());
            log.info(chunk.getContent());
        }
    }

}
