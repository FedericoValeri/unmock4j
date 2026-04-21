package unicam.phd.unmock.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Loader {

    public static String loadFile(String fileType) {
        return loadTextFile("prompts/human/" + fileType + ".txt");
    }

    private static String loadTextFile(String resourcePath) {
        try (InputStream is = Loader.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, e);
        }
    }
}