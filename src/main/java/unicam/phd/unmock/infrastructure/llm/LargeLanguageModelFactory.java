package unicam.phd.unmock.infrastructure.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.config.Config;

public class LargeLanguageModelFactory {

    private static final Logger log = LoggerFactory.getLogger(LargeLanguageModelFactory.class);

    public LargeLanguageModelContext create() {
        log.info("Initializing LLM ({} - {})...",
                Config.PROVIDER,
                Config.MODEL);

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

        return new LargeLanguageModelContext(llm, Config.MODEL, Config.PROVIDER);
    }
}
