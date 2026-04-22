package unicam.phd.unmock.services.infrastructure;

public class ClassLoaderService {

    public Class<?> load(String fullyQualifiedClassName) {
        try {
            return Class.forName(fullyQualifiedClassName.trim());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found in classpath: " + fullyQualifiedClassName, e);
        }
    }
}