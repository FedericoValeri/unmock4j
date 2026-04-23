package com.alibaba.nacos.client.naming.remote.gprc;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.naming.cache.ServiceInfoHolder;

public class ServiceInfoHolder_EmptyProxy extends ServiceInfoHolder {

    protected final ServiceInfoHolder serviceInfoHolder;

    public ServiceInfoHolder_EmptyProxy(ServiceInfoHolder serviceInfoHolder) {
        this.serviceInfoHolder = serviceInfoHolder;
    }

    @Override
    public void shutdown() throws NacosException {
        serviceInfoHolder.shutdown();
    }

}
