package org.apache.dolphinscheduler.api.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.apache.dolphinscheduler.api.service.impl.WorkflowLineageServiceImpl;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WorkflowTaskLineageServiceIntegrationTest {

    @InjectMocks
    private WorkflowLineageServiceImpl workflowLineageService;

    @Mock
    private WorkflowTaskLineageDao workflowTaskLineageDao;
    private WorkflowTaskLineageDao workflowTaskLineageDao_proxy = new WorkflowTaskLineageDao_Proxy(workflowTaskLineageDao);

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

        when(workflowTaskLineageDao.batchDeleteByWorkflowDefinitionCode(anyList())).thenReturn(workflowTaskLineageDao_proxy.batchDeleteByWorkflowDefinitionCode(anyList()));
        when(workflowTaskLineageDao.batchInsert(workflowTaskLineages)).thenReturn(workflowTaskLineageDao_proxy.batchInsert(workflowTaskLineages));

        int result = workflowLineageService.updateWorkflowLineage(workflowDefinitionCode, workflowTaskLineages);

        Assertions.assertEquals(2, result);
        Assertions.assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchDeleteByWorkflowDefinitionCode_verify());
        Assertions.assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchInsert_verify());
    }
}