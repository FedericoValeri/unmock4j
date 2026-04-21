package unicam.phd.unmock.services;

import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.PipelineState;

import java.io.IOException;
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

public class Output {

    public static String generate(PipelineState state, String sutPackage) {

        String summaryFile = "./results/summary.csv";
        LocalDateTime runTimestamp = LocalDateTime.now();
        int runIdNumber = getNextRunId(summaryFile, sutPackage);
        String runId = sutPackage + "-" + runIdNumber;

        String outputPath = writeOutputFile(
                runId,
                state.partiallyTransformedTest()
        );

        Double cost = computeCost(
                Config.MODEL,
                state.inputTokens(),
                state.outputTokens()
        );

        updateSummary(summaryFile, List.of(
                runId,
                sutPackage,
                Config.PROVIDER,
                Config.MODEL,
                String.valueOf(state.inputTokens()),
                String.valueOf(state.outputTokens()),
                String.valueOf(
                        state.inputTokens() + state.outputTokens()
                ),
                cost != null
                        ? String.format(Locale.US, "%.6f", cost)
                        : "-",
                String.format(
                        Locale.US,
                        "%.3f",
                        state.elapsed()
                ),
                runTimestamp.format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss"
                        )
                )
        ));

        System.out.println("Output file: " + outputPath);
        System.out.println("Summary updated: " + summaryFile);

        return outputPath;
    }

    private static String writeOutputFile(
            String runId,
            String content
    ) {

        try {
            Path outputPath = Path.of(
                    "./results",
                    runId,
                    runId + ".java"
            );

            Files.createDirectories(
                    outputPath.getParent()
            );

            Files.writeString(
                    outputPath,
                    content,
                    StandardCharsets.UTF_8
            );

            return outputPath.toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to write output file",
                    e
            );
        }
    }

    private static void updateSummary(
            String summaryFile,
            List<String> row
    ) {

        try {
            Path path = Path.of(summaryFile);
            boolean fileExists = Files.exists(path);

            Files.createDirectories(
                    path.getParent()
            );

            List<String> lines = new ArrayList<>();

            if (!fileExists) {
                lines.add(String.join(",",
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
                ));
            }

            lines.add(String.join(",", row));

            Files.write(
                    path,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to update summary",
                    e
            );
        }
    }

    private static int getNextRunId(
            String summaryFile,
            String sutPackage
    ) {

        Path path = Path.of(summaryFile);

        if (!Files.exists(path)) {
            return 0;
        }

        try {
            List<String> rows = Files.readAllLines(
                    path,
                    StandardCharsets.UTF_8
            );

            if (rows.size() <= 1) {
                return 0;
            }

            int maxId = -1;

            for (int i = 1; i < rows.size(); i++) {

                String[] cols = rows.get(i).split(",");

                if (cols.length == 0) {
                    continue;
                }

                String runId = cols[0];

                if (!runId.startsWith(
                        sutPackage + "-"
                )) {
                    continue;
                }

                try {
                    int currentId = Integer.parseInt(
                            runId.substring(
                                    runId.lastIndexOf("-") + 1
                            )
                    );

                    maxId = Math.max(
                            maxId,
                            currentId
                    );

                } catch (NumberFormatException ignored) {
                }
            }

            return maxId + 1;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read summary file",
                    e
            );
        }
    }
}
