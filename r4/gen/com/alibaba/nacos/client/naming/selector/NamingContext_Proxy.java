package com.alibaba.nacos.client.naming.selector;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.selector.NamingContext;
import com.alibaba.nacos.api.naming.selector.NamingResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



class NamingContext_Proxy extends NamingContext_EmptyProxy {
    NamingContext_Proxy(NamingContext dependency) {
        super(dependency);
    }

    @Override
    public List<Instance> getInstances() {
        List<Instance> result = dependency.getInstances();
        assertNotNull(result);
        return result;
    }
}