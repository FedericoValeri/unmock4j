package io.seata.spring.annotation.datasource;

import java.util.Set;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

public class AnnotationMetadata_EmptyProxy implements AnnotationMetadata {

    protected final AnnotationMetadata dependency;

    public AnnotationMetadata_EmptyProxy(AnnotationMetadata dependency) {
        this.dependency = dependency;
    }

    @Override
    public Set<MethodMetadata> getDeclaredMethods() {
        return dependency.getDeclaredMethods();
    }

    @Override
    public Set<MethodMetadata> getAnnotatedMethods(String annotationName) {
        return dependency.getAnnotatedMethods(annotationName);
    }

    @Override
    public boolean isInterface() {
        return dependency.isInterface();
    }

    @Override
    public boolean isAnnotation() {
        return dependency.isAnnotation();
    }

    @Override
    public boolean isFinal() {
        return dependency.isFinal();
    }

    @Override
    public boolean isAbstract() {
        return dependency.isAbstract();
    }

    @Override
    public String getClassName() {
        return dependency.getClassName();
    }

    @Override
    public boolean isIndependent() {
        return dependency.isIndependent();
    }

    @Override
    public String[] getInterfaceNames() {
        return dependency.getInterfaceNames();
    }

    @Override
    public String getSuperClassName() {
        return dependency.getSuperClassName();
    }

    @Override
    public String getEnclosingClassName() {
        return dependency.getEnclosingClassName();
    }

    @Override
    public String[] getMemberClassNames() {
        return dependency.getMemberClassNames();
    }

    @Override
    public MergedAnnotations getAnnotations() {
        return dependency.getAnnotations();
    }

}
