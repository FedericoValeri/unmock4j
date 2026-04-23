package unicam.phd.unmock.application.pipeline;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelContext;

import java.util.Map;

import static unicam.phd.unmock.infrastructure.llm.TokenCounter.countTokens;


/**
 * Executes a single pipeline step against a configured large language model.
 * <p>
 * A step consists of:
 * <ol>
 *   <li>Rendering a prompt template using the current {@link PipelineState}.</li>
 *   <li>Counting and validating input tokens.</li>
 *   <li>Calling the language model.</li>
 *   <li>Counting output tokens.</li>
 *   <li>Returning an updated pipeline state with new content and metrics.</li>
 * </ol>
 */
public class PipelineStepExecutor {

    /**
     * Runs one pipeline step and returns the updated state.
     *
     * @param context        language model runtime context, including provider,
     *                       model, and chat client
     * @param promptTemplate template used to build the prompt for this step
     * @param state          current pipeline state
     * @return updated pipeline state containing generated output, token counts,
     * and elapsed execution time
     * @throws IllegalArgumentException if the rendered prompt exceeds the
     *                                  configured token limit
     * @throws RuntimeException         if the language model invocation fails
     */
    public PipelineState run(
            LargeLanguageModelContext context,
            PromptTemplate promptTemplate,
            PipelineState state) {

        Prompt renderedPrompt = renderPrompt(
                promptTemplate,
                state
        );

        int inputTokens = computeInputTokens(
                renderedPrompt.text(),
                context,
                state
        );

        validateInputLimit(inputTokens);

        long start = System.nanoTime();

        String result = callLargeLanguageModel(
                context,
                renderedPrompt.text()
        );

        double elapsedSeconds = (System.nanoTime() - start) / 1_000_000_000.0;

        int outputTokens = computeOutputTokens(
                result,
                context,
                state
        );

        return new PipelineState(
                state.sut(),
                state.unitTest(),
                result,
                state.dependencies(),
                inputTokens,
                outputTokens,
                state.elapsed() + elapsedSeconds
        );
    }

    private Prompt renderPrompt(
            PromptTemplate promptTemplate,
            PipelineState state) {

        return promptTemplate.apply(
                Map.of(
                        "sut",
                        state.sut(),

                        "unit_test",
                        state.unitTest(),

                        "partially_transformed_test",
                        state.partiallyTransformedTest(),

                        "dependencies",
                        state.dependencies()
                )
        );
    }

    private int computeInputTokens(
            String promptText,
            LargeLanguageModelContext context,
            PipelineState state) {

        return countTokens(
                promptText,
                context.model(),
                context.provider()
        ) + state.inputTokens();
    }

    private int computeOutputTokens(
            String result,
            LargeLanguageModelContext context,
            PipelineState state) {

        return countTokens(
                result,
                context.model(),
                context.provider()
        ) + state.outputTokens();
    }

    private void validateInputLimit(int inputTokens) {

        if (inputTokens > Config.MAX_INPUT_TOKENS) {
            throw new IllegalArgumentException(
                    "Prompt too large: "
                            + inputTokens
                            + " tokens (max "
                            + Config.MAX_INPUT_TOKENS
                            + ")"
            );
        }
    }

    private String callLargeLanguageModel(
            LargeLanguageModelContext context,
            String promptText) {

        try {
            ChatResponse response = context.llm().chat(UserMessage.from(promptText));
            return response.aiMessage().text();

        } catch (Exception e) {
            throw new RuntimeException("LLM call failed", e);
        }
    }
}