package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class McpConfigurationIntegrationTest {

    @Mock
    ControllerMethodsCache methodsCache;

    private ControllerMethodsCache mockedDependency_proxy = new ControllerMethodsCache_Proxy(methodsCache);

    @Test
    void testInit() {
        McpConfiguration mcpConfiguration = new McpConfiguration(mockedDependency_proxy);
        mcpConfiguration.init();
        assertEquals(1, ((ControllerMethodsCache_Proxy) mockedDependency_proxy).initClassMethod_verify());
    }
}