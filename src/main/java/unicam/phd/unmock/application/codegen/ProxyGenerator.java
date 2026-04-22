package unicam.phd.unmock.application.codegen;

import org.jspecify.annotations.NonNull;

import java.beans.Introspector;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Proxy generator:
 * - implements target interface / extends target class
 * - includes declared methods
 * - includes inherited abstract methods
 * - skips static/private/final/default methods
 * - resolves inherited generics
 * - delegates every call to wrapped instance
 */
public class ProxyGenerator {

    private record MethodContext(Method method, Map<String, Type> generics) {
    }

    public String buildSource(Class<?> target, String packageName) {

        String simpleName = target.getSimpleName();
        String proxyName = simpleName + "_EmptyProxy";
        String fieldName = Introspector.decapitalize(simpleName);

        List<MethodContext> methods = collectMethodsToImplement(target);

        Set<String> imports = collectImports(target, methods);
        addImport(imports, target);

        StringBuilder sb = new StringBuilder();

        // package
        if (packageName != null && !packageName.isBlank()) {
            sb.append("package ").append(packageName).append(";\n\n");
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
        for (MethodContext ctx : methods) {
            sb.append(buildMethod(
                    ctx.method(),
                    fieldName,
                    ctx.generics()
            ));
        }

        sb.append("}\n");
        return sb.toString();
    }

    // =====================================================
    // METHOD COLLECTION
    // =====================================================

    private List<MethodContext> collectMethodsToImplement(Class<?> target) {

        Map<String, MethodContext> selected = new LinkedHashMap<>();

        collectRecursive(
                target,
                new LinkedHashMap<>(),
                selected
        );

        return new ArrayList<>(selected.values());
    }

    private void collectRecursive(
            Class<?> type,
            Map<String, Type> generics,
            Map<String, MethodContext> selected) {

        if (type == null || type == Object.class) {
            return;
        }

        for (Method method : type.getDeclaredMethods()) {

            if (!mustImplement(method)) {
                continue;
            }

            String sig = signatureOf(method, generics);

            // child wins over parent
            selected.putIfAbsent(
                    sig,
                    new MethodContext(
                            method,
                            new LinkedHashMap<>(generics)
                    )
            );
        }

        for (Type parent : type.getGenericInterfaces()) {
            visitParent(parent, generics, selected);
        }

        visitParent(type.getGenericSuperclass(), generics, selected);
    }

    private void visitParent(
            Type parentType,
            Map<String, Type> current,
            Map<String, MethodContext> selected) {

        switch (parentType) {
            case ParameterizedType pt -> {

                Class<?> raw = (Class<?>) pt.getRawType();

                Map<String, Type> next = new LinkedHashMap<>(current);

                TypeVariable<?>[] vars = raw.getTypeParameters();
                Type[] args = pt.getActualTypeArguments();

                for (int i = 0; i < vars.length; i++) {
                    next.put(
                            vars[i].getName(),
                            substitute(args[i], current)
                    );
                }

                collectRecursive(raw, next, selected);
            }
            case Class<?> raw -> collectRecursive(
                    raw,
                    new LinkedHashMap<>(current),
                    selected
            );
            default -> {
            }
        }

    }

    private boolean mustImplement(Method method) {

        int mod = method.getModifiers();

        if (method.isSynthetic()) return false;
        if (method.isBridge()) return false;
        if (Modifier.isStatic(mod)) return false;
        if (Modifier.isPrivate(mod)) return false;
        if (Modifier.isFinal(mod)) return false;
        if (method.isDefault()) return false;

        return Modifier.isAbstract(mod)
                || method.getDeclaringClass().isInterface();
    }

    // =====================================================
    // METHOD GENERATION
    // =====================================================

    private String buildMethod(
            Method method,
            String fieldName,
            Map<String, Type> generics) {

        StringBuilder sb = new StringBuilder();

        sb.append("    @Override\n");
        sb.append("    public ");

        sb.append(renderMethodTypeParameters(method, generics));

        sb.append(typeNameResolved(
                        method.getGenericReturnType(),
                        generics
                ))
                .append(" ")
                .append(method.getName())
                .append("(");

        Parameter[] params = method.getParameters();

        for (int i = 0; i < params.length; i++) {
            if (i > 0) sb.append(", ");

            sb.append(typeNameResolved(
                            params[i].getParameterizedType(),
                            generics
                    ))
                    .append(" ")
                    .append(paramName(params[i], i));
        }

        sb.append(")");

        Class<?>[] ex = method.getExceptionTypes();

        if (ex.length > 0) {
            sb.append(" throws ");

            for (int i = 0; i < ex.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(ex[i].getSimpleName());
            }
        }

        sb.append(" {\n");

        String args = buildArgs(params);

        if (method.getReturnType() == void.class) {
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
    // GENERICS
    // =====================================================

    private String renderMethodTypeParameters(
            Method method,
            Map<String, Type> generics) {

        TypeVariable<Method>[] vars = method.getTypeParameters();

        if (vars.length == 0) {
            return "";
        }

        String joined = Arrays.stream(vars)
                .map(v -> renderTypeVariable(v, generics))
                .collect(Collectors.joining(", "));

        return "<" + joined + "> ";
    }

    private String renderTypeVariable(
            TypeVariable<?> var,
            Map<String, Type> generics) {

        StringBuilder sb = new StringBuilder();
        sb.append(var.getName());

        List<String> bounds = Arrays.stream(var.getBounds())
                .filter(t -> t != Object.class)
                .map(t -> typeNameResolved(t, generics))
                .collect(Collectors.toList());

        if (!bounds.isEmpty()) {
            sb.append(" extends ");
            sb.append(String.join(" & ", bounds));
        }

        return sb.toString();
    }

    private String typeNameResolved(
            Type type,
            Map<String, Type> generics) {

        return typeName(substitute(type, generics));
    }

    private Type substitute(
            Type type,
            Map<String, Type> generics) {

        if (type instanceof TypeVariable<?> tv) {
            return generics.getOrDefault(
                    tv.getName(),
                    tv
            );
        }

        if (type instanceof ParameterizedType pt) {

            Type[] args = Arrays.stream(
                            pt.getActualTypeArguments()
                    )
                    .map(t -> substitute(t, generics))
                    .toArray(Type[]::new);

            return new SimpleParameterizedType(
                    pt.getRawType(),
                    args,
                    pt.getOwnerType()
            );
        }

        if (type instanceof GenericArrayType ga) {

            Type component = substitute(
                    ga.getGenericComponentType(),
                    generics
            );

            return new SimpleGenericArrayType(component);
        }

        return type;
    }

    private static class SimpleParameterizedType
            implements ParameterizedType {

        private final Type raw;
        private final Type[] args;
        private final Type owner;

        SimpleParameterizedType(
                Type raw,
                Type[] args,
                Type owner) {

            this.raw = raw;
            this.args = args;
            this.owner = owner;
        }

        @Override
        public Type @NonNull [] getActualTypeArguments() {
            return args;
        }

        @Override
        public @NonNull Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return owner;
        }
    }

    private record SimpleGenericArrayType(Type component)
            implements GenericArrayType {

        @Override
        public @NonNull Type getGenericComponentType() {
            return component;
        }
    }

    // =====================================================
    // TYPE RENDERING
    // =====================================================

    private String typeName(Type type) {

        if (type instanceof Class<?> cls) {

            if (cls.isArray()) {
                return typeName(
                        cls.getComponentType()
                ) + "[]";
            }

            return cls.getSimpleName();
        }

        if (type instanceof ParameterizedType pt) {

            String raw = typeName(
                    pt.getRawType()
            );

            String args = Arrays.stream(
                            pt.getActualTypeArguments()
                    )
                    .map(this::typeName)
                    .collect(Collectors.joining(", "));

            return raw + "<" + args + ">";
        }

        if (type instanceof TypeVariable<?> tv) {
            return tv.getName();
        }

        if (type instanceof WildcardType wt) {

            if (wt.getLowerBounds().length > 0) {
                return "? super " + typeName(
                        wt.getLowerBounds()[0]
                );
            }

            if (wt.getUpperBounds().length > 0
                    && wt.getUpperBounds()[0] != Object.class) {

                return "? extends " + typeName(
                        wt.getUpperBounds()[0]
                );
            }

            return "?";
        }

        if (type instanceof GenericArrayType ga) {
            return typeName(
                    ga.getGenericComponentType()
            ) + "[]";
        }

        return type.getTypeName();
    }

    // =====================================================
    // IMPORTS
    // =====================================================

    private Set<String> collectImports(
            Class<?> target,
            List<MethodContext> methods) {

        Set<String> imports = new TreeSet<>();

        for (MethodContext ctx : methods) {

            Method method = ctx.method();
            Map<String, Type> generics = ctx.generics();

            addType(imports,
                    substitute(
                            method.getGenericReturnType(),
                            generics
                    ));

            for (Type t : method.getGenericParameterTypes()) {
                addType(imports,
                        substitute(t, generics));
            }

            for (TypeVariable<Method> tv :
                    method.getTypeParameters()) {

                for (Type b : tv.getBounds()) {
                    addType(imports,
                            substitute(b, generics));
                }
            }

            for (Class<?> ex :
                    method.getExceptionTypes()) {
                addType(imports, ex);
            }
        }

        imports.removeIf(s ->
                s.startsWith("java.lang.")
                        || (target.getPackage() != null
                        && s.startsWith(
                        target.getPackageName() + "."))
        );

        return imports;
    }

    private void addType(
            Set<String> imports,
            Type type) {

        if (type instanceof Class<?> cls) {

            if (!cls.isPrimitive()
                    && cls.getPackage() != null) {
                imports.add(cls.getName());
            }

            if (cls.isArray()) {
                addType(imports,
                        cls.getComponentType());
            }

        } else if (type instanceof ParameterizedType pt) {

            addType(imports, pt.getRawType());

            for (Type arg :
                    pt.getActualTypeArguments()) {
                addType(imports, arg);
            }

        } else if (type instanceof WildcardType wt) {

            for (Type t : wt.getUpperBounds()) {
                addType(imports, t);
            }

            for (Type t : wt.getLowerBounds()) {
                addType(imports, t);
            }

        } else if (type instanceof GenericArrayType ga) {

            addType(imports,
                    ga.getGenericComponentType());

        } else if (type instanceof TypeVariable<?> tv) {

            for (Type b : tv.getBounds()) {
                addType(imports, b);
            }
        }
    }

    private void addImport(
            Set<String> imports,
            Class<?> type) {

        if (type.getPackage() == null) return;
        if (type.getName().startsWith("java.lang.")) return;

        imports.add(
                type.getName().replace('$', '.')
        );
    }

    // =====================================================
    // HELPERS
    // =====================================================

    private String signatureOf(
            Method method,
            Map<String, Type> generics) {

        return method.getName() + "(" +
                Arrays.stream(
                                method.getGenericParameterTypes()
                        )
                        .map(t -> typeNameResolved(
                                t, generics))
                        .collect(Collectors.joining(",")) +
                ")";
    }

    private String paramName(
            Parameter p,
            int index) {

        return p.isNamePresent()
                ? p.getName()
                : "arg" + index;
    }

    private String buildArgs(
            Parameter[] params) {

        List<String> names = new ArrayList<>();

        for (int i = 0; i < params.length; i++) {
            names.add(
                    paramName(params[i], i)
            );
        }

        return String.join(", ", names);
    }
}