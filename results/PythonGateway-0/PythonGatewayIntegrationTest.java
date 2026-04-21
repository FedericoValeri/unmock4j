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