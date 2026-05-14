package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class McpConfigurationIntegrationTest_testInit {

    private ControllerMethodsCache methodsCache_proxy;

    public McpConfigurationIntegrationTest_testInit(ControllerMethodsCache methodsCache) {
        this.methodsCache_proxy = new ControllerMethodsCache_Proxy(methodsCache);
    }

    @Test
    void testInit() {
        McpConfiguration mcpConfiguration = new McpConfiguration(methodsCache_proxy);
        mcpConfiguration.init();
        assertEquals(1, ((ControllerMethodsCache_Proxy) methodsCache_proxy).initClassMethod_verify());
    }
}