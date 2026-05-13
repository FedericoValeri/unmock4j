package org.apache.dolphinscheduler.api.python;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.ProjectUser;
import org.apache.dolphinscheduler.dao.mapper.ProjectMapper;

public class ProjectMapper_EmptyProxy implements ProjectMapper {

    protected final ProjectMapper dependency;

    public ProjectMapper_EmptyProxy(ProjectMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public List<Project> queryAuthedProjectListByUserId(int arg0) {
        return dependency.queryAuthedProjectListByUserId(arg0);
    }

    @Override
    public List<Project> queryRelationProjectListByUserId(int arg0) {
        return dependency.queryRelationProjectListByUserId(arg0);
    }

    @Override
    public Project queryProjectByTaskInstanceId(int arg0) {
        return dependency.queryProjectByTaskInstanceId(arg0);
    }

    @Override
    public ProjectUser queryProjectWithUserByWorkflowInstanceId(int arg0) {
        return dependency.queryProjectWithUserByWorkflowInstanceId(arg0);
    }

    @Override
    public List<Project> queryProjectCreatedAndAuthorizedByUserId(int arg0) {
        return dependency.queryProjectCreatedAndAuthorizedByUserId(arg0);
    }

    @Override
    public List<Project> queryAllProjectForDependent() {
        return dependency.queryAllProjectForDependent();
    }

    @Override
    public IPage<Project> queryProjectListPaging(IPage<Project> arg0, List<Integer> arg1, String arg2) {
        return dependency.queryProjectListPaging(arg0, arg1, arg2);
    }

    @Override
    public Project queryDetailById(int arg0) {
        return dependency.queryDetailById(arg0);
    }

    @Override
    public List<Project> queryProjectCreatedByUser(int arg0) {
        return dependency.queryProjectCreatedByUser(arg0);
    }

    @Override
    public Project queryDetailByCode(long arg0) {
        return dependency.queryDetailByCode(arg0);
    }

    @Override
    public int queryAllWorkflowCounts(List<Long> arg0) {
        return dependency.queryAllWorkflowCounts(arg0);
    }

    @Override
    public List<Project> listAuthorizedProjects(int arg0, List<Integer> arg1) {
        return dependency.listAuthorizedProjects(arg0, arg1);
    }

    @Override
    public List<Project> queryAllProject(int arg0) {
        return dependency.queryAllProject(arg0);
    }

    @Override
    public List<Project> queryProjectExceptUserId(int arg0) {
        return dependency.queryProjectExceptUserId(arg0);
    }

    @Override
    public List<Project> queryByCodes(Collection<Long> arg0) {
        return dependency.queryByCodes(arg0);
    }

    @Override
    public Project queryByCode(long arg0) {
        return dependency.queryByCode(arg0);
    }

    @Override
    public Project queryByName(String arg0) {
        return dependency.queryByName(arg0);
    }

    @Override
    public int update(Project entity, Wrapper<Project> updateWrapper) {
        return dependency.update(entity, updateWrapper);
    }

    @Override
    public int insert(Project entity) {
        return dependency.insert(entity);
    }

    @Override
    public int delete(Wrapper<Project> queryWrapper) {
        return dependency.delete(queryWrapper);
    }

    @Override
    public List<Project> selectBatchIds(Collection<? extends Serializable> idList) {
        return dependency.selectBatchIds(idList);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<Project> queryWrapper) {
        return dependency.selectMapsPage(page, queryWrapper);
    }

    @Override
    public Long selectCount(Wrapper<Project> queryWrapper) {
        return dependency.selectCount(queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return dependency.deleteBatchIds(idList);
    }

    @Override
    public List<Project> selectByMap(Map<String, Object> columnMap) {
        return dependency.selectByMap(columnMap);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return dependency.deleteByMap(columnMap);
    }

    @Override
    public List<Object> selectObjs(Wrapper<Project> queryWrapper) {
        return dependency.selectObjs(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return dependency.deleteById(id);
    }

    @Override
    public int deleteById(Project entity) {
        return dependency.deleteById(entity);
    }

    @Override
    public <P extends IPage<Project>> P selectPage(P page, Wrapper<Project> queryWrapper) {
        return dependency.selectPage(page, queryWrapper);
    }

    @Override
    public Project selectById(Serializable id) {
        return dependency.selectById(id);
    }

    @Override
    public List<Project> selectList(Wrapper<Project> queryWrapper) {
        return dependency.selectList(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Project> queryWrapper) {
        return dependency.selectMaps(queryWrapper);
    }

    @Override
    public int updateById(Project entity) {
        return dependency.updateById(entity);
    }

}
