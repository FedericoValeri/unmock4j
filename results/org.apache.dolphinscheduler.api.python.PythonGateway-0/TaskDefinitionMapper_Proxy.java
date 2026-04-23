package org.apache.dolphinscheduler.api.python;

import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;

import java.util.Collections;
import java.util.List;

public class TaskDefinitionMapper_Proxy extends TaskDefinitionMapper_EmptyProxy {

    private final TaskDefinitionMapper dependency;

    public TaskDefinitionMapper_Proxy(TaskDefinitionMapper dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public TaskDefinition queryByName(long projectCode, long workflowDefinitionCode, String name) {
        TaskDefinition result = dependency.queryByName(projectCode, workflowDefinitionCode, name);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        return result;
    }
}