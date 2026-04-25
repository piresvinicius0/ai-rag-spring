package com.vp.aibackendrag.service;

import com.vp.aibackendrag.chunking.model.Chunk;
import com.vp.aibackendrag.dto.ChatRequest;
import com.vp.aibackendrag.dto.ChatResponse;
import com.vp.aibackendrag.retrieval.RetrievalService;
import com.vp.aibackendrag.retrieval.model.RetrievalResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final RetrievalService retrievalService;

    public ChatResponse chat(ChatRequest request) {
        String userMessage = request.getMessage();
        RetrievalResult retrievalResult = retrievalService.retrieve(userMessage);
        String context = buildContext(retrievalResult);
        String aiAnswer = chatClient
                .prompt()
                .system(context)
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
