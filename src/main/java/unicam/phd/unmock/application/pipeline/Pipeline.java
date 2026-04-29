package unicam.phd.unmock.application.pipeline;

import dev.langchain4j.model.input.PromptTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelContext;
import unicam.phd.unmock.infrastructure.prompts.HumanPromptFileLoader;
import unicam.phd.unmock.infrastructure.prompts.PromptService;

import java.util.List;

/**
 * Orchestrates the complete transformation pipeline executed through a large
 * language model.
 * <p>
 * This class loads user-provided inputs, executes a predefined sequence of
 * prompt-driven steps, and returns the final accumulated {@link PipelineState}.
 * <p>
 * The pipeline currently consists of:
 * <ol>
 *   <li>Stub generation</li>
 *   <li>Verification</li>
 *   <li>Proxy generation</li>
 * </ol>
 * Each step may use a different subset of the available inputs depending on
 * its purpose.
 */
public class Pipeline {

    private static final Logger log = LoggerFactory.getLogger(Pipeline.class);

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

    /**
     * Executes the full pipeline and returns the resulting state.
     * <p>
     * The method performs the following operations:
     * <ol>
     *   <li>Loads the SUT, unit test, and dependency inputs.</li>
     *   <li>Initializes the pipeline state.</li>
     *   <li>Runs each configured prompt step in sequence.</li>
     *   <li>Accumulates generated content, token usage, and timing data.</li>
     * </ol>
     *
     * @return final pipeline state after all steps have completed
     */
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

            // I pass original unit test file only to the system prompt for stubs and proxy
            String currentUnitTest = step.equals(verifyPromptPath) ? "" : unitTest;

            // I pass dependencies file only to the system prompt for proxy
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

            log.info("Running pipeline step for {} ...", step);

            state = pipelineStepExecutor.run(
                    largeLanguageModelContext,
                    prompt,
                    state
            );
        }

        return state;
    }
}
