package unicam.phd.unmock.application.codegen;

import unicam.phd.unmock.infrastructure.files.FileWriter;
import unicam.phd.unmock.infrastructure.reflection.ClassLoader;

import java.nio.file.Path;

/**
 * Application service responsible for generating and writing an "empty proxy"
 * implementation for a target class.
 * <p>
 * The generation workflow is:
 * <ol>
 *   <li>Load the target class by its fully qualified name.</li>
 *   <li>Delegate source code creation to {@link EmptyProxyBuilder}.</li>
 *   <li>Write the generated source file to the specified output directory.</li>
 * </ol>
 * <p>
 */
public class EmptyProxyService {

    private final ClassLoader classLoader;
    private final FileWriter fileWriter;
    private final EmptyProxyBuilder emptyProxyBuilder;

    public EmptyProxyService(
            ClassLoader classLoader,
            FileWriter fileWriter,
            EmptyProxyBuilder emptyProxyBuilder) {

        this.classLoader = classLoader;
        this.fileWriter = fileWriter;
        this.emptyProxyBuilder = emptyProxyBuilder;
    }

    /**
     * Generates an empty proxy class for the given target type and writes it to disk.
     * <p>
     * The generated file name follows the convention:
     * {@code <SimpleClassName>_EmptyProxy}.
     *
     * @param fullClassName   fully qualified name of the target class to proxy
     * @param outputDirectory directory where the generated source file will be written
     * @param targetPackage   package declaration to use for the generated class
     * @throws RuntimeException if the class cannot be loaded or the file cannot be written
     */
    public void generate(
            String fullClassName,
            Path outputDirectory,
            String targetPackage) {

        Class<?> target = classLoader.load(fullClassName);

        String source = emptyProxyBuilder.buildSource(target, targetPackage);

        fileWriter.writeClass(
                outputDirectory,
                target.getSimpleName() + "_EmptyProxy",
                source
        );
    }
}