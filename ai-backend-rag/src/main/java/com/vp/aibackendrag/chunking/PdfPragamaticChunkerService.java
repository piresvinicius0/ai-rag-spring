package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfPragamaticChunkerService {

    private static final int PDF_CHUNK_SIZE = 500;
    private static final int PDF_CHUNK_OVERLAP = 100;

    private final FixedSizeChunkerService fixedSizeChunkerService;

    public List<Chunk> chunk(IngestedDocument document) {
        var rawChunks = fixedSizeChunkerService.chunk(document, PDF_CHUNK_SIZE, PDF_CHUNK_OVERLAP);
        
        return rawChunks.stream()
                .map(this::enrichPdfMetadata)
                .collect(Collectors.toList());
    }

    private Chunk enrichPdfMetadata(Chunk chunk) {
        Map<String, Object> enrichedData = new HashMap<>(chunk.getMetadata());
        enrichedData.put("sourceType", "PDF");
        enrichedData.put("chunkStrategy", "PDF_PRAGMATIC_FIXED_SIZE");
        enrichedData.put("chunkSize", PDF_CHUNK_SIZE);
        enrichedData.put("chunkOverlap", PDF_CHUNK_OVERLAP);
        return new Chunk(chunk.getSource(), chunk.getContent(), enrichedData, chunk.getChunkindex());

    }

}
