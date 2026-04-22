package unicam.phd.unmock.services.infrastructure;

import unicam.phd.unmock.models.InputFileType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HumanPromptLoader {

    public static String getFileContent(InputFileType fileType) {
        return loadTextFile("prompts/human/" + fileType.name() + ".txt");
    }

    private static String loadTextFile(String resourcePath) {
        try (InputStream is = HumanPromptLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, e);
        }
    }
}