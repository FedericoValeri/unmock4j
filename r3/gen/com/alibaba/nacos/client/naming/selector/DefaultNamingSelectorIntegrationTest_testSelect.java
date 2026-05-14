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
import static org.mockito.Mockito.when;

public class DefaultNamingSelectorIntegrationTest_testSelect {

    @Test
    public void testSelect() {
        DefaultNamingSelector namingSelector = new DefaultNamingSelector(Instance::isHealthy);
        Random random = new Random();
        int total = random.nextInt(32) + 1;
        int health = random.nextInt(total);

        NamingContext namingContext = getMockNamingContext(total, health);
        NamingResult result = namingSelector.select(namingContext);

        assertEquals(health, result.getResult().size());
        result.getResult().forEach(ins -> assertTrue(ins.isHealthy()));
    }

    private NamingContext getMockNamingContext(int total, int health) {
        NamingContext namingContext_proxy = new NamingContext_Proxy(<--NamingContext_REAL_CONFIGURED_INSTANCE-->);
        namingContext_proxy.getInstances();
        return namingContext_proxy;
    }

    private List<Instance> getInstance(int total, int health) {
        List<Instance> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            Instance instance = new Instance();
            instance.setHealthy(false);
            list.add(instance);
        }

        for (int i = 0; i < health; i++) {
            list.get(i).setHealthy(true);
        }

        return list;
    }
}