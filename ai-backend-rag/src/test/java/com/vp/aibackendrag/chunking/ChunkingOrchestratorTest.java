package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChunkingOrchestratorTest {

    private static final Logger log = LoggerFactory.getLogger(ChunkingOrchestratorTest.class);

    @Autowired
    private IngestionOrchestratorService ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Test
    public void testAllChunks() throws Exception {
        var docs = ingestionOrchestrator.ingestAll();
        for (IngestedDocument doc : docs) {
            log.info("Chunking document: {}", doc.getSource());
            var chunks = chunkingOrchestrator.chunk(doc);
            log.info("Total chunks created: {}", chunks.size());

            for (Chunk chunk : chunks) {
                log.info("----Chunk {}----", chunk.getChunkindex());
                log.info("Metadata: {}", chunk.getMetadata());
                log.info(chunk.getContent());
            }
        }
    }

}
