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



class NacosAuthConfig_Proxy extends NacosAuthConfig_EmptyProxy {
    public NacosAuthConfig_Proxy(NacosAuthConfig dependency) {
        super(dependency);
    }

    @Override
    public boolean isSupportServerIdentity() {
        boolean result = dependency.isSupportServerIdentity();
        assertFalse(result);
        return result;
    }
}