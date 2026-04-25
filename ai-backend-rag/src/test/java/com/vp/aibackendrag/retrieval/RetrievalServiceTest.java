package com.vp.aibackendrag.retrieval;

import com.vp.aibackendrag.chunking.model.Chunk;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RetrievalServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RetrievalServiceTest.class);


    @Autowired
    private RetrievalService service;

    @Test
    public void testRetrieval() {
        var result = service.retrieve("What is the work from home policy?");
        log.info("Retrieval results found: {}", result.getChunks().size());
        for (Chunk chunk : result.getChunks()) {
            log.info("metadata: {}", chunk.getMetadata());
            log.info("Content: {}", chunk.getContent());

        }
    }

}
