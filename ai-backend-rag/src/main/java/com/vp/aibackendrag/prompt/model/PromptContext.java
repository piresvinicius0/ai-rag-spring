package com.vp.aibackendrag.prompt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptContext {
    private final String contextText;
}
