package unicam.phd.unmock.infrastructure.prompts;

import unicam.phd.unmock.application.pipeline.InputFileType;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Loads human prompt text files from resources/prompts/human/.
 */
public class HumanPromptFileLoader {

    public String getFileContent(InputFileType fileType) {
        String path = "prompts/human/" + fileType.name() + ".txt";
        return load(path);
    }

    private String load(String resourcePath) {

        try (InputStream is =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new IllegalArgumentException(
                        "Resource not found: " + resourcePath
                );
            }

            return new String(
                    is.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to load resource: " + resourcePath,
                    e
            );
        }
    }
}