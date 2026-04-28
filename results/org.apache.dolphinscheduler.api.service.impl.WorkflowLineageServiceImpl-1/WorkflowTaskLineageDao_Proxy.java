package org.apache.dolphinscheduler.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.dolphinscheduler.dao.entity.WorkFlowRelationDetail;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;
import java.util.List;

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
        assertEquals(2, result);
        return result;
    }

    @Override
    public int batchInsert(List<WorkflowTaskLineage> workflowTaskLineages) {
        batchInsertCounter++;
        int result = dependency.batchInsert(workflowTaskLineages);
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