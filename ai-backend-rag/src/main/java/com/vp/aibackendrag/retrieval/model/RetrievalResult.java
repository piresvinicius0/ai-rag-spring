package com.vp.aibackendrag.retrieval.model;

import com.vp.aibackendrag.chunking.model.Chunk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalResult {
    private List<Chunk> chunks;
}
