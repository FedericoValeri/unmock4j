---INTEGRATION_TEST_START---
```java
package org.apache.dolphinscheduler.api.python;

import org.apache.dolphinscheduler.api.service.ResourcesService;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PythonGatewayIntegrationTest {

    @InjectMocks
    private PythonGateway pythonGateway;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private WorkflowDefinitionMapper workflowDefinitionMapper;

    @Mock
    private TaskDefinitionMapper taskDefinitionMapper;

    @Mock
    private ResourcesService resourcesService;

    private ProjectMapper mockedProjectMapper_proxy = new ProjectMapper_Proxy(projectMapper);
    private WorkflowDefinitionMapper mockedWorkflowDefinitionMapper_proxy = new WorkflowDefinitionMapper_Proxy(workflowDefinitionMapper);
    private TaskDefinitionMapper mockedTaskDefinitionMapper_proxy = new TaskDefinitionMapper_Proxy(taskDefinitionMapper);

    @Test
    public void testGetCodeAndVersion() {
        Project project = getTestProject();
        Mockito.when(projectMapper.queryByName(project.getName())).thenReturn(mockedProjectMapper_proxy.queryByName(project.getName()));
        Assertions.assertEquals(1, mockedProjectMapper_proxy.method_verify());

        WorkflowDefinition workflowDefinition = getTestProcessDefinition();
        Mockito.when(workflowDefinitionMapper.queryByDefineName(project.getCode(), workflowDefinition.getName()))
                .thenReturn(mockedWorkflowDefinitionMapper_proxy.queryByDefineName(project.getCode(), workflowDefinition.getName()));
        Assertions.assertEquals(1, mockedWorkflowDefinitionMapper_proxy.method_verify());

        TaskDefinition taskDefinition = getTestTaskDefinition();
        Mockito.when(taskDefinitionMapper.queryByName(project.getCode(), workflowDefinition.getCode(),
                taskDefinition.getName())).thenReturn(mockedTaskDefinitionMapper_proxy.queryByName(project.getCode(), workflowDefinition.getCode(),
                taskDefinition.getName()));
        Assertions.assertEquals(1, mockedTaskDefinitionMapper_proxy.method_verify());

        Map<String, Long> result = pythonGateway.getCodeAndVersion(project.getName(), workflowDefinition.getName(),
                taskDefinition.getName());
        Assertions.assertEquals(result.get("code").longValue(), taskDefinition.getCode());
    }
}
```
---INTEGRATION_TEST_END---

---PROXIES_START---
```java
package org.apache.dolphinscheduler.dao.mapper;

public class ProjectMapper_EmptyProxy implements ProjectMapper {
    protected ProjectMapper dependency;

    public ProjectMapper_EmptyProxy(ProjectMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public Project queryByCode(long projectCode) {
        return dependency.queryByCode(projectCode);
    }

    @Override
    public List<Project> queryByCodes(Collection<Long> codes) {
        return dependency.queryByCodes(codes);
    }

    @Override
    public Project queryDetailById(int projectId) {
        return dependency.queryDetailById(projectId);
    }

    @Override
    public Project queryByName(String projectName) {
        return dependency.queryByName(projectName);
    }

    @Override
    public IPage<Project> queryProjectListPaging(IPage<Project> page, List<Integer> projectsIds, String searchName) {
        return dependency.queryProjectListPaging(page, projectsIds, searchName);
    }

    @Override
    public List<Project> queryProjectCreatedByUser(int userId) {
        return dependency.queryProjectCreatedByUser(userId);
    }

    @Override
    public List<Project> queryAuthedProjectListByUserId(int userId) {
        return dependency.queryAuthedProjectListByUserId(userId);
    }

    @Override
    public List<Project> queryProjectExceptUserId(int userId) {
        return dependency.queryProjectExceptUserId(userId);
    }

    @Override
    public List<Project> queryProjectCreatedAndAuthorizedByUserId(int userId) {
        return dependency.queryProjectCreatedAndAuthorizedByUserId(userId);
    }

    @Override
    public ProjectUser queryProjectWithUserByWorkflowInstanceId(int workflowInstanceId) {
        return dependency.queryProjectWithUserByWorkflowInstanceId(workflowInstanceId);
    }

    @Override
    public List<Project> queryAllProject(int userId) {
        return dependency.queryAllProject(userId);
    }

    @Override
    public List<Project> listAuthorizedProjects(int userId, List<Integer> projectsIds) {
        return dependency.listAuthorizedProjects(userId, projectsIds);
    }

    @Override
    public List<Project> queryAllProjectForDependent() {
        return dependency.queryAllProjectForDependent();
    }

    @Override
    public Project queryProjectByTaskInstanceId(int taskInstanceId) {
        return dependency.queryProjectByTaskInstanceId(taskInstanceId);
    }
}

public class ProjectMapper_Proxy extends ProjectMapper_EmptyProxy {
    private int methodCounter = 0;

    public ProjectMapper_Proxy(ProjectMapper dependency) {
        super(dependency);
    }

    @Override
    public Project queryByName(String projectName) {
        methodCounter++;
        Project result = dependency.queryByName(projectName);
        return result;
    }

    public int method_verify() {
        return methodCounter;
    }
}

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

public class WorkflowDefinitionMapper_Proxy extends WorkflowDefinitionMapper_EmptyProxy {
    private int methodCounter = 0;

    public WorkflowDefinitionMapper_Proxy(WorkflowDefinitionMapper dependency) {
        super(dependency);
    }

    @Override
    public WorkflowDefinition queryByDefineName(long projectCode, String workflowDefinitionName) {
        methodCounter++;
        WorkflowDefinition result = dependency.queryByDefineName(projectCode, workflowDefinitionName);
        return result;
    }

    public int method_verify() {
        return methodCounter;
    }
}

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

public class TaskDefinitionMapper_Proxy extends TaskDefinitionMapper_EmptyProxy {
    private int methodCounter = 0;

    public TaskDefinitionMapper_Proxy(TaskDefinitionMapper dependency) {
        super(dependency);
    }

    @Override
    public TaskDefinition queryByName(long projectCode, long workflowDefinitionCode, String name) {
        methodCounter++;
        TaskDefinition result = dependency.queryByName(projectCode, workflowDefinitionCode, name);
        return result;
    }

    public int method_verify() {
        return methodCounter;
    }
}
```
---PROXIES_END---