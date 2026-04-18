package com.vp.aibackendrag.ingestion.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PdfIngestionServiceTest {

    @Autowired
    PdfIngestionService service;

    @Test
    void ingestPdfs_forLearning() throws Exception {
        service.ingestPdfs();
    }

}
