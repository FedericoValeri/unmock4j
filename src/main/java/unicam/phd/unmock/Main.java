package unicam.phd.unmock;

import com.github.lalyos.jfiglet.FigletFont;
import unicam.phd.unmock.application.codegen.EmptyProxyBuilder;
import unicam.phd.unmock.application.codegen.EmptyProxyService;
import unicam.phd.unmock.application.codegen.SourceFilesGenerator;
import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.application.pipeline.LargeLanguageModelOutputGenerator;
import unicam.phd.unmock.application.pipeline.Pipeline;
import unicam.phd.unmock.application.pipeline.PipelineState;
import unicam.phd.unmock.application.pipeline.PipelineStepExecutor;
import unicam.phd.unmock.infrastructure.files.FileWriter;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelFactory;
import unicam.phd.unmock.infrastructure.prompts.HumanPromptFileLoader;
import unicam.phd.unmock.infrastructure.prompts.PromptService;
import unicam.phd.unmock.infrastructure.reflection.ClassLoader;
import unicam.phd.unmock.infrastructure.reporting.RunIdGenerator;
import unicam.phd.unmock.infrastructure.reporting.SummaryWriter;

import java.io.IOException;

public class Main {

    static void main() throws IOException {

        String ascii = FigletFont.convertOneLine("UnMock");
        System.out.println(ascii);

        App app = buildApp();

        // 1. LLM execution
        PipelineState finalState = app.pipeline.run();

        // 2. Create full generated LLM file and summary.csv
        String llmOutputFilePath = app.largeLanguageModelOutputGenerator.generate(finalState);

        // 3. Create final source files from LLM output
        app.sourceFilesGenerator.create(finalState, llmOutputFilePath);

        System.out.println("Done.");
    }


    private static App buildApp() {

        FileWriter fileWriter = new FileWriter();
        JavaCodeExtractor extractor = new JavaCodeExtractor();

        EmptyProxyService emptyProxyService =
                new EmptyProxyService(
                        new ClassLoader(),
                        fileWriter,
                        new EmptyProxyBuilder()
                );

        SourceFilesGenerator sourceFilesGenerator =
                new SourceFilesGenerator(
                        extractor,
                        fileWriter,
                        emptyProxyService
                );

        LargeLanguageModelOutputGenerator largeLanguageModelOutputGenerator =
                new LargeLanguageModelOutputGenerator(
                        fileWriter,
                        new SummaryWriter(),
                        new RunIdGenerator(),
                        extractor
                );

        HumanPromptFileLoader humanPromptFileLoader = new HumanPromptFileLoader();
        PromptService promptService = new PromptService();
        PipelineStepExecutor pipelineStepExecutor = new PipelineStepExecutor();
        LargeLanguageModelFactory llmFactory = new LargeLanguageModelFactory();
        Pipeline pipeline = new Pipeline(humanPromptFileLoader, promptService, pipelineStepExecutor, llmFactory.create());

        return new App(
                pipeline,
                largeLanguageModelOutputGenerator,
                sourceFilesGenerator
        );
    }

    private record App(
            Pipeline pipeline,
            LargeLanguageModelOutputGenerator largeLanguageModelOutputGenerator,
            SourceFilesGenerator sourceFilesGenerator
    ) {
    }
}