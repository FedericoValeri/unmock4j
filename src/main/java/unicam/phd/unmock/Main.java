package unicam.phd.unmock;


import dev.langchain4j.model.input.PromptTemplate;
import unicam.phd.unmock.llm.Factory;
import unicam.phd.unmock.llm.Pipeline;
import unicam.phd.unmock.models.FileType;
import unicam.phd.unmock.models.LLMContext;
import unicam.phd.unmock.models.PipelineState;
import unicam.phd.unmock.services.*;

import java.util.List;

public class Main {

    static void main() {

        // =====================================================
        // MANUAL DEPENDENCY INJECTION
        // =====================================================

        ClassLoaderService classLoaderService = new ClassLoaderService();
        JavaFileWriter javaFileWriter = new JavaFileWriter();
        JavaBlockExtractor javaBlockExtractor = new JavaBlockExtractor();
        ProxyGenerator proxyGenerator = new ProxyGenerator();

        EmptyProxyGenerator emptyProxyGenerator =
                new EmptyProxyGenerator(
                        classLoaderService,
                        javaFileWriter,
                        proxyGenerator
                );

        SourceFilesGenerator sourceFilesGenerator =
                new SourceFilesGenerator(
                        javaBlockExtractor,
                        javaFileWriter,
                        emptyProxyGenerator
                );


        // =====================================================
        // PIPELINE
        // =====================================================

        LLMContext llmContext = Factory.getLlmContext();

        String unitTest = HumanPromptLoader.getFileContent(FileType.UNIT.name());
        String sut = HumanPromptLoader.getFileContent(FileType.SUT.name());
        String dependencies = HumanPromptLoader.getFileContent(FileType.DEPENDENCIES.name());


        PipelineState state = new PipelineState(
                unitTest,
                "",
                ""
        );

        String verifySystemPromptPath = "prompts/system/system_prompt_for_verify.md";

        String proxySystemPromptPath = "prompts/system/system_prompt_for_proxy.md";

        List<String> steps = List.of(
                "prompts/system/system_prompt_for_stubs.md",
                verifySystemPromptPath,
                proxySystemPromptPath
        );

        for (String stepPath : steps) {

            String currentUnitTest =
                    stepPath.equals(verifySystemPromptPath)
                            ? ""
                            : unitTest;

            String currentDependencies =
                    stepPath.equals(proxySystemPromptPath)
                            ? dependencies
                            : "";

            state = new PipelineState(
                    currentUnitTest,
                    state.partiallyTransformedTest(),
                    currentDependencies,
                    state.inputTokens(),
                    state.outputTokens(),
                    state.elapsed()
            );

            PromptTemplate prompt = PromptBuilder.buildPrompt(stepPath);

            state = Pipeline.run(
                    llmContext,
                    prompt,
                    state
            );
        }

        // =====================================================
        // OUTPUT + GENERATED JAVA FILES
        // =====================================================
        List<String> sutPackageList = javaBlockExtractor.extractFullClassNames(sut);
        String sutPackage = sutPackageList.getFirst();
        List<String> dependencyPackages = javaBlockExtractor.extractFullClassNames(dependencies);
        String sutPackageOnly = sutPackage.substring(0, sutPackage.lastIndexOf('.'));

        String outputPath = Output.generate(state, sutPackage);

        sourceFilesGenerator.create(
                outputPath,
                dependencyPackages,
                sutPackageOnly
        );

        System.out.println("\nDone. Pipeline finished successfully.");
    }
}