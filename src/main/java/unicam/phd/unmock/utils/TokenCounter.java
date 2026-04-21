package unicam.phd.unmock.utils;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;


public class TokenCounter {
    private static final EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();

    public static int countTokens(String text, String model, String provider) {

        if (text == null || text.isBlank()) {
            return 0;
        }

        switch (provider.toLowerCase()) {

            case "openai":
                return countOpenAiTokens(text, model);

            case "ollama":
                // heuristic fallback
                return text.length() / 4;

            default:
                throw new IllegalArgumentException(
                        "Unsupported provider: " + provider
                );
        }
    }

    private static int countOpenAiTokens(String text, String model) {

        try {
            ModelType modelType = mapModel(model);
            Encoding encoding = registry.getEncodingForModel(modelType);
            return encoding.countTokens(text);

        } catch (Exception e) {
            // fallback
            Encoding encoding = registry.getEncoding(EncodingType.valueOf("CL100K_BASE"));
            return encoding.countTokens(text);
        }
    }

    private static ModelType mapModel(String model) {

        String m = model.toLowerCase();

        if (m.contains("gpt-4o")) {
            return ModelType.GPT_4O;
        }

        if (m.contains("gpt-4")) {
            return ModelType.GPT_4;
        }

        if (m.contains("gpt-3.5")) {
            return ModelType.GPT_3_5_TURBO;
        }

        // fallback
        return ModelType.GPT_4;
    }
}
