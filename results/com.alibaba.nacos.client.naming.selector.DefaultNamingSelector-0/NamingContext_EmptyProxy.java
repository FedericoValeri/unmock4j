package com.alibaba.nacos.client.naming.selector;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.selector.NamingContext;
import java.util.List;

public class NamingContext_EmptyProxy implements NamingContext {

    protected final NamingContext namingContext;

    public NamingContext_EmptyProxy(NamingContext namingContext) {
        this.namingContext = namingContext;
    }

    @Override
    public String getServiceName() {
        return namingContext.getServiceName();
    }

    @Override
    public String getGroupName() {
        return namingContext.getGroupName();
    }

    @Override
    public String getClusters() {
        return namingContext.getClusters();
    }

    @Override
    public List<Instance> getInstances() {
        return namingContext.getInstances();
    }

}
