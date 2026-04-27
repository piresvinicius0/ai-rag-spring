package com.vp.aibackendrag.prompt;

import com.vp.aibackendrag.prompt.model.PromptContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroudingPolicyTest {
    private static final Logger log = LoggerFactory.getLogger(GroudingPolicyTest.class);

    @Test
    void emptyContextTest() {
        GroudingPolicy policy = new GroudingPolicy();
        String rule = policy.groundingRules(new PromptContext(""));
        log.info("Grounding rule for empty context: {}", rule);
    }

    @Test
    void nonEmptyContextTest() {
        GroudingPolicy policy = new GroudingPolicy();
        String rule = policy.groundingRules(new PromptContext("Some internal policy text"));
        log.info("Grounding rule for context: {}", rule);
    }
}
