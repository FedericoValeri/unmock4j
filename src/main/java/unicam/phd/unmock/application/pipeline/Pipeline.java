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

        PipelineState state = new PipelineState(sut, unitTest, "", dependencies);

        List<PipelineStepConfig> steps = getPipelineStepConfigs();

        for (PipelineStepConfig step : steps) {

            PromptTemplate prompt = promptService.build(step.promptPath(), step);

            log.info("Running pipeline step for {} ...", step.promptPath());

            state = pipelineStepExecutor.run(
                    largeLanguageModelContext,
                    prompt,
                    state
            );
        }

        return state;
    }

    private static List<PipelineStepConfig> getPipelineStepConfigs() {
        String baseSystemPromptPath = "prompts/system/";
        String stubsPromptPath = baseSystemPromptPath + "system_prompt_for_stubs.md";
        String verifyPromptPath = baseSystemPromptPath + "system_prompt_for_verify.md";
        String proxyPromptPath = baseSystemPromptPath + "system_prompt_for_proxy.md";
        String integrationTestPromptPath = baseSystemPromptPath + "system_prompt_for_integrationTest.md";

        return List.of(
                new PipelineStepConfig(stubsPromptPath, true, true, false, false),
                new PipelineStepConfig(verifyPromptPath, true, false, true, false),
                new PipelineStepConfig(proxyPromptPath, true, true, true, true),
                new PipelineStepConfig(integrationTestPromptPath, true, false, true, false)
        );
    }
}
