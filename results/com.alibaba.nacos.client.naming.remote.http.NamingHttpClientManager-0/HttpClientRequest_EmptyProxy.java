package com.alibaba.nacos.client.naming.remote.http;

import com.alibaba.nacos.common.http.client.request.HttpClientRequest;
import com.alibaba.nacos.common.http.client.response.HttpClientResponse;
import com.alibaba.nacos.common.model.RequestHttpEntity;
import java.io.IOException;
import java.net.URI;

public class HttpClientRequest_EmptyProxy implements HttpClientRequest {

    protected final HttpClientRequest httpClientRequest;

    public HttpClientRequest_EmptyProxy(HttpClientRequest httpClientRequest) {
        this.httpClientRequest = httpClientRequest;
    }

    @Override
    public HttpClientResponse execute(URI uri, String httpMethod, RequestHttpEntity requestHttpEntity) throws Exception {
        return httpClientRequest.execute(uri, httpMethod, requestHttpEntity);
    }

    @Override
    public void close() throws IOException {
        httpClientRequest.close();
    }

}
