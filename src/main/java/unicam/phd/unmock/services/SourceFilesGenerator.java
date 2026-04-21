package unicam.phd.unmock.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceFilesGenerator {

    public static void create(String file) {

        try {
            Path inputPath = Path.of(file).toAbsolutePath();
            Path outputDir = inputPath.getParent();

            String content = Files.readString(
                    inputPath,
                    StandardCharsets.UTF_8
            );

            String integrationCode = extractBlock(
                    content,
                    "---INTEGRATION_TEST_START---",
                    "---INTEGRATION_TEST_END---"
            );

            String proxiesCode = extractBlock(
                    content,
                    "---PROXIES_START---",
                    "---PROXIES_END---"
            );

            if (integrationCode != null) {
                List<JavaClassBlock> classes =
                        extractClasses(integrationCode);

                for (JavaClassBlock cls : classes) {
                    writeJavaFile(
                            cls.className(),
                            cls.code(),
                            outputDir
                    );
                }
            }

            if (proxiesCode != null) {
                List<JavaClassBlock> classes =
                        extractClasses(proxiesCode);

                for (JavaClassBlock cls : classes) {
                    writeJavaFile(
                            cls.className(),
                            cls.code(),
                            outputDir
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to generate source files",
                    e
            );
        }
    }

    private static String extractBlock(
            String content,
            String start,
            String end
    ) {

        Pattern pattern = Pattern.compile(
                Pattern.quote(start) +
                        "(.*?)" +
                        Pattern.quote(end),
                Pattern.DOTALL
        );

        Matcher match = pattern.matcher(content);

        return match.find()
                ? match.group(1).trim()
                : null;
    }

    private static List<JavaClassBlock> extractClasses(
            String javaCode
    ) {

        List<JavaClassBlock> results =
                new ArrayList<>();

        Matcher headerMatch = Pattern.compile(
                "^(.*?)(?=\\bclass\\s+\\w+)",
                Pattern.DOTALL
        ).matcher(javaCode);

        String header = headerMatch.find()
                ? headerMatch.group(1)
                : "";

        Matcher matcher = Pattern.compile(
                "\\b(?:public|protected|private|abstract|final\\s+)*class\\s+(\\w+)"
        ).matcher(javaCode);

        while (matcher.find()) {

            String className = matcher.group(1);
            int classStart = matcher.start();

            int start = findClassStart(
                    javaCode,
                    classStart
            );

            int openBrace = javaCode.indexOf(
                    '{',
                    classStart
            );

            if (openBrace < 0) {
                continue;
            }

            int end = findMatchingBrace(
                    javaCode,
                    openBrace
            );

            if (end < 0) {
                continue;
            }

            String classBody =
                    javaCode.substring(start, end + 1);

            String fullCode =
                    (header + classBody).trim();

            results.add(
                    new JavaClassBlock(
                            className,
                            fullCode
                    )
            );
        }

        return results;
    }

    private static int findClassStart(
            String javaCode,
            int classStart
    ) {

        int start = classStart;

        String before =
                javaCode.substring(0, classStart);

        String[] lines =
                before.split("\\R", -1);

        int offset = classStart;

        for (int i = lines.length - 1; i >= 0; i--) {

            String line = lines[i];
            String trimmed = line.trim();

            offset -= line.length();

            if (i > 0) {
                offset -= 1;
            }

            if (trimmed.startsWith("@")
                    || trimmed.isEmpty()) {
                start = offset;
            } else {
                break;
            }
        }

        return Math.max(0, start);
    }

    private static int findMatchingBrace(
            String text,
            int openBrace
    ) {

        int balance = 1;

        for (int i = openBrace + 1;
             i < text.length();
             i++) {

            char c = text.charAt(i);

            if (c == '{') {
                balance++;
            } else if (c == '}') {
                balance--;
            }

            if (balance == 0) {
                return i;
            }
        }

        return -1;
    }

    private static void writeJavaFile(
            String className,
            String code,
            Path outputDir
    ) {

        try {
            Path filePath = outputDir.resolve(
                    className + ".java"
            );

            Files.writeString(
                    filePath,
                    code,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            System.out.println(
                    "Generated: " + filePath
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed writing file: " + className,
                    e
            );
        }
    }

    private record JavaClassBlock(
            String className,
            String code
    ) {
    }
}
