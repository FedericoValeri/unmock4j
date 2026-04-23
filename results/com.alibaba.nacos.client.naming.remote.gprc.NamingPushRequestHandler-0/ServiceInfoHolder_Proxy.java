package com.alibaba.nacos.client.naming.cache;

import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import org.junit.jupiter.api.Assertions;

public class ServiceInfoHolder_Proxy extends ServiceInfoHolder_EmptyProxy {

    private int processServiceInfoCounter = 0;

    public ServiceInfoHolder_Proxy(ServiceInfoHolder dependency) {
        super(dependency);
    }

    @Override
    public ServiceInfo processServiceInfo(ServiceInfo serviceInfo) {
        processServiceInfoCounter++;
        ServiceInfo result = dependency.processServiceInfo(serviceInfo);
        Assertions.assertNotNull(result);
        return result;
    }

    public int processServiceInfo_verify() {
        return processServiceInfoCounter;
    }
}