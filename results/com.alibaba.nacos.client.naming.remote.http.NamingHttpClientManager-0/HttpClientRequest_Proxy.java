package com.alibaba.nacos.client.naming.remote.http;

import com.alibaba.nacos.common.http.client.request.HttpClientRequest;
import com.alibaba.nacos.common.http.client.response.HttpClientResponse;
import com.alibaba.nacos.common.model.RequestHttpEntity;

import java.io.Closeable;
import java.net.URI;



class HttpClientRequest_Proxy extends HttpClientRequest_EmptyProxy {
    private int closeCounter = 0;

    HttpClientRequest_Proxy(HttpClientRequest dependency) {
        super(dependency);
    }

    @Override
    public void close() {
        closeCounter++;
        dependency.close();
    }

    public int close_verify() {
        return closeCounter;
    }
}