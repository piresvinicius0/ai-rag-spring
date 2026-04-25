package com.vp.aibackendrag.ingestion.wiki;

import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WikiIngestionService {

    private static final Logger log = LoggerFactory.getLogger(WikiIngestionService.class);
    private static final String WIKI_DIRECTORY = "data/wiki";

    public List<IngestedDocument> ingestWikiFiles() throws Exception {
        File[] markedownFiles = new File(WIKI_DIRECTORY).listFiles();
        List<IngestedDocument> docs = new ArrayList<>();
        for (File mdFile : markedownFiles) {
            if (mdFile.isFile() && mdFile.getName().endsWith(".md")) {
                docs.add(ingestSingleWikiFile(mdFile));
            }
        }
        return docs;
    }

    private IngestedDocument ingestSingleWikiFile(File mdFile) throws IOException {
        String content = Files.readString(mdFile.toPath());
        return new IngestedDocument("WIKI", content, Map.of("filename", mdFile.getName()));
    }
}
