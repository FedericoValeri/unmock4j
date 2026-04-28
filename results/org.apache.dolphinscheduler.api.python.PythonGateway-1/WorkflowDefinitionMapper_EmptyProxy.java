package org.apache.dolphinscheduler.api.python;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.dolphinscheduler.dao.entity.DependentSimplifyDefinition;
import org.apache.dolphinscheduler.dao.entity.ProjectWorkflowDefinitionCount;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;
import org.apache.dolphinscheduler.dao.model.WorkflowDefinitionCountDto;

public class WorkflowDefinitionMapper_EmptyProxy implements WorkflowDefinitionMapper {

    protected final WorkflowDefinitionMapper workflowDefinitionMapper;

    public WorkflowDefinitionMapper_EmptyProxy(WorkflowDefinitionMapper workflowDefinitionMapper) {
        this.workflowDefinitionMapper = workflowDefinitionMapper;
    }

    @Override
    public int deleteByCode(long arg0) {
        return workflowDefinitionMapper.deleteByCode(arg0);
    }

    @Override
    public WorkflowDefinition queryByCode(long arg0) {
        return workflowDefinitionMapper.queryByCode(arg0);
    }

    @Override
    public List<WorkflowDefinition> queryByCodes(Collection<Long> arg0) {
        return workflowDefinitionMapper.queryByCodes(arg0);
    }

    @Override
    public WorkflowDefinition verifyByDefineName(long arg0, String arg1) {
        return workflowDefinitionMapper.verifyByDefineName(arg0, arg1);
    }

    @Override
    public WorkflowDefinition queryByDefineId(int arg0) {
        return workflowDefinitionMapper.queryByDefineId(arg0);
    }

    @Override
    public WorkflowDefinition queryByDefineName(long arg0, String arg1) {
        return workflowDefinitionMapper.queryByDefineName(arg0, arg1);
    }

    @Override
    public IPage<WorkflowDefinition> queryDefineListPaging(IPage<WorkflowDefinition> arg0, String arg1, int arg2, long arg3) {
        return workflowDefinitionMapper.queryDefineListPaging(arg0, arg1, arg2, arg3);
    }

    @Override
    public List<Integer> listProjectIds() {
        return workflowDefinitionMapper.listProjectIds();
    }

    @Override
    public IPage<WorkflowDefinition> filterWorkflowDefinition(IPage<WorkflowDefinition> arg0, WorkflowDefinition arg1) {
        return workflowDefinitionMapper.filterWorkflowDefinition(arg0, arg1);
    }

    @Override
    public List<WorkflowDefinition> queryAllDefinitionList(long arg0) {
        return workflowDefinitionMapper.queryAllDefinitionList(arg0);
    }

    @Override
    public List<WorkflowDefinition> queryDefinitionListByIdList(Integer[] arg0) {
        return workflowDefinitionMapper.queryDefinitionListByIdList(arg0);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionByProjectCodes(Collection<Long> arg0) {
        return workflowDefinitionMapper.countDefinitionByProjectCodes(arg0);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionByProjectCodesV2(List<Long> arg0, Integer arg1, Integer arg2) {
        return workflowDefinitionMapper.countDefinitionByProjectCodesV2(arg0, arg1, arg2);
    }

    @Override
    public List<Long> queryDefinitionCodeListByProjectCodes(List<Long> arg0) {
        return workflowDefinitionMapper.queryDefinitionCodeListByProjectCodes(arg0);
    }

    @Override
    public List<DependentSimplifyDefinition> queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(long arg0, Collection<Long> arg1) {
        return workflowDefinitionMapper.queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(arg0, arg1);
    }

    @Override
    public List<ProjectWorkflowDefinitionCount> queryProjectWorkflowDefinitionCountByProjectCodes(List<Long> arg0) {
        return workflowDefinitionMapper.queryProjectWorkflowDefinitionCountByProjectCodes(arg0);
    }

    @Override
    public int updateById(WorkflowDefinition arg0) {
        return workflowDefinitionMapper.updateById(arg0);
    }

    @Override
    public int update(WorkflowDefinition entity, Wrapper<WorkflowDefinition> updateWrapper) {
        return workflowDefinitionMapper.update(entity, updateWrapper);
    }

    @Override
    public int insert(WorkflowDefinition entity) {
        return workflowDefinitionMapper.insert(entity);
    }

    @Override
    public int delete(Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.delete(queryWrapper);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectMapsPage(page, queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return workflowDefinitionMapper.deleteBatchIds(idList);
    }

    @Override
    public List<WorkflowDefinition> selectBatchIds(Collection<? extends Serializable> idList) {
        return workflowDefinitionMapper.selectBatchIds(idList);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return workflowDefinitionMapper.deleteByMap(columnMap);
    }

    @Override
    public List<WorkflowDefinition> selectByMap(Map<String, Object> columnMap) {
        return workflowDefinitionMapper.selectByMap(columnMap);
    }

    @Override
    public Long selectCount(Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return workflowDefinitionMapper.deleteById(id);
    }

    @Override
    public int deleteById(WorkflowDefinition entity) {
        return workflowDefinitionMapper.deleteById(entity);
    }

    @Override
    public List<WorkflowDefinition> selectList(Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectList(queryWrapper);
    }

    @Override
    public List<Object> selectObjs(Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectObjs(queryWrapper);
    }

    @Override
    public WorkflowDefinition selectById(Serializable id) {
        return workflowDefinitionMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectMaps(queryWrapper);
    }

    @Override
    public <P extends IPage<WorkflowDefinition>> P selectPage(P page, Wrapper<WorkflowDefinition> queryWrapper) {
        return workflowDefinitionMapper.selectPage(page, queryWrapper);
    }

}
