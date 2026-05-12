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

    protected final WorkflowDefinitionMapper dependency;

    public WorkflowDefinitionMapper_EmptyProxy(WorkflowDefinitionMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public int updateById(WorkflowDefinition arg0) {
        return dependency.updateById(arg0);
    }

    @Override
    public List<DependentSimplifyDefinition> queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(long arg0, Collection<Long> arg1) {
        return dependency.queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(arg0, arg1);
    }

    @Override
    public List<WorkflowDefinition> queryDefinitionListByIdList(Integer[] arg0) {
        return dependency.queryDefinitionListByIdList(arg0);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionByProjectCodesV2(List<Long> arg0, Integer arg1, Integer arg2) {
        return dependency.countDefinitionByProjectCodesV2(arg0, arg1, arg2);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionByProjectCodes(Collection<Long> arg0) {
        return dependency.countDefinitionByProjectCodes(arg0);
    }

    @Override
    public List<Long> queryDefinitionCodeListByProjectCodes(List<Long> arg0) {
        return dependency.queryDefinitionCodeListByProjectCodes(arg0);
    }

    @Override
    public List<Integer> listProjectIds() {
        return dependency.listProjectIds();
    }

    @Override
    public List<WorkflowDefinition> queryByCodes(Collection<Long> arg0) {
        return dependency.queryByCodes(arg0);
    }

    @Override
    public WorkflowDefinition queryByDefineId(int arg0) {
        return dependency.queryByDefineId(arg0);
    }

    @Override
    public WorkflowDefinition queryByCode(long arg0) {
        return dependency.queryByCode(arg0);
    }

    @Override
    public WorkflowDefinition verifyByDefineName(long arg0, String arg1) {
        return dependency.verifyByDefineName(arg0, arg1);
    }

    @Override
    public WorkflowDefinition queryByDefineName(long arg0, String arg1) {
        return dependency.queryByDefineName(arg0, arg1);
    }

    @Override
    public int deleteByCode(long arg0) {
        return dependency.deleteByCode(arg0);
    }

    @Override
    public IPage<WorkflowDefinition> queryDefineListPaging(IPage<WorkflowDefinition> arg0, String arg1, int arg2, long arg3) {
        return dependency.queryDefineListPaging(arg0, arg1, arg2, arg3);
    }

    @Override
    public IPage<WorkflowDefinition> filterWorkflowDefinition(IPage<WorkflowDefinition> arg0, WorkflowDefinition arg1) {
        return dependency.filterWorkflowDefinition(arg0, arg1);
    }

    @Override
    public List<WorkflowDefinition> queryAllDefinitionList(long arg0) {
        return dependency.queryAllDefinitionList(arg0);
    }

    @Override
    public List<ProjectWorkflowDefinitionCount> queryProjectWorkflowDefinitionCountByProjectCodes(List<Long> arg0) {
        return dependency.queryProjectWorkflowDefinitionCountByProjectCodes(arg0);
    }

    @Override
    public List<Object> selectObjs(Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectObjs(queryWrapper);
    }

    @Override
    public List<WorkflowDefinition> selectList(Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectList(queryWrapper);
    }

    @Override
    public <P extends IPage<WorkflowDefinition>> P selectPage(P page, Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectPage(page, queryWrapper);
    }

    @Override
    public WorkflowDefinition selectById(Serializable id) {
        return dependency.selectById(id);
    }

    @Override
    public int deleteById(WorkflowDefinition entity) {
        return dependency.deleteById(entity);
    }

    @Override
    public int deleteById(Serializable id) {
        return dependency.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectMaps(queryWrapper);
    }

    @Override
    public int update(WorkflowDefinition entity, Wrapper<WorkflowDefinition> updateWrapper) {
        return dependency.update(entity, updateWrapper);
    }

    @Override
    public int insert(WorkflowDefinition entity) {
        return dependency.insert(entity);
    }

    @Override
    public int delete(Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.delete(queryWrapper);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return dependency.deleteByMap(columnMap);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return dependency.deleteBatchIds(idList);
    }

    @Override
    public List<WorkflowDefinition> selectBatchIds(Collection<? extends Serializable> idList) {
        return dependency.selectBatchIds(idList);
    }

    @Override
    public Long selectCount(Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<WorkflowDefinition> queryWrapper) {
        return dependency.selectMapsPage(page, queryWrapper);
    }

    @Override
    public List<WorkflowDefinition> selectByMap(Map<String, Object> columnMap) {
        return dependency.selectByMap(columnMap);
    }

}
