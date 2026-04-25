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
public class WikiSemanticChunkerServiceTest {
    private static final Logger log = LoggerFactory.getLogger(WikiSemanticChunkerServiceTest.class);

    @Autowired
    private IngestionOrchestratorService ingestionOrchestratorService;

    @Autowired
    private FixedSizeChunkerService fixedSizeChunker;

    @Autowired
    private WikiSemanticChunker wikiSemanticChunker;

    @Test
    public void testChunker() throws Exception {
        var documents = ingestionOrchestratorService.ingestAll();
        var wikiDoc = documents.stream().filter(doc -> doc.getSource().contains("WIKI"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No wiki document found"));

        log.info("-------------FIXED SIZE CHUNKING---------");
        var listChunks = fixedSizeChunker.chunk(wikiDoc, 500, 100);
        printChunks(wikiDoc, listChunks);

        log.info("-------------SEMANTINC WIKI CHUNKING---------");
        var semantiChunks = wikiSemanticChunker.chunk(wikiDoc);
        printChunks(wikiDoc, semantiChunks);

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
