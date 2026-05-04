package org.apache.dolphinscheduler.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

public class WorkflowTaskLineageDao_Proxy extends WorkflowTaskLineageDao_EmptyProxy {

    private int batchDeleteByWorkflowDefinitionCodeCounter = 0;
    private int batchInsertCounter = 0;

    public WorkflowTaskLineageDao_Proxy(WorkflowTaskLineageDao workflowTaskLineageDao) {
        super(workflowTaskLineageDao);
    }

    @Override
    public int batchDeleteByWorkflowDefinitionCode(java.util.List<java.lang.Long> workflowDefinitionCodes) {
        batchDeleteByWorkflowDefinitionCodeCounter++;
        int result = workflowTaskLineageDao.batchDeleteByWorkflowDefinitionCode(workflowDefinitionCodes);
        assertEquals(2, result);
        return result;
    }

    @Override
    public int batchInsert(java.util.List<org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage> workflowTaskLineages) {
        batchInsertCounter++;
        int result = workflowTaskLineageDao.batchInsert(workflowTaskLineages);
        assertEquals(2, result);
        return result;
    }

    public int batchDeleteByWorkflowDefinitionCode_verify() {
        return batchDeleteByWorkflowDefinitionCodeCounter;
    }

    public int batchInsert_verify() {
        return batchInsertCounter;
    }
}