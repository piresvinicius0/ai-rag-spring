package com.vp.aibackendrag.service;

import com.vp.aibackendrag.dto.ChatRequest;
import com.vp.aibackendrag.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public ChatResponse chat(ChatRequest request) {
        String answer = chatClient.prompt().user(request.getMessage()).call().content();
        return new ChatResponse(answer);
    }

}
