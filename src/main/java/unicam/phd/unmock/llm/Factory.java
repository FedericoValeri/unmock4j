package unicam.phd.unmock.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.LLMContext;

public class Factory {

    public static LLMContext getLlmContext() {
        System.out.println("Initializing LLM (" + Config.PROVIDER + " - " + Config.MODEL + ")...");

        ChatModel llm = switch (Config.PROVIDER.toLowerCase()) {
            case "openai" -> OpenAiChatModel.builder()
                    .apiKey(Config.OPENAI_API_KEY)
                    .modelName(Config.MODEL)
                    .temperature(Config.TEMPERATURE)
                    .build();
            case "ollama" -> OllamaChatModel.builder()
                    .baseUrl("http://localhost:11434")
                    .modelName(Config.MODEL)
                    .temperature(Config.TEMPERATURE)
                    .build();
            default -> throw new IllegalArgumentException(
                    "Unsupported provider: " + Config.PROVIDER
            );
        };

        return new LLMContext(llm, Config.MODEL, Config.PROVIDER);
    }
}
