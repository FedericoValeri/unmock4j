```java
package org.apache.dolphinscheduler.dao.mapper;

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