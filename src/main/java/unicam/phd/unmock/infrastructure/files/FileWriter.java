package unicam.phd.unmock.infrastructure.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriter {


    public void writeClass(Path outputDir, String className, String code) {
        write(outputDir.resolve(className + ".java"), code);
    }

    public Path writeRunResult(String runId, String code) {
        return write(
                Path.of("results", runId, runId + ".txt"),
                code
        );
    }

    private Path write(Path file, String code) {
        try {
            Files.createDirectories(file.getParent());

            Files.writeString(
                    file,
                    code,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            return file;

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Cannot write file: " + file, e
            );
        }
    }

}