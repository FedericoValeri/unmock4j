package org.apache.dolphinscheduler.api.python;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.TaskDefinitionLog;
import org.apache.dolphinscheduler.dao.entity.TaskMainInfo;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.model.WorkflowDefinitionCountDto;

public class TaskDefinitionMapper_EmptyProxy implements TaskDefinitionMapper {

    protected final TaskDefinitionMapper dependency;

    public TaskDefinitionMapper_EmptyProxy(TaskDefinitionMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public List<String> queryAllTaskDefinitionWorkerGroups(long arg0) {
        return dependency.queryAllTaskDefinitionWorkerGroups(arg0);
    }

    @Override
    public void deleteByWorkflowDefinitionCodeAndVersion(long arg0, int arg1) {
        dependency.deleteByWorkflowDefinitionCodeAndVersion(arg0, arg1);
    }

    @Override
    public int deleteByCode(long arg0) {
        return dependency.deleteByCode(arg0);
    }

    @Override
    public TaskDefinition queryByCode(long arg0) {
        return dependency.queryByCode(arg0);
    }

    @Override
    public TaskDefinition queryByName(long arg0, long arg1, String arg2) {
        return dependency.queryByName(arg0, arg1, arg2);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionGroupByUser(Long[] arg0) {
        return dependency.countDefinitionGroupByUser(arg0);
    }

    @Override
    public int batchInsert(List<TaskDefinitionLog> arg0) {
        return dependency.batchInsert(arg0);
    }

    @Override
    public int deleteByBatchCodes(List<Long> arg0) {
        return dependency.deleteByBatchCodes(arg0);
    }

    @Override
    public List<TaskDefinition> queryDefinitionsByTaskType(String arg0) {
        return dependency.queryDefinitionsByTaskType(arg0);
    }

    @Override
    public List<TaskDefinition> queryByCodeList(Collection<Long> arg0) {
        return dependency.queryByCodeList(arg0);
    }

    @Override
    public IPage<TaskDefinition> filterTaskDefinition(IPage<TaskDefinition> arg0, TaskDefinition arg1) {
        return dependency.filterTaskDefinition(arg0, arg1);
    }

    @Override
    public List<TaskMainInfo> queryDefineListByCodeList(long arg0, List<Long> arg1) {
        return dependency.queryDefineListByCodeList(arg0, arg1);
    }

    @Override
    public int update(TaskDefinition entity, Wrapper<TaskDefinition> updateWrapper) {
        return dependency.update(entity, updateWrapper);
    }

    @Override
    public int insert(TaskDefinition entity) {
        return dependency.insert(entity);
    }

    @Override
    public int delete(Wrapper<TaskDefinition> queryWrapper) {
        return dependency.delete(queryWrapper);
    }

    @Override
    public List<TaskDefinition> selectBatchIds(Collection<? extends Serializable> idList) {
        return dependency.selectBatchIds(idList);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectMapsPage(page, queryWrapper);
    }

    @Override
    public Long selectCount(Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectCount(queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return dependency.deleteBatchIds(idList);
    }

    @Override
    public List<TaskDefinition> selectByMap(Map<String, Object> columnMap) {
        return dependency.selectByMap(columnMap);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return dependency.deleteByMap(columnMap);
    }

    @Override
    public List<Object> selectObjs(Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectObjs(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return dependency.deleteById(id);
    }

    @Override
    public int deleteById(TaskDefinition entity) {
        return dependency.deleteById(entity);
    }

    @Override
    public <P extends IPage<TaskDefinition>> P selectPage(P page, Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectPage(page, queryWrapper);
    }

    @Override
    public TaskDefinition selectById(Serializable id) {
        return dependency.selectById(id);
    }

    @Override
    public List<TaskDefinition> selectList(Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectList(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<TaskDefinition> queryWrapper) {
        return dependency.selectMaps(queryWrapper);
    }

    @Override
    public int updateById(TaskDefinition entity) {
        return dependency.updateById(entity);
    }

}
