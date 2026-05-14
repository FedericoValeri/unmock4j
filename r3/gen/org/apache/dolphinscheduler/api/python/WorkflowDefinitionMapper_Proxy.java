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



class WorkflowDefinitionMapper_Proxy extends WorkflowDefinitionMapper_EmptyProxy {
    WorkflowDefinitionMapper_Proxy(WorkflowDefinitionMapper dependency) {
        super(dependency);
    }

    public WorkflowDefinition queryByDefineName(long projectCode, String workflowDefinitionName) {
        WorkflowDefinition result = dependency.queryByDefineName(projectCode, workflowDefinitionName);
        if (projectCode == 1L && "ut-process-definition".equals(workflowDefinitionName)) {
            Assertions.assertNotNull(result);
        }
        return result;
    }
}