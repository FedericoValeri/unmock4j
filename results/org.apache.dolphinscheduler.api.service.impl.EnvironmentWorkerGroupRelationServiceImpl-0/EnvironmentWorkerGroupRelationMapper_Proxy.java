package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.service.impl.EnvironmentWorkerGroupRelationServiceImpl;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.EnvironmentWorkerGroupRelation;
import org.apache.dolphinscheduler.dao.mapper.EnvironmentWorkerGroupRelationMapper;

import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



class EnvironmentWorkerGroupRelationMapper_Proxy extends EnvironmentWorkerGroupRelationMapper_EmptyProxy {
    private int methodCounter = 0;

    public EnvironmentWorkerGroupRelationMapper_Proxy(EnvironmentWorkerGroupRelationMapper dependency) {
        super(dependency);
    }

    @Override
    public java.util.List<EnvironmentWorkerGroupRelation> queryByEnvironmentCode(Long environmentCode) {
        methodCounter++;
        java.util.List<EnvironmentWorkerGroupRelation> result = dependency.queryByEnvironmentCode(environmentCode);
        Assertions.assertNotNull(result);
        return result;
    }

    public int queryByEnvironmentCode_verify() {
        return methodCounter;
    }
}