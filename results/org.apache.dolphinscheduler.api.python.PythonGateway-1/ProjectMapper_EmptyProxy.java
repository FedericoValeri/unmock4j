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

    protected final ProjectMapper projectMapper;

    public ProjectMapper_EmptyProxy(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public int queryAllWorkflowCounts(List<Long> arg0) {
        return projectMapper.queryAllWorkflowCounts(arg0);
    }

    @Override
    public List<Project> listAuthorizedProjects(int arg0, List<Integer> arg1) {
        return projectMapper.listAuthorizedProjects(arg0, arg1);
    }

    @Override
    public List<Project> queryAllProject(int arg0) {
        return projectMapper.queryAllProject(arg0);
    }

    @Override
    public List<Project> queryProjectCreatedByUser(int arg0) {
        return projectMapper.queryProjectCreatedByUser(arg0);
    }

    @Override
    public Project queryDetailByCode(long arg0) {
        return projectMapper.queryDetailByCode(arg0);
    }

    @Override
    public Project queryDetailById(int arg0) {
        return projectMapper.queryDetailById(arg0);
    }

    @Override
    public IPage<Project> queryProjectListPaging(IPage<Project> arg0, List<Integer> arg1, String arg2) {
        return projectMapper.queryProjectListPaging(arg0, arg1, arg2);
    }

    @Override
    public Project queryByCode(long arg0) {
        return projectMapper.queryByCode(arg0);
    }

    @Override
    public List<Project> queryByCodes(Collection<Long> arg0) {
        return projectMapper.queryByCodes(arg0);
    }

    @Override
    public Project queryByName(String arg0) {
        return projectMapper.queryByName(arg0);
    }

    @Override
    public List<Project> queryAuthedProjectListByUserId(int arg0) {
        return projectMapper.queryAuthedProjectListByUserId(arg0);
    }

    @Override
    public ProjectUser queryProjectWithUserByWorkflowInstanceId(int arg0) {
        return projectMapper.queryProjectWithUserByWorkflowInstanceId(arg0);
    }

    @Override
    public List<Project> queryAllProjectForDependent() {
        return projectMapper.queryAllProjectForDependent();
    }

    @Override
    public Project queryProjectByTaskInstanceId(int arg0) {
        return projectMapper.queryProjectByTaskInstanceId(arg0);
    }

    @Override
    public List<Project> queryRelationProjectListByUserId(int arg0) {
        return projectMapper.queryRelationProjectListByUserId(arg0);
    }

    @Override
    public List<Project> queryProjectCreatedAndAuthorizedByUserId(int arg0) {
        return projectMapper.queryProjectCreatedAndAuthorizedByUserId(arg0);
    }

    @Override
    public List<Project> queryProjectExceptUserId(int arg0) {
        return projectMapper.queryProjectExceptUserId(arg0);
    }

    @Override
    public int update(Project entity, Wrapper<Project> updateWrapper) {
        return projectMapper.update(entity, updateWrapper);
    }

    @Override
    public int insert(Project entity) {
        return projectMapper.insert(entity);
    }

    @Override
    public int delete(Wrapper<Project> queryWrapper) {
        return projectMapper.delete(queryWrapper);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<Project> queryWrapper) {
        return projectMapper.selectMapsPage(page, queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return projectMapper.deleteBatchIds(idList);
    }

    @Override
    public List<Project> selectBatchIds(Collection<? extends Serializable> idList) {
        return projectMapper.selectBatchIds(idList);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return projectMapper.deleteByMap(columnMap);
    }

    @Override
    public List<Project> selectByMap(Map<String, Object> columnMap) {
        return projectMapper.selectByMap(columnMap);
    }

    @Override
    public Long selectCount(Wrapper<Project> queryWrapper) {
        return projectMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return projectMapper.deleteById(id);
    }

    @Override
    public int deleteById(Project entity) {
        return projectMapper.deleteById(entity);
    }

    @Override
    public int updateById(Project entity) {
        return projectMapper.updateById(entity);
    }

    @Override
    public List<Project> selectList(Wrapper<Project> queryWrapper) {
        return projectMapper.selectList(queryWrapper);
    }

    @Override
    public List<Object> selectObjs(Wrapper<Project> queryWrapper) {
        return projectMapper.selectObjs(queryWrapper);
    }

    @Override
    public Project selectById(Serializable id) {
        return projectMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Project> queryWrapper) {
        return projectMapper.selectMaps(queryWrapper);
    }

    @Override
    public <P extends IPage<Project>> P selectPage(P page, Wrapper<Project> queryWrapper) {
        return projectMapper.selectPage(page, queryWrapper);
    }

}
