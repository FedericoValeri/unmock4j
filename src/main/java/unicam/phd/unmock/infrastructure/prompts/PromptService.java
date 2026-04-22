package unicam.phd.unmock.infrastructure.prompts;

import dev.langchain4j.model.input.PromptTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Builds PromptTemplate objects from system prompt resources.
 */
public class PromptService {

    public PromptTemplate build(String systemPromptPath) {

        String systemPrompt = loadResource(systemPromptPath);

        String template = """
                %s
                SYSTEM UNDER TEST:
                
                {{sut}}
                
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

    private String loadResource(String resourcePath) {

        try (InputStream is =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new IllegalArgumentException(
                        "Prompt resource not found: " + resourcePath
                );
            }

            return new String(
                    is.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Cannot load prompt resource: " + resourcePath,
                    e
            );
        }
    }
}