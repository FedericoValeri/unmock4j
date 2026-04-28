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

/**
 * environment service test
 */
@ExtendWith(MockitoExtension.class)
public class EnvironmentWorkerGroupRelationServiceIntegrationTest {

    public static final Logger logger = LoggerFactory.getLogger(EnvironmentWorkerGroupRelationServiceIntegrationTest.class);

    @InjectMocks
    private EnvironmentWorkerGroupRelationServiceImpl relationService;

    @Mock
    private EnvironmentWorkerGroupRelationMapper relationMapper;
    private EnvironmentWorkerGroupRelationMapper relationMapper_proxy = new EnvironmentWorkerGroupRelationMapper_Proxy(relationMapper);

    @Test
    public void testQueryEnvironmentWorkerGroupRelation() {
        Mockito.when(relationMapper.queryByEnvironmentCode(1L))
                .thenReturn(relationMapper_proxy.queryByEnvironmentCode(1L));
        Map<String, Object> result = relationService.queryEnvironmentWorkerGroupRelation(1L);
        logger.info(result.toString());
        Assertions.assertEquals(Status.SUCCESS, result.get(Constants.STATUS));
    }
}