package unicam.phd.unmock.application.parser;

import unicam.phd.unmock.application.codegen.JavaClassBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Component for extracting Java code structures from raw text.
 * <p>
 * This class provides lightweight parsing based on regular expressions and
 * brace matching. It is designed for generated or controlled Java source text,
 * where a full compiler parser would be unnecessary.
 * <p>
 */
public class JavaCodeExtractor {


    /**
     * Extracts the text contained between two marker strings.
     *
     * @param content full source text
     * @param start   start marker
     * @param end     end marker
     * @return trimmed content between markers, or {@code null} if not found
     */
    public String extractBlock(String content, String start, String end) {

        Pattern pattern = Pattern.compile(
                Pattern.quote(start) + "(.*?)" + Pattern.quote(end),
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(content);

        return matcher.find() ? matcher.group(1).trim() : null;
    }

    /**
     * Extracts individual class declarations from a Java code block.
     * <p>
     * Shared header content (such as package and imports) is preserved and
     * prepended to each extracted class.
     *
     * @param javaCode Java source block containing one or more classes
     * @return list of extracted class blocks
     */
    public List<JavaClassBlock> extractClasses(String javaCode) {

        List<JavaClassBlock> results = new ArrayList<>();

        Matcher headerMatch = Pattern.compile(
                "^(.*?)(?=\\bclass\\s+\\w+)",
                Pattern.DOTALL
        ).matcher(javaCode);

        String header = headerMatch.find() ? headerMatch.group(1) : "";

        Matcher matcher = Pattern.compile(
                "\\b(?:public|protected|private|abstract|final\\s+)*class\\s+(\\w+)"
        ).matcher(javaCode);

        while (matcher.find()) {

            String className = matcher.group(1);
            int classStart = matcher.start();

            int start = findClassStart(javaCode, classStart);
            int openBrace = javaCode.indexOf('{', classStart);

            if (openBrace < 0) continue;

            int end = findMatchingBrace(javaCode, openBrace);

            if (end < 0) continue;

            String classBody = javaCode.substring(start, end + 1);
            String fullCode = (header + classBody).trim();

            results.add(new JavaClassBlock(className, fullCode));
        }

        return results;
    }

    private int findClassStart(String code, int classStart) {

        int start = classStart;
        String before = code.substring(0, classStart);

        String[] lines = before.split("\\R", -1);

        int offset = classStart;

        for (int i = lines.length - 1; i >= 0; i--) {

            String line = lines[i];
            String trimmed = line.trim();

            offset -= line.length();
            if (i > 0) offset--;

            if (trimmed.startsWith("@") || trimmed.isEmpty()) {
                start = offset;
            } else {
                break;
            }
        }

        return Math.max(0, start);
    }

    private int findMatchingBrace(String text, int openBrace) {

        int balance = 1;

        for (int i = openBrace + 1; i < text.length(); i++) {

            char c = text.charAt(i);

            if (c == '{') balance++;
            else if (c == '}') balance--;

            if (balance == 0) return i;
        }

        return -1;
    }

}