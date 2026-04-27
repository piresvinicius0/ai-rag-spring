package com.vp.aibackendrag.service;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.dto.ChatRequest;
import com.vp.aibackendrag.dto.ChatResponse;
import com.vp.aibackendrag.prompt.PromptOrchestrator;
import com.vp.aibackendrag.prompt.model.ChatPrompt;
import com.vp.aibackendrag.retrieval.RetrievalService;
import com.vp.aibackendrag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final PromptOrchestrator promptOrchestrator;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getMessage();
        ChatPrompt prompt = promptOrchestrator.build(userMessage);
        String llmInput = prompt.getGroudingRule()
                + "\n\n"
                + prompt.getContext().getContextText()
                + "\n\nUser Question: \n"
                + userMessage;

        String aiAnswer = chatClient
                .prompt(prompt.getSystemInstructions().getInstructions())
                .system(llmInput)
                .user(request.getMessage())
                .call()
                .content();
        return new ChatResponse(aiAnswer);
    }

    private String buildContext(RetrievalResult retrievalResult) {
        StringBuilder contextBuilder = new StringBuilder();
        for (Chunk chunk : retrievalResult.getChunks()) {
            contextBuilder.append(chunk.getContent()).append("\n\n");
        }
        return contextBuilder.toString();
    }

}
