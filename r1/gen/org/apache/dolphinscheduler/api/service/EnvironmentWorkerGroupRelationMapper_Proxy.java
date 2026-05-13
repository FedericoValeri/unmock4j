package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.dao.entity.EnvironmentWorkerGroupRelation;
import org.apache.dolphinscheduler.dao.mapper.EnvironmentWorkerGroupRelationMapper;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class EnvironmentWorkerGroupRelationMapper_Proxy extends EnvironmentWorkerGroupRelationMapper_EmptyProxy {

    public EnvironmentWorkerGroupRelationMapper_Proxy(EnvironmentWorkerGroupRelationMapper dependency) {
        super(dependency);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByEnvironmentCode(Long environmentCode) {
        List<EnvironmentWorkerGroupRelation> result = dependency.queryByEnvironmentCode(environmentCode);
        if (environmentCode != null && environmentCode.equals(1L)) {
            Assertions.assertNotNull(result);
        }
        return result;
    }
}