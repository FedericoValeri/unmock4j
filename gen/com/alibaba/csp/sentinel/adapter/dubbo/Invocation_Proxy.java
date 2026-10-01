package com.alibaba.csp.sentinel.adapter.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class Invocation_Proxy extends Invocation_EmptyProxy {

    private int getAttachmentCounter = 0;

    public Invocation_Proxy(Invocation dependency) {
        super(dependency);
    }

    @Override
    public java.util.Map<String, String> getAttachments() {
        java.util.Map<String, String> result = dependency.getAttachments();
        assertNotNull(result);
        return result;
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        getAttachmentCounter++;
        String result = dependency.getAttachment(key, defaultValue);
        if (DubboUtils.DUBBO_APPLICATION_KEY.equals(key) && "".equals(defaultValue)) {
            assertEquals("consumerA", result);
        }
        return result;
    }

    public int getAttachment_verify() {
        return getAttachmentCounter;
    }
}