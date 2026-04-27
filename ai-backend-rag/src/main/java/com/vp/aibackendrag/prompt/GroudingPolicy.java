package com.vp.aibackendrag.prompt;

import com.vp.aibackendrag.prompt.model.PromptContext;

public class GroudingPolicy {
    public String groundingRules(PromptContext context) {
        if (context == null || context.getContextText().isBlank()) {
            return """ 
                You dont have sufficient information to answer the question. Respond clearly that you dont know. Do not guess or infer.
                """;
        }

        return """
            Use only the provided context to answer the question. 
            Do not use any external information or make assumptions. 
            If the answer is not explicitly stated in the context, respond that you do not know.
            """;
    }
}
