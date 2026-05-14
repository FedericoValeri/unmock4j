package com.alibaba.nacos.client.naming.remote.http;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.client.request.HttpClientRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;



class NamingHttpClientManagerIntegrationTest_testShutdown {

@Test
 void testShutdown() throws NoSuchFieldException, IllegalAccessException, NacosException, IOException {
        NamingHttpClientManager instance = NamingHttpClientManager.getInstance();

        HttpClientRequest mockHttpClientRequest = Mockito.mock(HttpClientRequest.class);
        HttpClientRequest mockHttpClientRequest_proxy = new HttpClientRequest_Proxy(<--HttpClientRequest_REAL_CONFIGURED_INSTANCE-->);
        Field requestClient = NacosRestTemplate.class.getDeclaredField("requestClient");
        requestClient.setAccessible(true);
        requestClient.set(instance.getNacosRestTemplate(), mockHttpClientRequest_proxy);
        NamingHttpClientManager.getInstance().shutdown();
        assertEquals(1, ((HttpClientRequest_Proxy) mockHttpClientRequest_proxy).close_verify());
    }
    }