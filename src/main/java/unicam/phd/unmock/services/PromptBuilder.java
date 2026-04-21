package unicam.phd.unmock.services;


import dev.langchain4j.model.input.PromptTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PromptBuilder {

    public static PromptTemplate buildPrompt(String systemPromptPath) {

        System.out.println("Building prompt from " + systemPromptPath + "...");

        String systemPrompt = loadSystemPrompt(systemPromptPath);

        String template = """
                %s
                
                UNIT TEST:
                ----------------
                {{unit_test}}
                
                PARTIALLY TRANSFORMED TEST:
                ----------------
                {{partially_transformed_test}}
                
                DEPENDENCIES:
                ----------------
                {{dependencies}}
                """.formatted(systemPrompt);

        return PromptTemplate.from(template);
    }

    public static String loadSystemPrompt(String resourcePath) {

        try (InputStream is = PromptBuilder.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("System prompt not found: " + resourcePath);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load system prompt: " + resourcePath, e);
        }
    }
}