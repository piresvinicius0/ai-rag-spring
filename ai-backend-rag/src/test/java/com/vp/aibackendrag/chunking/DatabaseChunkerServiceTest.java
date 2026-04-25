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
public class DatabaseChunkerServiceTest {
    private static final Logger log = LoggerFactory.getLogger(DatabaseChunkerServiceTest.class);

    @Autowired
    private IngestionOrchestratorService ingestionOrchestratorService;

    @Autowired
    private DatabaseChunkerService service;

    @Test
    void testDatabaseChunking() throws Exception {
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();

        var dbDocs = docs.stream().filter(d -> d.getSource().equals("DB"))
                .toList();

        for (IngestedDocument dbDoc : dbDocs) {
            List<Chunk> chunks = service.chunk(dbDoc);
            var chunk = chunks.get(0);

            log.info("==== DB CHUNK ======");
            log.info("----Source: {}", chunk.getSource());
            log.info("----CHUNK: {}", chunk.getChunkindex());
            log.info("----Metadata: {}", chunk.getMetadata());
            log.info("Content: {} ", chunk.getContent());
        }

    }

}
