package unicam.phd.unmock.application.pipeline;

import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelContext;
import unicam.phd.unmock.infrastructure.prompts.HumanPromptFileLoader;
import unicam.phd.unmock.infrastructure.prompts.PromptService;

import java.util.List;

public class Pipeline {

    private final HumanPromptFileLoader humanPromptFileLoader;
    private final PromptService promptService;
    private final PipelineStepExecutor pipelineStepExecutor;
    private final LargeLanguageModelContext largeLanguageModelContext;

    public Pipeline(
            HumanPromptFileLoader humanPromptFileLoader,
            PromptService promptService,
            PipelineStepExecutor pipelineStepExecutor,
            LargeLanguageModelContext largeLanguageModelContext) {

        this.humanPromptFileLoader = humanPromptFileLoader;
        this.promptService = promptService;
        this.pipelineStepExecutor = pipelineStepExecutor;
        this.largeLanguageModelContext = largeLanguageModelContext;
    }

    public PipelineState run() {

        String sut = humanPromptFileLoader.getFileContent(InputFileType.SUT);
        String unitTest = humanPromptFileLoader.getFileContent(InputFileType.UNIT);
        String dependencies = humanPromptFileLoader.getFileContent(InputFileType.DEPENDENCIES);

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

            System.out.println("Running pipeline step for " + step + "...");

            state = pipelineStepExecutor.run(
                    largeLanguageModelContext,
                    prompt,
                    state
            );
        }

        return state;
    }
}
