package unicam.phd.unmock.application.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.infrastructure.files.FileWriter;
import unicam.phd.unmock.infrastructure.reporting.RunIdGenerator;
import unicam.phd.unmock.infrastructure.reporting.SummaryWriter;

import java.nio.file.Path;
import java.util.List;

/**
 * Application service responsible for persisting the final large language model
 * output and updating execution summaries.
 * <p>
 * This component generates a unique run identifier for the current pipeline
 * execution, stores the transformed test output, and appends metadata to the
 * summary report.
 * <p>
 * The generated run identifier follows the pattern:
 * {@code <fullyQualifiedSutName>-<progressiveId>}.
 */
public class LargeLanguageModelOutputGenerator {

    private static final Logger log = LoggerFactory.getLogger(LargeLanguageModelOutputGenerator.class);

    private final FileWriter fileWriter;
    private final SummaryWriter summaryWriter;
    private final RunIdGenerator runIdGenerator;
    private final JavaCodeExtractor javaCodeExtractor;

    public LargeLanguageModelOutputGenerator(
            FileWriter fileWriter,
            SummaryWriter summaryWriter,
            RunIdGenerator runIdGenerator,
            JavaCodeExtractor javaCodeExtractor) {

        this.fileWriter = fileWriter;
        this.summaryWriter = summaryWriter;
        this.runIdGenerator = runIdGenerator;
        this.javaCodeExtractor = javaCodeExtractor;
    }

    /**
     * Generates the persisted output for the current pipeline state and updates
     * the summary report.
     * <p>
     * The process performs the following steps:
     * <ol>
     *   <li>Extracts the fully qualified SUT name.</li>
     *   <li>Computes the next available run identifier.</li>
     *   <li>Writes the transformed test output to disk.</li>
     *   <li>Appends execution metadata to {@code results/summary.csv}.</li>
     * </ol>
     *
     * @param state current pipeline state
     * @return path of the generated output file as a string
     */
    public String generate(PipelineState state) {

        log.info("Generating llm output and summary...");

        List<String> sutNames = javaCodeExtractor.extractFullClassNames(state.sut());
        String sutFullClassName = sutNames.getFirst();

        Path summaryFile = Path.of("results", "summary.csv");
        int nextId = runIdGenerator.next(summaryFile, sutFullClassName);
        String runId = sutFullClassName + "-" + nextId;

        Path outputFile = fileWriter.writeRunResult(
                runId,
                state.partiallyTransformedTest()
        );

        summaryWriter.append(
                summaryFile,
                runId,
                sutFullClassName,
                state
        );

        return outputFile.toString();
    }
}