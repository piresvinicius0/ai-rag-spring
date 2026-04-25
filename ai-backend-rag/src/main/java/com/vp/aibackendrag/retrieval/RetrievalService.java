package com.vp.aibackendrag.retrieval;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.retrieval.model.RetrievalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetrievalService {

    private static final Logger log = LoggerFactory.getLogger(RetrievalService.class);

    private final VectorStore vectorStore;
    private final ChunkRankingComparator chunkRankingComparator = new ChunkRankingComparator();

    public RetrievalService(@Qualifier("customVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public RetrievalResult retrieve(String query) {
        log.info("Received retrieval query: {}", query);

        SearchRequest searchRequest = SearchRequest.builder().query(query)
                .topK(5)
                .build();

        var documents = vectorStore.similaritySearch(searchRequest);

        List<Chunk> chunks = documents.stream()
                .filter(this::isAllowedByMetadata)
                .map(this::toChunk)
                .sorted(chunkRankingComparator)
                .collect(Collectors.toList());

        return new RetrievalResult(chunks);
    }

    private boolean isAllowedByMetadata(Document document) {
        Map<String, Object> metadata = new HashMap<>(document.getMetadata());
        String source = metadata.get("source").toString();

        if (!"DB".equals(source)) {
            return true;
        }

        String table = metadata.get("table") != null ? metadata.get("table").toString() : "";
        return switch(table) {
            case "announcements" -> isActiveAnnouncement(metadata);
            case "faqs" -> isPublicFaq(metadata);
            case "release_notes" -> true;
            default -> true;
        };

    }

    private boolean isPublicFaq(Map<String, Object> metadata) {
        return !"RESTRICTED".equalsIgnoreCase(metadata.get("visibility").toString());
    }

    private boolean isActiveAnnouncement(Map<String, Object> metadata) {
        var today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        String fromDate = metadata.get("effectiveFrom") != null ? metadata.get("effectiveFrom").toString() : null;
        if (fromDate == null)
            return true;
        String tillDate = metadata.get("effectiveTo") != null ? metadata.get("effectiveTo").toString() : "";

        LocalDate from = LocalDate.parse(fromDate, formatter);
        LocalDate to = !tillDate.isEmpty() ? LocalDate.parse(tillDate, formatter) : from.plusYears(1);

        return !today.isBefore(from) && !today.isAfter(to);
    }

    private Chunk toChunk(Document document) {
        String content = document.getText();
        Map<String, Object> metadata = new HashMap<>(document.getMetadata());

        int chunkIndex = 0;
        Object chunkIndexValue = metadata.get("chunkIndex");
        if(chunkIndexValue != null) {
            chunkIndex = Integer.parseInt(chunkIndexValue.toString());
        }
        return new Chunk(metadata.get("source").toString(), content,metadata, chunkIndex);
    }


}
