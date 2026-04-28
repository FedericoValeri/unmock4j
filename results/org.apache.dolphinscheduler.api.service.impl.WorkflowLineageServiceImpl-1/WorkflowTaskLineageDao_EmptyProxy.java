package org.apache.dolphinscheduler.api.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.dolphinscheduler.dao.entity.WorkFlowRelationDetail;
import org.apache.dolphinscheduler.dao.entity.WorkflowTaskLineage;
import org.apache.dolphinscheduler.dao.repository.WorkflowTaskLineageDao;

public class WorkflowTaskLineageDao_EmptyProxy implements WorkflowTaskLineageDao {

    protected final WorkflowTaskLineageDao workflowTaskLineageDao;

    public WorkflowTaskLineageDao_EmptyProxy(WorkflowTaskLineageDao workflowTaskLineageDao) {
        this.workflowTaskLineageDao = workflowTaskLineageDao;
    }

    @Override
    public List<WorkFlowRelationDetail> queryWorkFlowLineageByCode(long arg0) {
        return workflowTaskLineageDao.queryWorkFlowLineageByCode(arg0);
    }

    @Override
    public int batchInsert(List<WorkflowTaskLineage> arg0) {
        return workflowTaskLineageDao.batchInsert(arg0);
    }

    @Override
    public List<WorkFlowRelationDetail> queryWorkFlowLineageByName(long arg0, String arg1) {
        return workflowTaskLineageDao.queryWorkFlowLineageByName(arg0, arg1);
    }

    @Override
    public List<WorkflowTaskLineage> queryByProjectCode(long arg0) {
        return workflowTaskLineageDao.queryByProjectCode(arg0);
    }

    @Override
    public List<WorkflowTaskLineage> queryWorkFlowLineageByDept(long arg0, long arg1, long arg2) {
        return workflowTaskLineageDao.queryWorkFlowLineageByDept(arg0, arg1, arg2);
    }

    @Override
    public int updateWorkflowTaskLineage(List<WorkflowTaskLineage> arg0) {
        return workflowTaskLineageDao.updateWorkflowTaskLineage(arg0);
    }

    @Override
    public List<WorkflowTaskLineage> queryByWorkflowDefinitionCode(long arg0) {
        return workflowTaskLineageDao.queryByWorkflowDefinitionCode(arg0);
    }

    @Override
    public int batchDeleteByWorkflowDefinitionCode(List<Long> arg0) {
        return workflowTaskLineageDao.batchDeleteByWorkflowDefinitionCode(arg0);
    }

    @Override
    public int insert(WorkflowTaskLineage arg0) {
        return workflowTaskLineageDao.insert(arg0);
    }

    @Override
    public Optional<WorkflowTaskLineage> queryOptionalById(Serializable arg0) {
        return workflowTaskLineageDao.queryOptionalById(arg0);
    }

    @Override
    public void insertBatch(Collection<WorkflowTaskLineage> arg0) {
        workflowTaskLineageDao.insertBatch(arg0);
    }

    @Override
    public boolean deleteByIds(Collection<? extends Serializable> arg0) {
        return workflowTaskLineageDao.deleteByIds(arg0);
    }

    @Override
    public boolean deleteByCondition(WorkflowTaskLineage arg0) {
        return workflowTaskLineageDao.deleteByCondition(arg0);
    }

    @Override
    public List<WorkflowTaskLineage> queryByCondition(WorkflowTaskLineage arg0) {
        return workflowTaskLineageDao.queryByCondition(arg0);
    }

    @Override
    public List<WorkflowTaskLineage> queryByIds(Collection<? extends Serializable> arg0) {
        return workflowTaskLineageDao.queryByIds(arg0);
    }

    @Override
    public List<WorkflowTaskLineage> queryAll() {
        return workflowTaskLineageDao.queryAll();
    }

    @Override
    public boolean updateById(WorkflowTaskLineage arg0) {
        return workflowTaskLineageDao.updateById(arg0);
    }

    @Override
    public boolean deleteById(Serializable arg0) {
        return workflowTaskLineageDao.deleteById(arg0);
    }

    @Override
    public WorkflowTaskLineage queryById(Serializable arg0) {
        return workflowTaskLineageDao.queryById(arg0);
    }

}
