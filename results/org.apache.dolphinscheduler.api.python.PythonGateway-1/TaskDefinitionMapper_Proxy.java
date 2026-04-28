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

public class TaskDefinitionMapper_Proxy extends TaskDefinitionMapper_EmptyProxy {

    public TaskDefinitionMapper_Proxy(TaskDefinitionMapper taskDefinitionMapper) {
        super(taskDefinitionMapper);
    }

    @Override
    public TaskDefinition queryByName(long projectCode, long workflowDefinitionCode, String name) {
        TaskDefinition result = dependency.queryByName(projectCode, workflowDefinitionCode, name);
        Assertions.assertNotNull(result);
        return result;
    }
}