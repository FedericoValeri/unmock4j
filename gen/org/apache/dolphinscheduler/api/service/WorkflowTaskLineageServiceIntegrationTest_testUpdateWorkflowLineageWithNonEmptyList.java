package org.apache.dolphinscheduler.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.dolphinscheduler.api.service.impl.WorkflowLineageServiceImpl;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkFlowLineage;
import org.apache.dolphinscheduler.dao.entity.WorkFlowRelation;
import org.apache.dolphinscheduler.dao.entity.WorkFlowRelationDetail;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionLogMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkflowTaskLineageServiceIntegrationTest_testUpdateWorkflowLineageWithNonEmptyList {

    private WorkflowLineageServiceImpl workflowLineageService;

    private WorkflowTaskLineageDao workflowTaskLineageDao;
    private WorkflowTaskLineageDao workflowTaskLineageDao_proxy;

    private ProjectMapper projectMapper;
    private ProjectMapper projectMapper_proxy;

    private TaskDefinitionLogMapper taskDefinitionLogMapper;
    private TaskDefinitionLogMapper taskDefinitionLogMapper_proxy;

    private TaskDefinitionMapper taskDefinitionMapper;
    private TaskDefinitionMapper taskDefinitionMapper_proxy;

    public WorkflowTaskLineageServiceIntegrationTest_testUpdateWorkflowLineageWithNonEmptyList(WorkflowTaskLineageDao workflowTaskLineageDao, ProjectMapper projectMapper, TaskDefinitionLogMapper taskDefinitionLogMapper, TaskDefinitionMapper taskDefinitionMapper) {
        this.workflowTaskLineageDao = workflowTaskLineageDao;
        this.workflowTaskLineageDao_proxy = new WorkflowTaskLineageDao_Proxy(workflowTaskLineageDao);
        this.projectMapper = projectMapper;
        this.projectMapper_proxy = new ProjectMapper_Proxy(projectMapper);
        this.taskDefinitionLogMapper = taskDefinitionLogMapper;
        this.taskDefinitionLogMapper_proxy = new TaskDefinitionLogMapper_Proxy(taskDefinitionLogMapper);
        this.taskDefinitionMapper = taskDefinitionMapper;
        this.taskDefinitionMapper_proxy = new TaskDefinitionMapper_Proxy(taskDefinitionMapper);
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

        workflowTaskLineageDao_proxy.batchDeleteByWorkflowDefinitionCode(List.of(workflowDefinitionCode));
        workflowTaskLineageDao_proxy.batchInsert(workflowTaskLineages);

        int result = workflowLineageService.updateWorkflowLineage(workflowDefinitionCode, workflowTaskLineages);

        Assertions.assertEquals(2, result);
        assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchDeleteByWorkflowDefinitionCode_verify());
        assertEquals(1, ((WorkflowTaskLineageDao_Proxy) workflowTaskLineageDao_proxy).batchInsert_verify());
    }
}