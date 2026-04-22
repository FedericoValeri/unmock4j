package unicam.phd.unmock.services.infrastructure;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.LargeLanguageModelContext;
import unicam.phd.unmock.models.PipelineState;

import java.util.Map;

import static unicam.phd.unmock.utils.TokenCounter.countTokens;

/**
 * Executes one pipeline step against the LLM.
 */
public class PipelineStepExecutor {

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