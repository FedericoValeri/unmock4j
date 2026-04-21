```java
package org.apache.dolphinscheduler.dao.mapper;

public class ProjectMapper_EmptyProxy implements ProjectMapper {
    protected ProjectMapper dependency;

    public ProjectMapper_EmptyProxy(ProjectMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public Project queryByCode(long projectCode) {
        return dependency.queryByCode(projectCode);
    }

    @Override
    public List<Project> queryByCodes(Collection<Long> codes) {
        return dependency.queryByCodes(codes);
    }

    @Override
    public Project queryDetailById(int projectId) {
        return dependency.queryDetailById(projectId);
    }

    @Override
    public Project queryByName(String projectName) {
        return dependency.queryByName(projectName);
    }

    @Override
    public IPage<Project> queryProjectListPaging(IPage<Project> page, List<Integer> projectsIds, String searchName) {
        return dependency.queryProjectListPaging(page, projectsIds, searchName);
    }

    @Override
    public List<Project> queryProjectCreatedByUser(int userId) {
        return dependency.queryProjectCreatedByUser(userId);
    }

    @Override
    public List<Project> queryAuthedProjectListByUserId(int userId) {
        return dependency.queryAuthedProjectListByUserId(userId);
    }

    @Override
    public List<Project> queryProjectExceptUserId(int userId) {
        return dependency.queryProjectExceptUserId(userId);
    }

    @Override
    public List<Project> queryProjectCreatedAndAuthorizedByUserId(int userId) {
        return dependency.queryProjectCreatedAndAuthorizedByUserId(userId);
    }

    @Override
    public ProjectUser queryProjectWithUserByWorkflowInstanceId(int workflowInstanceId) {
        return dependency.queryProjectWithUserByWorkflowInstanceId(workflowInstanceId);
    }

    @Override
    public List<Project> queryAllProject(int userId) {
        return dependency.queryAllProject(userId);
    }

    @Override
    public List<Project> listAuthorizedProjects(int userId, List<Integer> projectsIds) {
        return dependency.listAuthorizedProjects(userId, projectsIds);
    }

    @Override
    public List<Project> queryAllProjectForDependent() {
        return dependency.queryAllProjectForDependent();
    }

    @Override
    public Project queryProjectByTaskInstanceId(int taskInstanceId) {
        return dependency.queryProjectByTaskInstanceId(taskInstanceId);
    }
}