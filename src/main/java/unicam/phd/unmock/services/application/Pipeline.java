package unicam.phd.unmock.services.application;

import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.models.LargeLanguageModelContext;
import unicam.phd.unmock.models.PipelineState;
import unicam.phd.unmock.services.infrastructure.PipelineStepExecutor;
import unicam.phd.unmock.services.infrastructure.PromptService;

import java.util.List;

public class Pipeline {

    private final PromptService promptService;
    private final PipelineStepExecutor pipelineStepExecutor;
    private final LargeLanguageModelContext largeLanguageModelContext;

    public Pipeline(
            PromptService promptService,
            PipelineStepExecutor pipelineStepExecutor,
            LargeLanguageModelContext largeLanguageModelContext) {

        this.promptService = promptService;
        this.pipelineStepExecutor = pipelineStepExecutor;
        this.largeLanguageModelContext = largeLanguageModelContext;
    }

    public PipelineState run(String sut, String unitTest, String dependencies) {

        PipelineState state = new PipelineState(sut, unitTest, "", "");

        String verifyPromptPath = "prompts/system/system_prompt_for_verify.md";
        String proxyPromptPath = "prompts/system/system_prompt_for_proxy.md";

        List<String> steps = List.of(
                "prompts/system/system_prompt_for_stubs.md",
                verifyPromptPath,
                proxyPromptPath
        );

        for (String step : steps) {

            String currentUnitTest = step.equals(verifyPromptPath) ? "" : unitTest;

            String currentDependencies = step.equals(proxyPromptPath) ? dependencies : "";

            state = new PipelineState(
                    sut,
                    currentUnitTest,
                    state.partiallyTransformedTest(),
                    currentDependencies,
                    state.inputTokens(),
                    state.outputTokens(),
                    state.elapsed()
            );

            PromptTemplate prompt = promptService.build(step);

            state = pipelineStepExecutor.run(
                    largeLanguageModelContext,
                    prompt,
                    state
            );
        }

        return state;
    }
}
