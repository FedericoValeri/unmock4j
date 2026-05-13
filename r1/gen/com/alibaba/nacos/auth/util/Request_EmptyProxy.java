package com.alibaba.nacos.auth.util;

import com.alibaba.nacos.api.remote.request.Request;

public class Request_EmptyProxy extends Request {

    protected final Request dependency;

    public Request_EmptyProxy(Request dependency) {
        this.dependency = dependency;
    }

    @Override
    public String getModule() {
        return dependency.getModule();
    }

}
