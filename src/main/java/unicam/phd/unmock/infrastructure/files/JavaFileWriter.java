package unicam.phd.unmock.infrastructure.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JavaFileWriter {

    public void write(Path outputDir, String className, String code) {
        try {
            Files.createDirectories(outputDir);

            Path file = outputDir.resolve(className + ".java");

            Files.writeString(
                    file,
                    code,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Cannot write file for class: " + className, e
            );
        }
    }
}