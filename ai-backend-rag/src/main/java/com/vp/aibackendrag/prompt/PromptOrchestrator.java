package com.vp.aibackendrag.prompt;

import com.vp.aibackendrag.prompt.model.ChatPrompt;
import com.vp.aibackendrag.prompt.model.PromptContext;
import com.vp.aibackendrag.prompt.model.SystemInstructions;
import com.vp.aibackendrag.retrieval.RetrievalService;
import com.vp.aibackendrag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptOrchestrator {

    private final RetrievalService retrievalService;
    private final ContextBuilder contextBuilder = new ContextBuilder();
    private final SystemPromptLoader systemPromptLoader = new SystemPromptLoader();
    private final GroudingPolicy groudingPolicy = new GroudingPolicy();

    public ChatPrompt build(String question) {
        RetrievalResult retrievalResult = retrievalService.retrieve(question);

        PromptContext promptContext = contextBuilder.build(retrievalResult);

        String groundingRule = groudingPolicy.groundingRules(promptContext);

        SystemInstructions systemInstructions = systemPromptLoader.load();

        return new ChatPrompt(systemInstructions, promptContext, groundingRule);
    }

}
