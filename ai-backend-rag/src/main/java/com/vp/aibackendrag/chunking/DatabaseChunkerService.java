package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseChunkerService {

    public List<Chunk> chunk(IngestedDocument document) {
        return List.of(
                new Chunk(document.getSource(), document.getContent(), document.getMetadata(), 0)
        );// chunk size of 1000 chars with 200 chars overlap
    }
}
