package unicam.phd.unmock.utils;

import dev.langchain4j.data.document.Document;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentHelper {
    public static String joinChunks(List<Document> chunks) {

        if (chunks == null || chunks.isEmpty()) {
            return "";
        }

        return chunks.stream()
                .map(Document::text)
                .collect(Collectors.joining("\n\n"));
    }
}
