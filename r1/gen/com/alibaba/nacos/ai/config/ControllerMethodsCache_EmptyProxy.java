package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;

public class ControllerMethodsCache_EmptyProxy extends ControllerMethodsCache {

    protected final ControllerMethodsCache dependency;

    public ControllerMethodsCache_EmptyProxy(ControllerMethodsCache dependency) {
        this.dependency = dependency;
    }

}
