package com.vp.aibackendrag.prompt;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.prompt.model.PromptContext;
import com.vp.aibackendrag.retrieval.model.RetrievalResult;

import java.util.Map;

public class ContextBuilder {

    public PromptContext build(RetrievalResult retrievalResult) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Chunk chunk : retrievalResult.getChunks()) {
            sb.append("Context ")
                    .append(index++)
                    .append(":\n")
                    .append(chunk.getContent())
                    .append("\n\n");
            appendCitations(sb, chunk);
        }
        return new PromptContext(sb.toString().trim());
    }

    private void appendCitations(StringBuilder sb, Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        String source = metadata.get("source") != null ? metadata.get("source").toString() : "Unknown Source";
        switch (source) {
            case "PDF":
            case "WIKI":
                Object fileName = metadata.get("fileName") != null ? metadata.get("fileName") : metadata.get("filename");
                sb.append("[")
                        .append(source)
                        .append(": ").append(fileName != null ? fileName : "unknown-file")
                        .append("]");
                break;
            case "DB":
                sb.append("[DB: ")
                        .append(metadata.get("table"))
                        .append("#")
                        .append(metadata.get("id"))
                        .append("]\n");
                break;
        }
    }
}
