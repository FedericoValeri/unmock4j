package unicam.phd.unmock.infrastructure.llm;

import dev.langchain4j.model.chat.ChatModel;

public record LargeLanguageModelContext(
        ChatModel llm,
        String model,
        String provider
) {
}