package org.apache.dolphinscheduler.api.python;

import org.apache.dolphinscheduler.api.service.ResourcesService;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;
import org.apache.dolphinscheduler.plugin.storage.api.StorageEntity;
import org.apache.dolphinscheduler.spi.enums.ResourceType;

import java.util.Date;
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

//    private ProjectMapper mockedDependency_proxy = new ProjectMapper_Proxy(projectMapper);
//    private WorkflowDefinitionMapper mockedDependency_proxy2 = new WorkflowDefinitionMapper_Proxy(workflowDefinitionMapper);
//    private TaskDefinitionMapper mockedDependency_proxy3 = new TaskDefinitionMapper_Proxy(taskDefinitionMapper);

    private ProjectMapper mockedDependency_proxy;
    private WorkflowDefinitionMapper mockedDependency_proxy2;
    private TaskDefinitionMapper mockedDependency_proxy3;

    public PythonGatewayIntegrationTest (ProjectMapper projectMapper, WorkflowDefinitionMapper workflowDefinitionMapper, TaskDefinitionMapper taskDefinitionMapper){
        this.mockedDependency_proxy = new ProjectMapper_Proxy(projectMapper);
        this.mockedDependency_proxy2 = new WorkflowDefinitionMapper_Proxy(workflowDefinitionMapper);
        this.mockedDependency_proxy3 = new TaskDefinitionMapper_Proxy(taskDefinitionMapper);        
    }
    
    @Test
    public void testGetCodeAndVersion() {
        Project project = getTestProject();
        Mockito.when(projectMapper.queryByName(project.getName())).thenReturn(mockedDependency_proxy.queryByName(project.getName()));

        WorkflowDefinition workflowDefinition = getTestProcessDefinition();
        Mockito.when(workflowDefinitionMapper.queryByDefineName(project.getCode(), workflowDefinition.getName()))
                .thenReturn(mockedDependency_proxy2.queryByDefineName(project.getCode(), workflowDefinition.getName()));

        TaskDefinition taskDefinition = getTestTaskDefinition();
        Mockito.when(taskDefinitionMapper.queryByName(project.getCode(), workflowDefinition.getCode(),
                taskDefinition.getName())).thenReturn(mockedDependency_proxy3.queryByName(project.getCode(), workflowDefinition.getCode(),
                taskDefinition.getName()));

        Map<String, Long> result = pythonGateway.getCodeAndVersion(project.getName(), workflowDefinition.getName(),
                taskDefinition.getName());
        Assertions.assertEquals(result.get("code").longValue(), taskDefinition.getCode());
    }

     private Project getTestProject() {
            Project project = new Project();
            project.setName("ut-project");
            project.setUserId(111);
            project.setCode(1L);
            project.setCreateTime(new Date());
            project.setUpdateTime(new Date());
            return project;
        }

        private WorkflowDefinition getTestProcessDefinition() {
            WorkflowDefinition workflowDefinition = new WorkflowDefinition();
            workflowDefinition.setCode(1L);
            workflowDefinition.setName("ut-process-definition");
            workflowDefinition.setProjectCode(1L);
            workflowDefinition.setUserId(111);
            workflowDefinition.setUpdateTime(new Date());
            workflowDefinition.setCreateTime(new Date());
            return workflowDefinition;
        }

        private TaskDefinition getTestTaskDefinition() {
            TaskDefinition taskDefinition = new TaskDefinition();
            taskDefinition.setCode(888888L);
            taskDefinition.setName("ut-task-definition");
            taskDefinition.setProjectCode(1L);
            taskDefinition.setTaskType("SHELL");
            taskDefinition.setUserId(111);
            taskDefinition.setResourceIds("1");
            taskDefinition.setWorkerGroup("default");
            taskDefinition.setEnvironmentCode(1L);
            taskDefinition.setVersion(1);
            taskDefinition.setCreateTime(new Date());
            taskDefinition.setUpdateTime(new Date());
            return taskDefinition;
        }
}
