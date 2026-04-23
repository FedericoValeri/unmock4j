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

    protected final TaskDefinitionMapper taskDefinitionMapper;

    public TaskDefinitionMapper_EmptyProxy(TaskDefinitionMapper taskDefinitionMapper) {
        this.taskDefinitionMapper = taskDefinitionMapper;
    }

    @Override
    public int deleteByBatchCodes(List<Long> arg0) {
        return taskDefinitionMapper.deleteByBatchCodes(arg0);
    }

    @Override
    public IPage<TaskDefinition> filterTaskDefinition(IPage<TaskDefinition> arg0, TaskDefinition arg1) {
        return taskDefinitionMapper.filterTaskDefinition(arg0, arg1);
    }

    @Override
    public TaskDefinition queryByName(long arg0, long arg1, String arg2) {
        return taskDefinitionMapper.queryByName(arg0, arg1, arg2);
    }

    @Override
    public List<TaskDefinition> queryDefinitionsByTaskType(String arg0) {
        return taskDefinitionMapper.queryDefinitionsByTaskType(arg0);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionGroupByUser(Long[] arg0) {
        return taskDefinitionMapper.countDefinitionGroupByUser(arg0);
    }

    @Override
    public List<TaskDefinition> queryByCodeList(Collection<Long> arg0) {
        return taskDefinitionMapper.queryByCodeList(arg0);
    }

    @Override
    public List<TaskMainInfo> queryDefineListByCodeList(long arg0, List<Long> arg1) {
        return taskDefinitionMapper.queryDefineListByCodeList(arg0, arg1);
    }

    @Override
    public int batchInsert(List<TaskDefinitionLog> arg0) {
        return taskDefinitionMapper.batchInsert(arg0);
    }

    @Override
    public void deleteByWorkflowDefinitionCodeAndVersion(long arg0, int arg1) {
        taskDefinitionMapper.deleteByWorkflowDefinitionCodeAndVersion(arg0, arg1);
    }

    @Override
    public List<String> queryAllTaskDefinitionWorkerGroups(long arg0) {
        return taskDefinitionMapper.queryAllTaskDefinitionWorkerGroups(arg0);
    }

    @Override
    public TaskDefinition queryByCode(long arg0) {
        return taskDefinitionMapper.queryByCode(arg0);
    }

    @Override
    public int deleteByCode(long arg0) {
        return taskDefinitionMapper.deleteByCode(arg0);
    }

    @Override
    public int update(TaskDefinition entity, Wrapper<TaskDefinition> updateWrapper) {
        return taskDefinitionMapper.update(entity, updateWrapper);
    }

    @Override
    public int insert(TaskDefinition entity) {
        return taskDefinitionMapper.insert(entity);
    }

    @Override
    public int delete(Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.delete(queryWrapper);
    }

    @Override
    public int updateById(TaskDefinition entity) {
        return taskDefinitionMapper.updateById(entity);
    }

    @Override
    public int deleteById(Serializable id) {
        return taskDefinitionMapper.deleteById(id);
    }

    @Override
    public int deleteById(TaskDefinition entity) {
        return taskDefinitionMapper.deleteById(entity);
    }

    @Override
    public TaskDefinition selectById(Serializable id) {
        return taskDefinitionMapper.selectById(id);
    }

    @Override
    public List<Object> selectObjs(Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectObjs(queryWrapper);
    }

    @Override
    public List<TaskDefinition> selectList(Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectList(queryWrapper);
    }

    @Override
    public <P extends IPage<TaskDefinition>> P selectPage(P page, Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectMaps(queryWrapper);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return taskDefinitionMapper.deleteByMap(columnMap);
    }

    @Override
    public List<TaskDefinition> selectBatchIds(Collection<? extends Serializable> idList) {
        return taskDefinitionMapper.selectBatchIds(idList);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return taskDefinitionMapper.deleteBatchIds(idList);
    }

    @Override
    public List<TaskDefinition> selectByMap(Map<String, Object> columnMap) {
        return taskDefinitionMapper.selectByMap(columnMap);
    }

    @Override
    public Long selectCount(Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<TaskDefinition> queryWrapper) {
        return taskDefinitionMapper.selectMapsPage(page, queryWrapper);
    }

}
