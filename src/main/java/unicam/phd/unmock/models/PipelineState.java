package unicam.phd.unmock.models;

public record PipelineState(
        String unitTest,
        String partiallyTransformedTest,
        String dependencies,
        int inputTokens,
        int outputTokens,
        double elapsed
) {

    public PipelineState(String unitTest,
                         String partiallyTransformedTest,
                         String dependencies) {
        this(
                unitTest,
                partiallyTransformedTest,
                dependencies,
                0,
                0,
                0.0
        );
    }
}