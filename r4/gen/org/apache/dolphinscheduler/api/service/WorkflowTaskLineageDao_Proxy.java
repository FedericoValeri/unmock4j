package org.apache.dolphinscheduler.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

public class WorkflowTaskLineageDao_Proxy extends WorkflowTaskLineageDao_EmptyProxy {

    private int batchDeleteByWorkflowDefinitionCodeCounter = 0;
    private int batchInsertCounter = 0;

    public WorkflowTaskLineageDao_Proxy(WorkflowTaskLineageDao workflowTaskLineageDao) {
        super(workflowTaskLineageDao);
    }

    @Override
    public int batchDeleteByWorkflowDefinitionCode(List<Long> workflowDefinitionCodes) {
        batchDeleteByWorkflowDefinitionCodeCounter++;
        int result = dependency.batchDeleteByWorkflowDefinitionCode(workflowDefinitionCodes);
        if (workflowDefinitionCodes != null) {
            assertEquals(2, result);
        }
        return result;
    }

    @Override
    public int batchInsert(List<WorkflowTaskLineage> workflowTaskLineages) {
        batchInsertCounter++;
        int result = dependency.batchInsert(workflowTaskLineageList(workflowTaskLineages));
        if (workflowTaskLineages != null) {
            assertEquals(2, result);
        }
        return result;
    }

    public int batchDeleteByWorkflowDefinitionCode_verify() {
        return batchDeleteByWorkflowDefinitionCodeCounter;
    }

    public int batchInsert_verify() {
        return batchInsertCounter;
    }

    private List<WorkflowTaskLineage> workflowTaskLineageList(List<WorkflowTaskLineage> workflowTaskLineages) {
        return workflowTaskLineages;
    }
}