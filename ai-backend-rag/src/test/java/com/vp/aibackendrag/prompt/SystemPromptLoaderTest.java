package com.vp.aibackendrag.prompt;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SystemPromptLoaderTest {

    private static final Logger log = LoggerFactory.getLogger(SystemPromptLoaderTest.class);

    @Test
    void loadSystemPromptFromResources() {
        SystemPromptLoader loader = new SystemPromptLoader();
        var systemInstructions = loader.load();

        log.info("Loaded system instructions: {}", systemInstructions.getInstructions());
    }

}
