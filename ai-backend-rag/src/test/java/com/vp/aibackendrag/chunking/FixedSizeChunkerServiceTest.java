package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FixedSizeChunkerServiceTest {
    private static final Logger log = LoggerFactory.getLogger(FixedSizeChunkerServiceTest.class);

    @Autowired
    private IngestionOrchestratorService ingestionOrchestratorService;

    @Autowired
    private FixedSizeChunkerService chunker;

    @Test
    public void chunkerTest() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();
        IngestedDocument doc = docs.get(0);
        log.info("----------NO OVERLAP-------");
        var listChunks = chunker.chunk(doc, 500);
        printChunks(doc, listChunks);
    }

    @Test
    public void chunkerTestWithOverlap() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();
        IngestedDocument doc = docs.get(0);
        log.info("----------WITH OVERLAP-------");
        var listChunks = chunker.chunk(doc, 500, 100);
        printChunks(doc, listChunks);
    }

    private static void printChunks(IngestedDocument doc, List<Chunk> listChunks) {
        log.info("Source: {}", doc.getSource());
        log.info("Original lenght: {}", doc.getContent().length());
        log.info("Total chunks: {}", listChunks.size());

        for (Chunk chunk : listChunks) {
            log.info("----Chunk {}----", chunk.getChunkindex());
            log.info(chunk.getContent());
        }
    }
}
