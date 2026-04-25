package com.vp.aibackendrag.vectorstore;

import com.vp.aibackendrag.chunking.ChunkingOrchestrator;
import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.embedding.ChunkEmbeddingService;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ChunkvectorStoreServiceTest {
    @Autowired
    private IngestionOrchestratorService ingestionOrchestrator;

    @Autowired
    private ChunkingOrchestrator chunkingOrchestrator;

    @Autowired
    private ChunkvectorStoreService chunkvectorStoreService;

    @Test
    public void testVectorStore() throws Exception {
        var documents = ingestionOrchestrator.ingestAll();
        List<Chunk> chunksToStore = new ArrayList<>();
        documents.stream().forEach(doc -> {
            List<Chunk> chunks = null;
            try {
                chunks = chunkingOrchestrator.chunk(doc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            chunksToStore.addAll(chunks);
        });

        chunkvectorStoreService.store(chunksToStore);
    }


}
