package unicam.phd.unmock.services.application;

import unicam.phd.unmock.models.PipelineState;
import unicam.phd.unmock.services.infrastructure.JavaCodeExtractor;
import unicam.phd.unmock.services.infrastructure.ResultFileWriter;
import unicam.phd.unmock.services.infrastructure.RunIdGenerator;
import unicam.phd.unmock.services.infrastructure.SummaryWriter;

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