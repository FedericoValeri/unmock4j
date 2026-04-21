package unicam.phd.unmock;

import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.llm.Factory;
import unicam.phd.unmock.llm.Pipeline;
import unicam.phd.unmock.models.FileType;
import unicam.phd.unmock.models.LLMContext;
import unicam.phd.unmock.models.PipelineState;
import unicam.phd.unmock.services.Loader;
import unicam.phd.unmock.services.Output;
import unicam.phd.unmock.services.PromptBuilder;
import unicam.phd.unmock.services.SourceFilesGenerator;

import java.util.List;

public class Main {

    static void main() {

        LLMContext llmContext = Factory.getLlmContext();
        String unitTest = Loader.loadFile(FileType.UNIT.name());
        String dependencies = Loader.loadFile(FileType.DEPENDENCIES.name());

        PipelineState state = new PipelineState(
                unitTest,
                "",
                ""
        );

        String verifySystemPromptPath = "prompts/system/system_prompt_for_verify.md";
        String proxySystemPromptPath = "prompts/system/system_prompt_for_proxy.md";
        List<String> steps = List.of("prompts/system/system_prompt_for_stubs.md", verifySystemPromptPath, proxySystemPromptPath);

        for (String stepPath : steps) {

            String currentUnitTest = stepPath.equals(verifySystemPromptPath) ? "" : unitTest;
            String currentDependencies = stepPath.equals(proxySystemPromptPath) ? dependencies : "";

            state = new PipelineState(
                    currentUnitTest,
                    state.partiallyTransformedTest(),
                    currentDependencies,
                    state.inputTokens(),
                    state.outputTokens(),
                    state.elapsed()
            );

            PromptTemplate prompt = PromptBuilder.buildPrompt(stepPath);
            state = Pipeline.run(llmContext, prompt, state);
        }

        String outputPath = Output.generate(state);
        SourceFilesGenerator.create(outputPath);
        System.out.println("\nDone. Pipeline finished successfully.");
    }
}