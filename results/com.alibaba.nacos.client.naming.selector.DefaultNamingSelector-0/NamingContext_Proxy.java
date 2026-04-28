package com.alibaba.nacos.client.naming.selector;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.selector.NamingContext;
import java.util.List;

public class NamingContext_Proxy extends NamingContext_EmptyProxy {

    public NamingContext_Proxy(NamingContext namingContext) {
        super(namingContext);
    }

    @Override
    public List<Instance> getInstances() {
        List<Instance> result = dependency.getInstances();
        return result;
    }
}