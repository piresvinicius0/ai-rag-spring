package com.vp.aibackendrag.embedding;

import com.vp.aibackendrag.chunking.ChunkingOrchestrator;
import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.embedding.model.EmbeddedChunk;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ChunkEmbeddingServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ChunkEmbeddingServiceTest.class);

    @Autowired
    private IngestionOrchestratorService ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Autowired
    private ChunkEmbeddingService embeddingService;

    @Test
    public void testEmbedding() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestrator.ingestAll();
        IngestedDocument doc = docs.get(0);

        var chunks = chunkingOrchestrator.chunk(doc);
        log.info("Total chunks created: {}", chunks.size());

        for (Chunk chunk : chunks) {
            EmbeddedChunk embeddedChunk = embeddingService.embed(chunk);

            log.info("Metadata  : {}", chunk.getMetadata());
            log.info("Content  : {}", chunk.getContent());
            log.info("Embedding Lenght  : {}", embeddedChunk.getVector().length);
        }
    }

}
