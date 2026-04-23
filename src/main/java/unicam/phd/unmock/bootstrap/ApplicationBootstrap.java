package unicam.phd.unmock.bootstrap;

import unicam.phd.unmock.application.codegen.EmptyProxyBuilder;
import unicam.phd.unmock.application.codegen.EmptyProxyService;
import unicam.phd.unmock.application.codegen.SourceFilesGenerator;
import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.application.pipeline.LargeLanguageModelOutputGenerator;
import unicam.phd.unmock.application.pipeline.Pipeline;
import unicam.phd.unmock.application.pipeline.PipelineStepExecutor;
import unicam.phd.unmock.infrastructure.files.FileWriter;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelFactory;
import unicam.phd.unmock.infrastructure.prompts.HumanPromptFileLoader;
import unicam.phd.unmock.infrastructure.prompts.PromptService;
import unicam.phd.unmock.infrastructure.reflection.ClassLoader;
import unicam.phd.unmock.infrastructure.reporting.CostCalculator;
import unicam.phd.unmock.infrastructure.reporting.RunIdGenerator;
import unicam.phd.unmock.infrastructure.reporting.SummaryWriter;

public class ApplicationBootstrap {

    public UnmockApplication create() {
        FileWriter fileWriter = new FileWriter();
        JavaCodeExtractor extractor = new JavaCodeExtractor();
        CostCalculator costCalculator = new CostCalculator();

        EmptyProxyService proxyService = new EmptyProxyService(
                new ClassLoader(), fileWriter, new EmptyProxyBuilder());

        SourceFilesGenerator sourceFilesGenerator = new SourceFilesGenerator(
                extractor, fileWriter, proxyService);

        SummaryWriter summaryWriter = new SummaryWriter(costCalculator);

        LargeLanguageModelOutputGenerator outputGenerator =
                new LargeLanguageModelOutputGenerator(
                        fileWriter,
                        summaryWriter,
                        new RunIdGenerator(),
                        extractor);

        Pipeline pipeline = new Pipeline(
                new HumanPromptFileLoader(),
                new PromptService(),
                new PipelineStepExecutor(),
                new LargeLanguageModelFactory().create()
        );

        return new UnmockApplication(pipeline, outputGenerator, sourceFilesGenerator);
    }
}