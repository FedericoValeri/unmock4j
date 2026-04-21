package unicam.phd.unmock.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaBlockExtractor {

    public List<String> extractFullClassNames(String source) {
        List<String> fullNames = new ArrayList<>();

        // Questa regex cerca il package e POI la parola interface/class con il relativo nome
        // Utilizziamo Pattern.DOTALL per permettere al .*? di saltare gli import tra package e classe
        Pattern pattern = Pattern.compile(
                "package\\s+([\\w.]+)\\s*;.*?\\b(?:interface|class)\\s+(\\w+)",
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            String packageName = matcher.group(1);
            String className = matcher.group(2);
            fullNames.add(packageName + "." + className);
        }

        return fullNames;
    }

    public String extractBlock(String content, String start, String end) {

        Pattern pattern = Pattern.compile(
                Pattern.quote(start) + "(.*?)" + Pattern.quote(end),
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(content);

        return matcher.find() ? matcher.group(1).trim() : null;
    }

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

    public record JavaClassBlock(String className, String code) {
    }
}