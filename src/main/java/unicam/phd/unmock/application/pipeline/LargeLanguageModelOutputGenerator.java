package unicam.phd.unmock.application.pipeline;

import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.infrastructure.files.ResultFileWriter;
import unicam.phd.unmock.infrastructure.reporting.RunIdGenerator;
import unicam.phd.unmock.infrastructure.reporting.SummaryWriter;

import java.nio.file.Path;
import java.util.List;

/**
 * High-level orchestrator for writing generated output
 * and updating execution summary.
 */
public class LargeLanguageModelOutputGenerator {

    private final ResultFileWriter resultFileWriter;
    private final SummaryWriter summaryWriter;
    private final RunIdGenerator runIdGenerator;
    private final JavaCodeExtractor javaCodeExtractor;

    public LargeLanguageModelOutputGenerator(
            ResultFileWriter resultFileWriter,
            SummaryWriter summaryWriter,
            RunIdGenerator runIdGenerator,
            JavaCodeExtractor javaCodeExtractor) {

        this.resultFileWriter = resultFileWriter;
        this.summaryWriter = summaryWriter;
        this.runIdGenerator = runIdGenerator;
        this.javaCodeExtractor = javaCodeExtractor;
    }

    public String generate(PipelineState state) {

        System.out.println("Generating llm output and summary...");
        List<String> sutNames = javaCodeExtractor.extractFullClassNames(state.sut());
        String sutFullClassName = sutNames.getFirst();

        Path summaryFile = Path.of("results", "summary.csv");
        int nextId = runIdGenerator.next(summaryFile, sutFullClassName);
        String runId = sutFullClassName + "-" + nextId;

        Path outputFile = resultFileWriter.write(
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