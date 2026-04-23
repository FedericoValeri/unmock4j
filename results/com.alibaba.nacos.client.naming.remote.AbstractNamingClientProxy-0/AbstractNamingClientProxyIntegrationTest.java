package com.alibaba.nacos.client.naming.remote;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.api.naming.pojo.Service;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.alibaba.nacos.api.selector.AbstractSelector;
import com.alibaba.nacos.client.address.ServerListChangeEvent;
import com.alibaba.nacos.client.auth.ram.utils.SignUtil;
import com.alibaba.nacos.client.security.SecurityProxy;
import com.alibaba.nacos.client.utils.AppNameUtils;
import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.plugin.auth.api.RequestResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
class AbstractNamingClientProxyIntegrationTest {

    @Mock
    private SecurityProxy sc;

    private SecurityProxy sc_proxy = new SecurityProxy_Proxy(sc);

    /**
     * test get security headers for accessToken.
     */
    @Test
    void testGetSecurityHeadersForAccessToken() {
        AbstractNamingClientProxy proxy = new MockNamingClientProxy(sc);
        String token = "aa";
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put(Constants.ACCESS_TOKEN, token);
        when(sc.getIdentityContext(any(RequestResource.class))).thenReturn(sc_proxy.getIdentityContext(any(RequestResource.class)));
        Map<String, String> securityHeaders = proxy.getSecurityHeaders("", "", "");
        assertEquals(2, securityHeaders.size());
        assertEquals(token, securityHeaders.get(Constants.ACCESS_TOKEN));
        assertEquals(AppNameUtils.getAppName(), securityHeaders.get("app"));
    }
}