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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * environment service test
 */
public class EnvironmentWorkerGroupRelationServiceIntegrationTest_testQueryEnvironmentWorkerGroupRelation {

    public static final Logger logger = LoggerFactory.getLogger(EnvironmentWorkerGroupRelationServiceIntegrationTest_testQueryEnvironmentWorkerGroupRelation.class);

    private EnvironmentWorkerGroupRelationMapper relationMapper_proxy;

    private final EnvironmentWorkerGroupRelationServiceImpl relationService;

    public EnvironmentWorkerGroupRelationServiceIntegrationTest_testQueryEnvironmentWorkerGroupRelation(EnvironmentWorkerGroupRelationMapper relationMapper) {
        this.relationMapper_proxy = new EnvironmentWorkerGroupRelationMapper_Proxy(relationMapper);
        this.relationService = new EnvironmentWorkerGroupRelationServiceImpl();
    }

    @Test
    public void testQueryEnvironmentWorkerGroupRelation() {
        relationMapper_proxy.queryByEnvironmentCode(1L);
        Map<String, Object> result = relationService.queryEnvironmentWorkerGroupRelation(1L);
        logger.info(result.toString());
        Assertions.assertEquals(Status.SUCCESS, result.get(Constants.STATUS));
    }
}