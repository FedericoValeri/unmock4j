package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.dao.entity.WorkFlowRelationDetail;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

import java.util.List;

public class WorkflowTaskLineageDao_Proxy implements WorkflowTaskLineageDao {

    private final WorkflowTaskLineageDao dependency;
    private int batchDeleteByWorkflowDefinitionCodeCounter = 0;
    private int batchInsertCounter = 0;

    public WorkflowTaskLineageDao_Proxy(WorkflowTaskLineageDao dependency) {
        this.dependency = dependency;
    }

    @Override
    public int batchDeleteByWorkflowDefinitionCode(List<Long> workflowDefinitionCodes) {
        batchDeleteByWorkflowDefinitionCodeCounter++;
        int result = dependency.batchDeleteByWorkflowDefinitionCode(workflowDefinitionCodes);
        return result;
    }

    @Override
    public int batchInsert(List<WorkflowTaskLineage> workflowTaskLineages) {
        batchInsertCounter++;
        int result = dependency.batchInsert(workflowTaskLineages);
        return result;
    }

    public int batchDeleteByWorkflowDefinitionCode_verify() {
        return batchDeleteByWorkflowDefinitionCodeCounter;
    }

    public int batchInsert_verify() {
        return batchInsertCounter;
    }

    @Override
    public List<WorkflowTaskLineage> queryByProjectCode(long projectCode) {
        return dependency.queryByProjectCode(projectCode);
    }

    @Override
    public List<WorkFlowRelationDetail> queryWorkFlowLineageByCode(long workflowDefinitionCode) {
        return dependency.queryWorkFlowLineageByCode(workflowDefinitionCode);
    }

    @Override
    public List<WorkFlowRelationDetail> queryWorkFlowLineageByName(long projectCode, String workflowDefinitionName) {
        return dependency.queryWorkFlowLineageByName(projectCode, workflowDefinitionName);
    }

    @Override
    public List<WorkflowTaskLineage> queryWorkFlowLineageByDept(long deptProjectCode, long deptWorkflowDefinitionCode, long deptTaskDefinitionCode) {
        return dependency.queryWorkFlowLineageByDept(deptProjectCode, deptWorkflowDefinitionCode, deptTaskDefinitionCode);
    }

    @Override
    public List<WorkflowTaskLineage> queryByWorkflowDefinitionCode(long workflowDefinitionCode) {
        return dependency.queryByWorkflowDefinitionCode(workflowDefinitionCode);
    }

    @Override
    public int updateWorkflowTaskLineage(List<WorkflowTaskLineage> workflowTaskLineages) {
        return dependency.updateWorkflowTaskLineage(workflowTaskLineages);
    }
}