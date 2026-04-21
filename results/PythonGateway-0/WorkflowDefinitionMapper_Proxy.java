```java
package org.apache.dolphinscheduler.dao.mapper;

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