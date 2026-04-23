package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;



class ControllerMethodsCache_Proxy extends ControllerMethodsCache_EmptyProxy {

    private int methodCounter = 0;

    public ControllerMethodsCache_Proxy(ControllerMethodsCache dependency) {
        super(dependency);
    }

    @Override
    public void initClassMethod(String packageName) {
        methodCounter++;
        dependency.initClassMethod(packageName);
    }

    public int method_verify() {
        return methodCounter;
    }
}