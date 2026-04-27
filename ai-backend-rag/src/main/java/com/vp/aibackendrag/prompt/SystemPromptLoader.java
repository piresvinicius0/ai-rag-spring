package com.vp.aibackendrag.prompt;

import com.vp.aibackendrag.prompt.model.SystemInstructions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SystemPromptLoader {
    private static final String SYSTEM_PROMPT_PATH = "prompts/system-prompt.st";

    public SystemInstructions load() {
        ClassPathResource resource = new ClassPathResource(SYSTEM_PROMPT_PATH);

        try {
            String prompt = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return new SystemInstructions(prompt);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system prompt from " + SYSTEM_PROMPT_PATH, e);
        }
    }
}
