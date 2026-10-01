package com.alibaba.csp.sentinel.adapter.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class DubboUtilsIntegrationTest_testGetApplication {

    @Test
    public void testGetApplication() {
        Invocation invocation_proxy = new Invocation_Proxy(<--Invocation_REAL_CONFIGURED_INSTANCE-->);
        invocation_proxy.getAttachments();
        invocation_proxy.getAttachment(DubboUtils.DUBBO_APPLICATION_KEY, "");

        String application = DubboUtils.getApplication(invocation_proxy, "");
        assertEquals(1, ((Invocation_Proxy) invocation_proxy).getAttachment_verify());

        assertEquals("consumerA", application);
    }
}