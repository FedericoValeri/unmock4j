package org.apache.dolphinscheduler.api.python;

import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;
import org.apache.dolphinscheduler.dao.mapper.TaskDefinitionMapper;
import org.apache.dolphinscheduler.dao.mapper.WorkflowDefinitionMapper;

import java.util.Collections;
import java.util.List;

public class ProjectMapper_Proxy extends ProjectMapper_EmptyProxy {

    private final ProjectMapper dependency;

    public ProjectMapper_Proxy(ProjectMapper dependency) {
        super(dependency);
        this.dependency = dependency;
    }

    @Override
    public Project queryByName(String projectName) {
        Project result = dependency.queryByName(projectName);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        return result;
    }
}