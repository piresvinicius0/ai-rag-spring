package com.vp.aibackendrag.lifecycle;

import com.vp.aibackendrag.chunking.ChunkingOrchestrator;
import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.IngestionOrchestratorService;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import com.vp.aibackendrag.lifecycle.model.KnowledgeRequest;
import com.vp.aibackendrag.vectorstore.ChunkvectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeLifeCycleService {

    private final ChunkvectorStoreService chunkvectorStoreService;
    private final IngestionOrchestratorService ingestionOrchestratorService;
    private final ChunkingOrchestrator chunkingOrchestrator;

    public void ingest(KnowledgeRequest request) throws Exception {
        String identity = KnowledgeIdentity.from(request);
        chunkvectorStoreService.deleteByIdentity(identity);
        List<IngestedDocument> docs = ingestionOrchestratorService.ingest(request);
        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument doc : docs) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(doc);
            chunksToStore.addAll(chunks);
        }
        chunkvectorStoreService.store(chunksToStore);
    }

    public void ingestAll() throws Exception {
        deleteAll();
        List<IngestedDocument> docs = ingestionOrchestratorService.ingestAll();
        List<Chunk> chunksToStore = new ArrayList<>();
        for (IngestedDocument doc : docs) {
            List<Chunk> chunks = chunkingOrchestrator.chunk(doc);
            chunksToStore.addAll(chunks);
        }
        chunkvectorStoreService.store(chunksToStore);
    }

    public void delete(KnowledgeRequest request) {
        String identity = KnowledgeIdentity.from(request);
        chunkvectorStoreService.deleteByIdentity(identity);
    }

    public void deleteAll() {
        chunkvectorStoreService.deleteAll();
    }
}
