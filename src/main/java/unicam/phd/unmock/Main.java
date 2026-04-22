package unicam.phd.unmock;

import com.github.lalyos.jfiglet.FigletFont;
import unicam.phd.unmock.models.InputFileType;
import unicam.phd.unmock.models.PipelineState;
import unicam.phd.unmock.services.application.EmptyProxyGenerator;
import unicam.phd.unmock.services.application.LargeLanguageModelOutputGenerator;
import unicam.phd.unmock.services.application.Pipeline;
import unicam.phd.unmock.services.application.SourceFilesGenerator;
import unicam.phd.unmock.services.infrastructure.*;

import java.io.IOException;

public class Main {

    static void main() throws IOException {

        String ascii = FigletFont.convertOneLine("UnMock");
        System.out.println(ascii);

        App app = buildApp();

        String sut = HumanPromptLoader.getFileContent(InputFileType.SUT);
        String unitTest = HumanPromptLoader.getFileContent(InputFileType.UNIT);
        String dependencies = HumanPromptLoader.getFileContent(InputFileType.DEPENDENCIES);

        // 1. LLM execution
        PipelineState state = app.pipeline.run(sut, unitTest, dependencies);

        // 2. Create full generated LLM file and summary.csv
        String llmOutputFilePath = app.largeLanguageModelOutputGenerator.generate(state);

        // 3. Create final source files from LLM output
        app.sourceFilesGenerator.create(state, llmOutputFilePath);

        System.out.println("Done.");
    }


    private static App buildApp() {

        JavaFileWriter javaFileWriter = new JavaFileWriter();
        JavaCodeExtractor extractor = new JavaCodeExtractor();

        EmptyProxyGenerator emptyProxyGenerator =
                new EmptyProxyGenerator(
                        new ClassLoaderService(),
                        javaFileWriter,
                        new ProxyGenerator()
                );

        SourceFilesGenerator sourceFilesGenerator =
                new SourceFilesGenerator(
                        extractor,
                        javaFileWriter,
                        emptyProxyGenerator
                );

        LargeLanguageModelOutputGenerator largeLanguageModelOutputGenerator =
                new LargeLanguageModelOutputGenerator(
                        new ResultFileWriter(),
                        new SummaryWriter(),
                        new RunIdGenerator(),
                        extractor
                );

        PromptService promptService = new PromptService();
        PipelineStepExecutor pipelineStepExecutor = new PipelineStepExecutor();
        LargeLanguageModelFactory llmFactory = new LargeLanguageModelFactory();
        Pipeline pipeline = new Pipeline(promptService, pipelineStepExecutor, llmFactory.create());

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