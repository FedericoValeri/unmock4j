package com.alibaba.nacos.auth.util;

import com.alibaba.nacos.auth.config.NacosAuthConfig;

public class NacosAuthConfig_EmptyProxy implements NacosAuthConfig {

    protected final NacosAuthConfig dependency;

    public NacosAuthConfig_EmptyProxy(NacosAuthConfig dependency) {
        this.dependency = dependency;
    }

    @Override
    public boolean isAuthEnabled() {
        return dependency.isAuthEnabled();
    }

    @Override
    public String getAuthScope() {
        return dependency.getAuthScope();
    }

    @Override
    public boolean isSupportServerIdentity() {
        return dependency.isSupportServerIdentity();
    }

    @Override
    public String getNacosAuthSystemType() {
        return dependency.getNacosAuthSystemType();
    }

    @Override
    public String getServerIdentityKey() {
        return dependency.getServerIdentityKey();
    }

    @Override
    public String getServerIdentityValue() {
        return dependency.getServerIdentityValue();
    }

}
