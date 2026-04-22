package unicam.phd.unmock.infrastructure.reporting;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Generates sequential run ids per SUT.
 * Example:
 * org.foo.Bar-0
 * org.foo.Bar-1
 * org.foo.Bar-2
 */
public class RunIdGenerator {

    public int next(Path summaryFile, String sutPackage) {

        if (!Files.exists(summaryFile)) {
            return 0;
        }

        try {
            List<String> rows = Files.readAllLines(
                    summaryFile,
                    StandardCharsets.UTF_8
            );

            if (rows.size() <= 1) {
                return 0;
            }

            int max = -1;

            for (int i = 1; i < rows.size(); i++) {

                String[] cols = rows.get(i).split(",");

                if (cols.length == 0) {
                    continue;
                }

                String runId = cols[0];

                if (!runId.startsWith(sutPackage + "-")) {
                    continue;
                }

                try {
                    int current =
                            Integer.parseInt(
                                    runId.substring(
                                            runId.lastIndexOf('-') + 1
                                    )
                            );

                    max = Math.max(max, current);

                } catch (NumberFormatException ignored) {
                }
            }

            return max + 1;

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed reading summary file",
                    e
            );
        }
    }
}