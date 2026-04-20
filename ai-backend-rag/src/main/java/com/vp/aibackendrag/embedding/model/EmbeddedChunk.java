package com.vp.aibackendrag.embedding.model;

import com.vp.aibackendrag.chunking.model.Chunk;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmbeddedChunk {
    private final Chunk chunk;
    private final float[] vector;
}
