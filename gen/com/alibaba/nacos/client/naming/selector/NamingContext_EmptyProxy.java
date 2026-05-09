package com.alibaba.nacos.client.naming.selector;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.selector.NamingContext;
import java.util.List;

public class NamingContext_EmptyProxy implements NamingContext {

    protected final NamingContext dependency;

    public NamingContext_EmptyProxy(NamingContext dependency) {
        this.dependency = dependency;
    }

    @Override
    public String getClusters() {
        return dependency.getClusters();
    }

    @Override
    public List<Instance> getInstances() {
        return dependency.getInstances();
    }

    @Override
    public String getServiceName() {
        return dependency.getServiceName();
    }

    @Override
    public String getGroupName() {
        return dependency.getGroupName();
    }

}
