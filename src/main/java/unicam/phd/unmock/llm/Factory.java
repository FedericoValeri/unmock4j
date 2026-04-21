package unicam.phd.unmock.llm;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.models.LLMContext;

public class Factory {

    public static LLMContext getLlmContext() {

        ChatModel llm;

        switch (Config.PROVIDER.toLowerCase()) {

            case "openai":
                llm = OpenAiChatModel.builder()
                        .apiKey(Config.OPENAI_API_KEY)
                        .modelName(Config.MODEL)
                        .temperature(Config.TEMPERATURE)
                        .build();
                break;

            case "ollama":
                llm = OllamaChatModel.builder()
                        .baseUrl("http://localhost:11434")
                        .modelName(Config.MODEL)
                        .temperature(Config.TEMPERATURE)
                        .build();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported provider: " + Config.PROVIDER
                );
        }

        return new LLMContext(llm, Config.MODEL, Config.PROVIDER);
    }
}
