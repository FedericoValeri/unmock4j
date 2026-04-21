```java
package org.apache.dolphinscheduler.dao.mapper;

public class WorkflowDefinitionMapper_EmptyProxy implements WorkflowDefinitionMapper {
    protected WorkflowDefinitionMapper dependency;

    public WorkflowDefinitionMapper_EmptyProxy(WorkflowDefinitionMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public WorkflowDefinition queryByCode(long code) {
        return dependency.queryByCode(code);
    }

    @Override
    public int updateById(WorkflowDefinition workflowDefinition) {
        return dependency.updateById(workflowDefinition);
    }

    @Override
    public int deleteByCode(long code) {
        return dependency.deleteByCode(code);
    }

    @Override
    public List<WorkflowDefinition> queryByCodes(Collection<Long> codes) {
        return dependency.queryByCodes(codes);
    }

    @Override
    public WorkflowDefinition verifyByDefineName(long projectCode, String workflowDefinitionName) {
        return dependency.verifyByDefineName(projectCode, workflowDefinitionName);
    }

    @Override
    public WorkflowDefinition queryByDefineName(long projectCode, String workflowDefinitionName) {
        return dependency.queryByDefineName(projectCode, workflowDefinitionName);
    }

    @Override
    public WorkflowDefinition queryByDefineId(int workflowDefinitionId) {
        return dependency.queryByDefineId(workflowDefinitionId);
    }

    @Override
    public IPage<WorkflowDefinition> queryDefineListPaging(IPage<WorkflowDefinition> page, String searchVal, int userId, long projectCode) {
        return dependency.queryDefineListPaging(page, searchVal, userId, projectCode);
    }

    @Override
    public List<WorkflowDefinition> queryAllDefinitionList(long projectCode) {
        return dependency.queryAllDefinitionList(projectCode);
    }

    @Override
    public List<DependentSimplifyDefinition> queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(long projectCode, Collection<Long> codes) {
        return dependency.queryDefinitionListByProjectCodeAndWorkflowDefinitionCodes(projectCode, codes);
    }

    @Override
    public List<WorkflowDefinition> queryDefinitionListByIdList(Integer[] ids) {
        return dependency.queryDefinitionListByIdList(ids);
    }

    @Override
    public List<WorkflowDefinitionCountDto> countDefinitionByProjectCodes(Collection<Long> projectCodes) {
        return dependency.countDefinitionByProjectCodes(projectCodes);
    }

    @Override
    public List<Integer> listProjectIds() {
        return dependency.listProjectIds();
    }

    @Override
    public List<Long> queryDefinitionCodeListByProjectCodes(List<Long> projectCodes) {
        return dependency.queryDefinitionCodeListByProjectCodes(projectCodes);
    }

    @Override
    public List<ProjectWorkflowDefinitionCount> queryProjectWorkflowDefinitionCountByProjectCodes(List<Long> projectCodes) {
        return dependency.queryProjectWorkflowDefinitionCountByProjectCodes(projectCodes);
    }
}