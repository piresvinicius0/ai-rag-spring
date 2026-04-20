package com.vp.aibackendrag.chunking;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.ingestion.model.IngestedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChunkingOrchestrator {

    private final DatabaseChunkerService dbChunker;
    private final PdfPragamaticChunkerService pdfChunker;
    private final WikiSemanticChunker wikiChunker;

    public List<Chunk> chunk(IngestedDocument document) throws Exception {
        return switch (document.getSource()) {
            case "WIKI" -> wikiChunker.chunk(document);
            case "PDF" -> pdfChunker.chunk(document);
            case "DB" -> dbChunker.chunk(document);
            default -> throw new Exception("Unsupported document source: " + document.getSource());
        };
    }

}
