package org.apache.dolphinscheduler.api.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.dolphinscheduler.api.service.impl.WorkflowLineageServiceImpl;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkflowTaskLineageServiceIntegrationTest_testUpdateWorkflowLineageWithNonEmptyList {

    private WorkflowLineageServiceImpl workflowLineageService;

    private WorkflowTaskLineageDao workflowTaskLineageDao_proxy;

    public WorkflowTaskLineageServiceIntegrationTest_testUpdateWorkflowLineageWithNonEmptyList(WorkflowTaskLineageDao workflowTaskLineageDao) {
        this.workflowTaskLineageDao_proxy = new WorkflowTaskLineageDao_Proxy(workflowTaskLineageDao);
        this.workflowLineageService = new WorkflowLineageServiceImpl();
    }

    @Test
    public void testUpdateWorkflowLineageWithNonEmptyList() {
        long workflowDefinitionCode = 100L;
        List<WorkflowTaskLineage> workflowTaskLineages = new ArrayList<>();

        WorkflowTaskLineage lineage1 = new WorkflowTaskLineage();
        lineage1.setWorkflowDefinitionCode(workflowDefinitionCode);
        lineage1.setTaskDefinitionCode(200L);
        workflowTaskLineages.add(lineage1);

        WorkflowTaskLineage lineage2 = new WorkflowTaskLineage();
        lineage2.setWorkflowDefinitionCode(workflowDefinitionCode);
        lineage2.setTaskDefinitionCode(300L);
        workflowTaskLineages.add(lineage2);

        workflowTaskLineageDao_proxy.batchDeleteByWorkflowDefinitionCode(123);
        workflowTaskLineageDao_proxy.batchInsert(workflowTaskLineages);

        int result = workflowLineageService.updateWorkflowLineage(workflowDefinitionCode, workflowTaskLineages);

        Assertions.assertEquals(2, result);
        Assertions.assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchDeleteByWorkflowDefinitionCode_verify());
        Assertions.assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchInsert_verify());
    }
}