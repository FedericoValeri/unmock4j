package unicam.phd.unmock.application.pipeline;

public record PipelineStepConfig(
        String promptPath,
        boolean useSut,
        boolean useUnitTest,
        boolean usePartial,
        boolean useDependencies
) {
}