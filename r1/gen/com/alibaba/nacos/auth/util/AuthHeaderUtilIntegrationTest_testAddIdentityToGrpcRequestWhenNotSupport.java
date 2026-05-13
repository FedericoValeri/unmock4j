package com.alibaba.nacos.auth.util;

import com.alibaba.nacos.api.remote.request.Request;
import com.alibaba.nacos.auth.config.NacosAuthConfig;
import com.alibaba.nacos.common.http.param.Header;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class AuthHeaderUtilIntegrationTest_testAddIdentityToGrpcRequestWhenNotSupport {

    private NacosAuthConfig authConfig_proxy;
    
    private Request request_proxy;

    public AuthHeaderUtilIntegrationTest_testAddIdentityToGrpcRequestWhenNotSupport(NacosAuthConfig authConfig, Request request) {
        this.authConfig_proxy = new NacosAuthConfig_Proxy(authConfig);
        this.request_proxy = new Request_Proxy(request);
    }

    @Test
    void testAddIdentityToGrpcRequestWhenNotSupport() {
        authConfig_proxy.isSupportServerIdentity();

        AuthHeaderUtil.addIdentityToHeader(request_proxy, authConfig_proxy);

        assertEquals(0, ((Request_Proxy) request_proxy).putHeader_verify());
    }
}