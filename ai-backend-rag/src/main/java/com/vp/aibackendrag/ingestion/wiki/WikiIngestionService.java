package com.vp.aibackendrag.ingestion.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class WikiIngestionService {

    private static final Logger log = LoggerFactory.getLogger(WikiIngestionService.class);
    private static final String WIKI_DIRECTORY = "data/wiki";

    public void ingestWikiFiles() throws Exception {
        File[] markedownFiles = new File(WIKI_DIRECTORY).listFiles();
        for (File mdFile : markedownFiles) {
            if (mdFile.isFile() && mdFile.getName().endsWith(".md")) {
                log.info("Ingesting Wiki Markdown: " + mdFile.getName());
                ingestSingleWikiFile(mdFile);
            }
        }
    }

    private void ingestSingleWikiFile(File mdFile) throws IOException {
        log.info("ingesting wiki file: {}", mdFile.getName());
        String content = Files.readString(mdFile.toPath());
        log.info("---Extracted Content ({}) -----", mdFile.getName());
        log.info(content);
    }
}
