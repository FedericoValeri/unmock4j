package unicam.phd.unmock.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JavaFileWriter {

    public Path write(Path outputDir, String className, String code) {
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

            return file;

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Cannot write file for class: " + className, e
            );
        }
    }
}