package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.dao.entity.EnvironmentWorkerGroupRelation;
import org.apache.dolphinscheduler.dao.mapper.EnvironmentWorkerGroupRelationMapper;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.api.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentWorkerGroupRelationMapper_Proxy extends EnvironmentWorkerGroupRelationMapper_EmptyProxy {

    public EnvironmentWorkerGroupRelationMapper_Proxy(EnvironmentWorkerGroupRelationMapper relationMapper) {
        super(relationMapper);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByEnvironmentCode(Long environmentCode) {
        List<EnvironmentWorkerGroupRelation> result = dependency.queryByEnvironmentCode(environmentCode);
        Assertions.assertNotNull(result);
        return result;
    }
}