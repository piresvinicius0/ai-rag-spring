package com.vp.aibackendrag.vectorstore;

import com.vp.aibackendrag.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ChunkvectorStoreService {

    private final VectorStore vectorStore;

    public ChunkvectorStoreService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(List<Chunk> chunks) {
        var docs = chunks.stream().map(chunk -> {
            var metadata = new HashMap<>(chunk.getMetadata());
            metadata.put("source", chunk.getSource());
            metadata.put("chunkIndex", chunk.getChunkindex());
            return new Document(chunk.getContent(), metadata);
        }).toList();

        vectorStore.add(docs);
    }
}
