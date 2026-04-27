package com.vp.aibackendrag.lifecycle;

import com.vp.aibackendrag.lifecycle.model.KnowledgeRequest;
import com.vp.aibackendrag.lifecycle.model.SourceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KnowledgeLifecycleTest {

    @Autowired
    private KnowledgeLifeCycleService service;

    @Test
    void testIngestPdf() throws Exception {
        KnowledgeRequest request = KnowledgeRequest.builder()
                .sourceType(SourceType.PDF)
                .name("HR_Leave_Policy.pdf")
                .build();
        service.ingest(request);
    }

    @Test
    void ingestAll() throws Exception {
        service.ingestAll();
    }

    @Test
    public void testDeleteAll() {
        service.deleteAll();
    }

}
