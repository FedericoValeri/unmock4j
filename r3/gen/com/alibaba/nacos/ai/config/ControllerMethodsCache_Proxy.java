package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;



class ControllerMethodsCache_Proxy extends ControllerMethodsCache_EmptyProxy {

    public ControllerMethodsCache_Proxy(ControllerMethodsCache dependency) {
        super(dependency);
    }

    private int initClassMethodCounter = 0;

    @Override
    public void initClassMethod(String packageName) {
        initClassMethodCounter++;
        dependency.initClassMethod(packageName);
    }

    public int initClassMethod_verify() {
        return initClassMethodCounter;
    }
}