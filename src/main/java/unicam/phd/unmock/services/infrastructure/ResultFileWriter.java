package unicam.phd.unmock.services.infrastructure;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes generated Java source output.
 */
public class ResultFileWriter {

    public Path write(String runId, String content) {

        try {
            Path outputPath = Path.of(
                    "results",
                    runId,
                    runId + ".java"
            );

            Files.createDirectories(outputPath.getParent());

            Files.writeString(
                    outputPath,
                    content,
                    StandardCharsets.UTF_8
            );

            return outputPath;

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to write generated file for run: " + runId,
                    e
            );
        }
    }
}