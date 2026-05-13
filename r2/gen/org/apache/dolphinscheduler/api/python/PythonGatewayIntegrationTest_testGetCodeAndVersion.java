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

public class PythonGatewayIntegrationTest_testGetCodeAndVersion {

    private PythonGateway pythonGateway;

    private ProjectMapper projectMapper_proxy;

    private WorkflowDefinitionMapper workflowDefinitionMapper_proxy;

    private TaskDefinitionMapper taskDefinitionMapper_proxy;

    public PythonGatewayIntegrationTest_testGetCodeAndVersion(ProjectMapper projectMapper, WorkflowDefinitionMapper workflowDefinitionMapper, TaskDefinitionMapper taskDefinitionMapper) {
        this.projectMapper_proxy = new ProjectMapper_Proxy(projectMapper);
        this.workflowDefinitionMapper_proxy = new WorkflowDefinitionMapper_Proxy(workflowDefinitionMapper);
        this.taskDefinitionMapper_proxy = new TaskDefinitionMapper_Proxy(taskDefinitionMapper);
    }

    @Test
    public void testGetCodeAndVersion() {
        Project project = getTestProject();
        Assertions.assertEquals(1, ((ProjectMapper_Proxy) projectMapper_proxy).queryByName_verify());

        WorkflowDefinition workflowDefinition = getTestProcessDefinition();
        Assertions.assertEquals(1, ((WorkflowDefinitionMapper_Proxy) workflowDefinitionMapper_proxy).queryByDefineName_verify());

        TaskDefinition taskDefinition = getTestTaskDefinition();
        Assertions.assertEquals(1, ((TaskDefinitionMapper_Proxy) taskDefinitionMapper_proxy).queryByName_verify());

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