package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WikiSemanticChunker {

    public List<Chunk> chunk(IngestedDocument document) {
        List<Chunk> chunks = new ArrayList<>();
        String content = document.getContent();
        String[] sections = content.split("\n(?=#+\\s)");

        int chunkIndex = 0;

        for (String section : sections) {
            if (section.trim().isEmpty()) continue;
            Map<String, Object> chunkMetadata = new HashMap<>(document.getMetadata());
            chunkMetadata.put("chunkIndex", chunkIndex);
            chunkMetadata.put("chunkType", "WIKI_SECTION");

            chunks.add(new Chunk(document.getSource(), section.trim(), document.getMetadata(), chunkIndex));
            chunkIndex++;
        }

        return chunks;
    }
}
