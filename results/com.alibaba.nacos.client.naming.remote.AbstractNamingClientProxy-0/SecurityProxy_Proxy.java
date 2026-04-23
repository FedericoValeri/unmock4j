package com.alibaba.nacos.client.security;

import com.alibaba.nacos.plugin.auth.api.RequestResource;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class SecurityProxy_Proxy extends SecurityProxy_EmptyProxy {

    private int getIdentityContextCounter = 0;

    public SecurityProxy_Proxy(SecurityProxy dependency) {
        super(dependency);
    }

    @Override
    public Map<String, String> getIdentityContext(RequestResource resource) {
        getIdentityContextCounter++;
        Map<String, String> result = dependency.getIdentityContext(resource);
        Assertions.assertNotNull(result);
        return result;
    }

    public int getIdentityContext_verify() {
        return getIdentityContextCounter;
    }
}