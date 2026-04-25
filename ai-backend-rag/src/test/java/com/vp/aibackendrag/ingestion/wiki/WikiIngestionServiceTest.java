package com.vp.aibackendrag.ingestion.wiki;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
public class WikiIngestionServiceTest {

    @Autowired
    private WikiIngestionService service;

    @Test
    public void should_list_text_from_md_files() throws Exception {
        service.ingestWikiFiles();
    }

}
