package unicam.phd.unmock.services.application;

import unicam.phd.unmock.services.infrastructure.ClassLoaderService;
import unicam.phd.unmock.services.infrastructure.JavaFileWriter;
import unicam.phd.unmock.services.infrastructure.ProxyGenerator;

import java.nio.file.Path;

public class EmptyProxyGenerator {

    private final ClassLoaderService classLoaderService;
    private final JavaFileWriter javaFileWriter;
    private final ProxyGenerator proxyGenerator;

    public EmptyProxyGenerator(
            ClassLoaderService classLoaderService,
            JavaFileWriter javaFileWriter,
            ProxyGenerator proxyGenerator) {

        this.classLoaderService = classLoaderService;
        this.javaFileWriter = javaFileWriter;
        this.proxyGenerator = proxyGenerator;
    }

    public Path generate(
            String fullClassName,
            Path outputDirectory,
            String targetPackage) {

        Class<?> target = classLoaderService.load(fullClassName);

        String source = proxyGenerator.buildSource(target, targetPackage);

        return javaFileWriter.write(
                outputDirectory,
                target.getSimpleName() + "_EmptyProxy",
                source
        );
    }
}