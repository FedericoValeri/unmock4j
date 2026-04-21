```java
package org.apache.dolphinscheduler.dao.mapper;

public class TaskDefinitionMapper_EmptyProxy implements TaskDefinitionMapper {
    protected TaskDefinitionMapper dependency;

    public TaskDefinitionMapper_EmptyProxy(TaskDefinitionMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public TaskDefinition queryByName(long projectCode, long workflowDefinitionCode, String name) {
        return dependency.queryByName(projectCode, workflowDefinitionCode, name);
    }

    @Override
    public TaskDefinition queryByCode(long code) {
        return dependency.queryByCode(code);
    }

    @Override
    public List<String> queryAllTaskDefinitionWorkerGroups(long projectCode) {
        return dependency.queryAllTaskDefinitionWorkerGroups(projectCode);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionGroupByUser(Long[] projectCodes) {
        return dependency.countDefinitionGroupByUser(projectCodes);
    }

    @Override
    public int deleteByCode(long code) {
        return dependency.deleteByCode(code);
    }

    @Override
    public int batchInsert(List<TaskDefinitionLog> taskDefinitions) {
        return dependency.batchInsert(taskDefinitions);
    }

    @Override
    public List<TaskDefinition> queryByCodeList(Collection<Long> codes) {
        return dependency.queryByCodeList(codes);
    }

    @Override
    public int deleteByBatchCodes(List<Long> taskCodeList) {
        return dependency.deleteByBatchCodes(taskCodeList);
    }

    @Override
    public void deleteByWorkflowDefinitionCodeAndVersion(long workflowDefinitionCode, int workflowDefinitionVersion) {
        dependency.deleteByWorkflowDefinitionCodeAndVersion(workflowDefinitionCode, workflowDefinitionVersion);
    }

    @Override
    public List<TaskDefinition> queryDefinitionsByTaskType(String taskType) {
        return dependency.queryDefinitionsByTaskType(taskType);
    }
}