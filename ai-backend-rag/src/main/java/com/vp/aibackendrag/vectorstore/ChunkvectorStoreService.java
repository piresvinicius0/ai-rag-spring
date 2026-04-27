package com.vp.aibackendrag.vectorstore;

import com.vp.aibackendrag.chunking.model.Chunk;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.HashMap;
import java.util.List;

@Service
public class ChunkvectorStoreService {

    private final VectorStore vectorStore;
    private final JedisPooled jedisPooled;

    public ChunkvectorStoreService(@Qualifier("customVectorStore") VectorStore vectorStore, JedisPooled jedisPooled) {
        this.vectorStore = vectorStore;
        this.jedisPooled = jedisPooled;
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

    public long deleteAll() {
        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams params = new ScanParams().match("embedding:*").count(1000);
        long removed = 0;

        do {
            ScanResult<String> scanResult = jedisPooled.scan(cursor, params);
            List<String> keys = scanResult.getResult();
            if (!keys.isEmpty()) {
                removed += jedisPooled.unlink(keys.toArray(new String[0]));
            }
            cursor = scanResult.getCursor();

        } while (!ScanParams.SCAN_POINTER_START.equals(cursor));

        return removed;
    }

    public void deleteByIdentity(String identity) {
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        vectorStore.delete(filterBuilder.eq("identity", identity).build());
    }
}
