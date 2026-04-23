package com.alibaba.nacos.client.naming.remote;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.security.SecurityProxy;

public class SecurityProxy_EmptyProxy extends SecurityProxy {

    protected final SecurityProxy securityProxy;

    public SecurityProxy_EmptyProxy(SecurityProxy securityProxy) {
        this.securityProxy = securityProxy;
    }

    @Override
    public void shutdown() throws NacosException {
        securityProxy.shutdown();
    }

}
