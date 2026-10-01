package io.seata.spring.annotation.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnnotationMetadata_Proxy extends AnnotationMetadata_EmptyProxy {

    public AnnotationMetadata_Proxy(AnnotationMetadata dependency) {
        super(dependency);
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName) {
        Map<String, Object> result = dependency.getAnnotationAttributes(annotationName);
        if (EnableAutoDataSourceProxy.class.getName().equals(annotationName)) {
            assertNotNull(result);
        }
        return result;
    }
}