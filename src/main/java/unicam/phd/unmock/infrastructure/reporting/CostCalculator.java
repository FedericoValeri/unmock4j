package unicam.phd.unmock.infrastructure.reporting;

import java.util.Map;

public class CostCalculator {

    private static final Map<String, Map<String, Double>> MODEL_PRICING = Map.of(
            "gpt-4o-mini", Map.of(
                    "input", 0.15,
                    "output", 0.60
            ),
            "gpt-5.4-mini", Map.of(
                    "input", 0.75,
                    "output", 4.50
            ),
            "gpt-5.6-luna", Map.of(
                    "input", 0.20,
                    "output", 1.20
            ),
            "gpt-6-luna", Map.of(
                    "input", 0.10,
                    "output", 0.50
            )
    );

    public Double compute(String model,
                          long inputTokens,
                          long outputTokens) {

        if (!MODEL_PRICING.containsKey(model)) {
            return null; // oppure 0.0
        }

        Map<String, Double> pricing = MODEL_PRICING.get(model);

        return (inputTokens / 1_000_000.0) * pricing.get("input")
                + (outputTokens / 1_000_000.0) * pricing.get("output");
    }
}