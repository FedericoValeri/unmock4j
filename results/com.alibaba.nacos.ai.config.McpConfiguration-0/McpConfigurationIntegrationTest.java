package com.alibaba.nacos.ai.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class McpConfigurationIntegrationTest {

    @Mock
    ControllerMethodsCache methodsCache;

    private ControllerMethodsCache methodsCache_proxy = new ControllerMethodsCache_Proxy(methodsCache);

    @Test
    void testInit() {
        McpConfiguration mcpConfiguration = new McpConfiguration(methodsCache_proxy);
        mcpConfiguration.init();
        assertThat(methodsCache_proxy.method_verify()).isEqualTo(1);
        verify(methodsCache).initClassMethod("com.alibaba.nacos.ai.controller");
    }
}