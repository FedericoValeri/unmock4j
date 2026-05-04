package unicam.phd.unmock.infrastructure.prompts;

import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.application.pipeline.PipelineStepConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Builds PromptTemplate objects from system prompt resources.
 */
public class PromptService {

    public PromptTemplate build(String systemPromptPath, PipelineStepConfig config) {

        String systemPrompt = loadResource(systemPromptPath);

        StringBuilder template = new StringBuilder(systemPrompt);

        if (config.useSut()) {
            template.append("""
                    
                    SYSTEM UNDER TEST:
                    
                    {{sut}}
                    ---
                    """);
        }

        if (config.useUnitTest()) {
            template.append("""
                    
                    UNIT TEST:
                    
                    {{unit_test}}
                    ---
                    """);
        }

        if (config.usePartial()) {
            template.append("""
                    
                    PARTIALLY TRANSFORMED TEST:
                    
                    {{partially_transformed_test}}
                    ---
                    """);
        }

        if (config.useDependencies()) {
            template.append("""
                    
                    DEPENDENCIES:
                    
                    {{dependencies}}
                    ---
                    """);
        }

        return PromptTemplate.from(template.toString());
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