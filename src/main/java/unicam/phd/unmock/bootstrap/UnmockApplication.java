package unicam.phd.unmock.bootstrap;

import unicam.phd.unmock.application.codegen.SourceFilesGenerator;
import unicam.phd.unmock.application.pipeline.LargeLanguageModelOutputGenerator;
import unicam.phd.unmock.application.pipeline.Pipeline;
import unicam.phd.unmock.application.pipeline.PipelineState;

public record UnmockApplication(
        Pipeline pipeline,
        LargeLanguageModelOutputGenerator outputGenerator,
        SourceFilesGenerator sourceFilesGenerator) {

    public void run() {
        // 1. LLM execution
        PipelineState state = pipeline.run();

        // 2. Create full generated LLM file and summary.csv
        String path = outputGenerator.generate(state);

        // 3. Create final source files
        sourceFilesGenerator.create(state, path);
    }
}