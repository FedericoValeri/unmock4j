package unicam.phd.unmock.bootstrap;

import unicam.phd.unmock.application.codegen.SourceFilesGenerator;
import unicam.phd.unmock.application.parser.FullClassNamesExtractor;
import unicam.phd.unmock.application.pipeline.LargeLanguageModelOutputGenerator;
import unicam.phd.unmock.application.pipeline.Pipeline;
import unicam.phd.unmock.application.pipeline.PipelineState;

import java.util.List;

public record UnmockApplication(
        Pipeline pipeline,
        LargeLanguageModelOutputGenerator outputGenerator,
        SourceFilesGenerator sourceFilesGenerator,
        FullClassNamesExtractor fullClassNamesExtractor) {

    public void run() {

        // 1. LLM execution
        PipelineState state = pipeline.run();

        // 2. Create full generated LLM file and summary.csv
        List<String> sutNames = fullClassNamesExtractor.extract(state.sut());
        String sutFullClassName = sutNames.getFirst();
        String path = outputGenerator.generate(sutFullClassName, state.partiallyTransformedTest(), state.inputTokens(), state.outputTokens(), state.elapsed());

        // 3. Create final source files
        List<String> unitTestNames = fullClassNamesExtractor.extract(state.unitTest());
        String unitTestFullClassName = unitTestNames.getFirst();
        String unitTestPackage = unitTestFullClassName.substring(0, unitTestFullClassName.lastIndexOf('.'));
        List<String> dependencyPackages = fullClassNamesExtractor.extract(state.dependencies());
        sourceFilesGenerator.create(unitTestPackage, dependencyPackages, path);
    }
}