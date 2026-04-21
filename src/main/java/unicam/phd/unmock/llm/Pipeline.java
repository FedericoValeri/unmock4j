package unicam.phd.unmock.llm;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.LLMContext;
import unicam.phd.unmock.models.PipelineState;

import java.util.Map;

import static unicam.phd.unmock.utils.TokenCounter.countTokens;

public class Pipeline {

    public static PipelineState run(
            LLMContext context,
            PromptTemplate prompt,
            PipelineState state
    ) {

        System.out.println("Running pipeline step...");

        Prompt renderedPrompt = prompt.apply(Map.of(
                "unit_test", state.unitTest(),
                "partially_transformed_test", state.partiallyTransformedTest(),
                "dependencies", state.dependencies()
        ));

        String fullPromptText = renderedPrompt.text();

        int inputTokens = countTokens(
                fullPromptText,
                context.model(),
                context.provider()
        ) + state.inputTokens();

        if (inputTokens > Config.MAX_INPUT_TOKENS) {
            throw new IllegalArgumentException(
                    "Prompt too large: " + inputTokens +
                            " tokens (max " + Config.MAX_INPUT_TOKENS + ")"
            );
        }

        long start = System.nanoTime();

        String result;

        try {
            ChatResponse response = context.llm().chat(UserMessage.from(renderedPrompt.text()));
            result = response.aiMessage().text();

        } catch (Exception e) {
            throw new RuntimeException(
                    "LLM call failed: " + e.getMessage(),
                    e
            );
        }

        double elapsed =
                (System.nanoTime() - start) / 1_000_000_000.0;

        int outputTokens = countTokens(
                result,
                context.model(),
                context.provider()
        ) + state.outputTokens();

        return new PipelineState(
                state.unitTest(),
                result,
                state.dependencies(),
                inputTokens,
                outputTokens,
                state.elapsed() + elapsed
        );
    }
}
