package unicam.phd.unmock.application.pipeline;

public record PipelineState(
        String sut,
        String unitTest,
        String partiallyTransformedTest,
        String dependencies,
        int inputTokens,
        int outputTokens,
        double elapsed
) {

    public PipelineState(String sut,
                         String unitTest,
                         String partiallyTransformedTest,
                         String dependencies) {
        this(
                sut,
                unitTest,
                partiallyTransformedTest,
                dependencies,
                0,
                0,
                0.0
        );
    }
}