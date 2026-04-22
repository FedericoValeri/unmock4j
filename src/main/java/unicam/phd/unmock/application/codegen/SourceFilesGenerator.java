package unicam.phd.unmock.application.codegen;

import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.application.pipeline.PipelineState;
import unicam.phd.unmock.infrastructure.files.JavaFileWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SourceFilesGenerator {

    private final JavaCodeExtractor extractor;
    private final JavaFileWriter writer;
    private final EmptyProxyGenerator emptyProxyGenerator;

    public SourceFilesGenerator(
            JavaCodeExtractor extractor,
            JavaFileWriter writer,
            EmptyProxyGenerator emptyProxyGenerator) {

        this.extractor = extractor;
        this.writer = writer;
        this.emptyProxyGenerator = emptyProxyGenerator;
    }

    public void create(
            PipelineState state,
            String file) {

        System.out.println("Generating final source files...");
        List<String> sutNames = extractor.extractFullClassNames(state.sut());
        String sutFullClassName = sutNames.getFirst();
        String sutPackageOnly = sutFullClassName.substring(0, sutFullClassName.lastIndexOf('.'));
        List<String> dependencyPackages = extractor.extractFullClassNames(state.dependencies());

        try {
            Path inputPath = Path.of(file).toAbsolutePath();
            Path outputDir = inputPath.getParent();

            String content = Files.readString(
                    inputPath,
                    StandardCharsets.UTF_8
            );

            generateFromBlock(
                    content,
                    "---INTEGRATION_TEST_START---",
                    "---INTEGRATION_TEST_END---",
                    outputDir
            );

            generateFromBlock(
                    content,
                    "---PROXIES_START---",
                    "---PROXIES_END---",
                    outputDir
            );

            for (String className : dependencyPackages) {

                if (className.isBlank()) continue;
                if (className.startsWith("#")) continue;

                try {
                    emptyProxyGenerator.generate(
                            className,
                            outputDir,
                            sutPackageOnly
                    );

                } catch (Exception e) {
                    System.err.println(
                            "Failed for " + className + ": " + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot generate source files", e);
        }
    }

    private void generateFromBlock(
            String content,
            String start,
            String end,
            Path outputDir) {

        String block = extractor.extractBlock(content, start, end);

        if (block == null) return;

        List<JavaClassBlock> classes = extractor.extractClasses(block);

        for (JavaClassBlock cls : classes) {
            writer.write(outputDir, cls.className(), cls.code());
        }
    }
}