package unicam.phd.unmock.services;

import java.beans.Introspector;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Pure source generator.
 * Builds proxy Java source code as String.
 * Does NOT write files and does NOT load classes.
 */
public class ProxyGenerator {

    public String buildSource(Class<?> target, String packageName) {

        String simpleName = target.getSimpleName();
        String proxyName = simpleName + "_EmptyProxy";
        String fieldName = Introspector.decapitalize(simpleName);

        Map<String, String> generics = resolveTypeVariables(target);
        Set<String> imports = collectImports(target);
        addTargetImport(imports, target);

        StringBuilder sb = new StringBuilder();

        // package
        if (packageName != null && !packageName.isBlank()) {
            sb.append("package ")
                    .append(packageName)
                    .append(";\n\n");
        }

        // imports
        for (String imp : imports) {
            sb.append("import ").append(imp).append(";\n");
        }

        if (!imports.isEmpty()) {
            sb.append("\n");
        }

        // class declaration
        sb.append("public class ")
                .append(proxyName)
                .append(target.isInterface() ? " implements " : " extends ")
                .append(simpleName)
                .append(" {\n\n");

        // delegate field
        sb.append("    protected final ")
                .append(simpleName)
                .append(" ")
                .append(fieldName)
                .append(";\n\n");

        // constructor
        sb.append("    public ")
                .append(proxyName)
                .append("(")
                .append(simpleName)
                .append(" ")
                .append(fieldName)
                .append(") {\n")
                .append("        this.")
                .append(fieldName)
                .append(" = ")
                .append(fieldName)
                .append(";\n")
                .append("    }\n\n");

        // methods
        Set<String> emitted = new HashSet<>();

        for (Method method : target.getMethods()) {

            if (method.getDeclaringClass() == Object.class) continue;
            if (method.isBridge()) continue;
            if (method.isSynthetic()) continue;
            if (Modifier.isStatic(method.getModifiers())) continue;
            if (Modifier.isFinal(method.getModifiers())) continue;
            if (Modifier.isPrivate(method.getModifiers())) continue;

            String signature = signatureOf(method, generics);

            if (!emitted.add(signature)) {
                continue;
            }

            sb.append(buildMethod(method, fieldName, generics));
        }

        sb.append("}\n");

        return sb.toString();
    }

    // =====================================================
    // METHOD GENERATION
    // =====================================================

    private String buildMethod(
            Method method,
            String fieldName,
            Map<String, String> classGenerics) {

        Map<String, String> generics = new LinkedHashMap<>(classGenerics);
        resolveMethodTypeVariables(method, generics);

        StringBuilder sb = new StringBuilder();

        sb.append("    @Override\n");
        sb.append("    public ")
                .append(typeName(method.getGenericReturnType(), generics))
                .append(" ")
                .append(method.getName())
                .append("(");

        Parameter[] params = method.getParameters();

        for (int i = 0; i < params.length; i++) {
            if (i > 0) sb.append(", ");

            sb.append(typeName(params[i].getParameterizedType(), generics))
                    .append(" ")
                    .append(parameterName(params[i], i));
        }

        sb.append(")");

        Class<?>[] exceptions = method.getExceptionTypes();

        if (exceptions.length > 0) {
            sb.append(" throws ");

            for (int i = 0; i < exceptions.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(exceptions[i].getSimpleName());
            }
        }

        sb.append(" {\n");

        String args = Arrays.stream(params)
                .map(p -> parameterName(p, indexOf(params, p)))
                .collect(Collectors.joining(", "));

        if (method.getReturnType() == Void.TYPE) {
            sb.append("        ")
                    .append(fieldName)
                    .append(".")
                    .append(method.getName())
                    .append("(")
                    .append(args)
                    .append(");\n");
        } else {
            sb.append("        return ")
                    .append(fieldName)
                    .append(".")
                    .append(method.getName())
                    .append("(")
                    .append(args)
                    .append(");\n");
        }

        sb.append("    }\n\n");

        return sb.toString();
    }

    // =====================================================
    // GENERIC RESOLUTION
    // =====================================================

    private Map<String, String> resolveTypeVariables(Class<?> target) {
        Map<String, String> map = new LinkedHashMap<>();
        resolveHierarchy(target, map);
        return map;
    }

    private void resolveHierarchy(Class<?> clazz, Map<String, String> map) {

        if (clazz == null || clazz == Object.class) {
            return;
        }

        resolveFromType(clazz.getGenericSuperclass(), map);

        for (Type t : clazz.getGenericInterfaces()) {
            resolveFromType(t, map);
        }

        resolveHierarchy(clazz.getSuperclass(), map);
    }

    private void resolveFromType(Type type, Map<String, String> map) {

        if (!(type instanceof ParameterizedType pt)) {
            return;
        }

        if (!(pt.getRawType() instanceof Class<?> rawClass)) {
            return;
        }

        TypeVariable<?>[] vars = rawClass.getTypeParameters();
        Type[] args = pt.getActualTypeArguments();

        for (int i = 0; i < vars.length; i++) {
            map.put(vars[i].getName(), resolveType(args[i], map));
        }

        resolveHierarchy(rawClass, map);
    }

    private void resolveMethodTypeVariables(
            Method method,
            Map<String, String> generics) {

        for (TypeVariable<Method> var : method.getTypeParameters()) {

            Type[] bounds = var.getBounds();

            if (bounds.length == 0) continue;

            generics.put(
                    var.getName(),
                    resolveType(bounds[0], generics)
            );
        }
    }

    // =====================================================
    // TYPE RENDERING
    // =====================================================

    private String typeName(Type type, Map<String, String> generics) {
        return simplify(resolveType(type, generics));
    }

    private String resolveType(Type type, Map<String, String> generics) {

        if (type instanceof Class<?> cls) {
            return cls.getSimpleName();
        }

        if (type instanceof TypeVariable<?> tv) {
            return generics.getOrDefault(tv.getName(), tv.getName());
        }

        if (type instanceof ParameterizedType pt) {

            String raw = resolveType(pt.getRawType(), generics);

            String args = Arrays.stream(pt.getActualTypeArguments())
                    .map(t -> resolveType(t, generics))
                    .collect(Collectors.joining(", "));

            return raw + "<" + args + ">";
        }

        if (type instanceof WildcardType wt) {

            Type[] upper = wt.getUpperBounds();
            Type[] lower = wt.getLowerBounds();

            if (lower.length > 0) {
                return "? super " + resolveType(lower[0], generics);
            }

            if (upper.length > 0 && upper[0] != Object.class) {
                return "? extends " + resolveType(upper[0], generics);
            }

            return "?";
        }

        if (type instanceof GenericArrayType ga) {
            return resolveType(
                    ga.getGenericComponentType(),
                    generics
            ) + "[]";
        }

        return type.getTypeName();
    }

    private String simplify(String name) {

        name = name.replace("java.lang.", "");
        name = name.replace("java.util.", "");
        name = name.replace("java.io.", "");
        name = name.replace("com.baomidou.mybatisplus.core.conditions.", "");
        name = name.replace("com.baomidou.mybatisplus.core.metadata.", "");

        return name;
    }

    // =====================================================
    // IMPORTS
    // =====================================================

    private Set<String> collectImports(Class<?> target) {

        Set<String> imports = new TreeSet<>();

        for (Method method : target.getMethods()) {

            if (method.isBridge()) continue;
            if (method.isSynthetic()) continue;

            addType(imports, method.getGenericReturnType());

            for (Type t : method.getGenericParameterTypes()) {
                addType(imports, t);
            }

            for (Class<?> ex : method.getExceptionTypes()) {
                addType(imports, ex);
            }
        }

        imports.removeIf(s ->
                s.startsWith("java.lang.") ||
                        (target.getPackage() != null &&
                                s.startsWith(target.getPackageName() + "."))
        );

        return imports;
    }

    private void addType(Set<String> imports, Type type) {

        if (type instanceof Class<?> cls) {

            if (!cls.isPrimitive() && cls.getPackage() != null) {
                imports.add(cls.getName());
            }

        } else if (type instanceof ParameterizedType pt) {

            addType(imports, pt.getRawType());

            for (Type arg : pt.getActualTypeArguments()) {
                addType(imports, arg);
            }

        } else if (type instanceof GenericArrayType gat) {

            addType(imports, gat.getGenericComponentType());
        }
    }

    private void addTargetImport(Set<String> imports, Class<?> target) {

        if (target.isPrimitive()) return;
        if (target.getPackage() == null) return;

        String fqcn = target.getName().replace('$', '.');

        if (fqcn.startsWith("java.lang.")) return;

        imports.add(fqcn);
    }

    // =====================================================
    // HELPERS
    // =====================================================

    private String signatureOf(
            Method method,
            Map<String, String> generics) {

        return method.getName() + "(" +
                Arrays.stream(method.getGenericParameterTypes())
                        .map(t -> typeName(t, generics))
                        .collect(Collectors.joining(",")) +
                ")";
    }

    private String parameterName(Parameter p, int index) {
        return p.isNamePresent() ? p.getName() : "arg" + index;
    }

    private int indexOf(Parameter[] arr, Parameter p) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == p) return i;
        }
        return -1;
    }
}