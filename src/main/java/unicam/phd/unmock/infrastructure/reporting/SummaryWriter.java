package unicam.phd.unmock.infrastructure.reporting;

import unicam.phd.unmock.config.Config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Appends run metadata into summary.csv
 */
public class SummaryWriter {
    private final CostCalculator costCalculator;

    public SummaryWriter(CostCalculator costCalculator) {
        this.costCalculator = costCalculator;
    }

    public void append(
            Path summaryFile,
            String runId,
            String sutPackage,
            int inputTokens,
            int outputToken,
            double elapsed) {

        try {
            Files.createDirectories(summaryFile.getParent());

            boolean exists = Files.exists(summaryFile);

            List<String> lines = new ArrayList<>();

            if (!exists) {
                lines.add(header());
            }

            lines.add(buildRow(
                    runId,
                    sutPackage,
                    inputTokens,
                    outputToken,
                    elapsed
            ));

            Files.write(
                    summaryFile,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to update summary file",
                    e
            );
        }
    }

    private String header() {
        return String.join(",",
                "Run ID",
                "SUT",
                "Provider",
                "Model",
                "Input Tokens",
                "Output Tokens",
                "Total Tokens",
                "Cost (USD)",
                "Generation Time (sec)",
                "Timestamp"
        );
    }

    private String buildRow(
            String runId,
            String sutPackage,
            int inputTokens,
            int outputTokens,
            double elapsed) {

        Double cost = costCalculator.compute(
                Config.MODEL,
                inputTokens,
                outputTokens
        );

        String timestamp = LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss"
                        )
                );

        return String.join(",",
                runId,
                sutPackage,
                Config.PROVIDER,
                Config.MODEL,
                String.valueOf(inputTokens),
                String.valueOf(outputTokens),
                String.valueOf(inputTokens + outputTokens),
                cost != null
                        ? String.format(
                        Locale.US,
                        "%.6f",
                        cost
                )
                        : "-",
                String.format(
                        Locale.US,
                        "%.3f",
                        elapsed
                ),
                timestamp
        );
    }
}