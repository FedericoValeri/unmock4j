package unicam.phd.unmock.application.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullClassNamesExtractor {
    /**
     * Extracts fully qualified names of classes or interfaces declared in the
     * provided source code.
     * <p>
     * The method detects the package declaration and combines it with each
     * discovered class or interface name.
     *
     * @param source Java source text
     * @return list of fully qualified names in declaration order
     */
    public List<String> extract(String source) {
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
}
