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



class Request_Proxy extends Request_EmptyProxy {
    private int putHeaderCounter = 0;

    Request_Proxy(Request dependency) {
        super(dependency);
    }

    @Override
    public void putHeader(String key, String value) {
        putHeaderCounter++;
        dependency.putHeader(key, value);
    }

    public int putHeader_verify() {
        return putHeaderCounter;
    }
}