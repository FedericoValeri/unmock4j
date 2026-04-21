package unicam.phd.unmock.models;

import dev.langchain4j.model.chat.ChatModel;

public record LLMContext(
        ChatModel llm,
        String model,
        String provider
) {
}