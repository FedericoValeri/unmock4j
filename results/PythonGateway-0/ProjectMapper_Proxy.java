```java
package org.apache.dolphinscheduler.dao.mapper;

public class ProjectMapper_Proxy extends ProjectMapper_EmptyProxy {
    private int methodCounter = 0;

    public ProjectMapper_Proxy(ProjectMapper dependency) {
        super(dependency);
    }

    @Override
    public Project queryByName(String projectName) {
        methodCounter++;
        Project result = dependency.queryByName(projectName);
        return result;
    }

    public int method_verify() {
        return methodCounter;
    }
}