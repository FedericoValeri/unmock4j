package unicam.phd.unmock.services.infrastructure;

import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.PipelineState;

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

import static unicam.phd.unmock.utils.Pricing.computeCost;

/**
 * Appends run metadata into summary.csv
 */
public class SummaryWriter {

    public void append(
            Path summaryFile,
            String runId,
            String sutPackage,
            PipelineState state) {

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
                    state
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
            PipelineState state) {

        Double cost = computeCost(
                Config.MODEL,
                state.inputTokens(),
                state.outputTokens()
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
                String.valueOf(state.inputTokens()),
                String.valueOf(state.outputTokens()),
                String.valueOf(
                        state.inputTokens()
                                + state.outputTokens()
                ),
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
                        state.elapsed()
                ),
                timestamp
        );
    }
}