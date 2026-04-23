package unicam.phd.unmock.infrastructure.reflection;

public class ClassLoader {

    public Class<?> load(String fullyQualifiedClassName) {
        try {
            return Class.forName(fullyQualifiedClassName.trim());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found in classpath: " + fullyQualifiedClassName, e);
        }
    }
}