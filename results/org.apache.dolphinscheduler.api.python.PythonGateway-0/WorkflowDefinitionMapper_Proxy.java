package org.apache.dolphinscheduler.api.python;

import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;

import java.util.Collections;
import java.util.List;

public class WorkflowDefinitionMapper_Proxy extends WorkflowDefinitionMapper_EmptyProxy {

    private final WorkflowDefinitionMapper dependency;

    public WorkflowDefinitionMapper_Proxy(WorkflowDefinitionMapper dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public WorkflowDefinition queryByDefineName(long projectCode, String workflowDefinitionName) {
        WorkflowDefinition result = dependency.queryByDefineName(projectCode, workflowDefinitionName);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        return result;
    }
}