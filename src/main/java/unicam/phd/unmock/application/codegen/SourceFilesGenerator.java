package unicam.phd.unmock.application.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.application.parser.JavaCodeExtractor;
import unicam.phd.unmock.infrastructure.files.FileWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Application service responsible for generating final Java source files
 * produced by the pipeline output.
 * <p>
 * This component reads a generated text file containing structured code blocks,
 * extracts Java classes from predefined sections, writes them to disk, and
 * generates additional empty proxy classes for discovered dependencies.
 * <p>
 */
public class SourceFilesGenerator {

    private static final Logger log = LoggerFactory.getLogger(SourceFilesGenerator.class);

    private final JavaCodeExtractor javaCodeExtractor;
    private final FileWriter writer;
    private final EmptyProxyService emptyProxyService;

    public SourceFilesGenerator(
            JavaCodeExtractor javaCodeExtractor,
            FileWriter writer,
            EmptyProxyService emptyProxyService) {

        this.javaCodeExtractor = javaCodeExtractor;
        this.writer = writer;
        this.emptyProxyService = emptyProxyService;
    }

    /**
     * Reads the generated pipeline output file and creates all final source files.
     * <p>
     * The process performs the following steps:
     * <ol>
     *   <li>Extracts the SUT package name from the pipeline state.</li>
     *   <li>Reads the provided file content.</li>
     *   <li>Generates source files from known code blocks.</li>
     *   <li>Generates empty proxies for each declared dependency.</li>
     * </ol>
     *
     * @param file path to the generated text file containing code blocks
     * @throws RuntimeException if the input file cannot be read or processing fails
     */
    public void create(
            String unitTestPackage,
            List<String> dependencyPackages,
            String file) {

        try {
            Path inputPath = Path.of(file).toAbsolutePath();
            log.info("Generating final source files for {}...", inputPath);
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

            for (String mockedDependencyClassName : dependencyPackages) {

                if (mockedDependencyClassName.isBlank()) continue;
                if (mockedDependencyClassName.startsWith("#")) continue;

                try {
                    emptyProxyService.generate(
                            mockedDependencyClassName,
                            outputDir,
                            unitTestPackage
                    );

                } catch (Exception e) {
                    System.err.println(
                            "Failed for " + mockedDependencyClassName + ": " + e.getMessage()
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

        String block = javaCodeExtractor.extractBlock(content, start, end);

        if (block == null) return;

        List<JavaClassBlock> classes = javaCodeExtractor.extractClasses(block);

        for (JavaClassBlock cls : classes) {
            writer.writeClass(outputDir, cls.className(), cls.code());
        }
    }
}