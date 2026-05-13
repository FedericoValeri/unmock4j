package org.apache.dolphinscheduler.service.process;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.entity.UserWithWorkflowDefinitionCode;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;

public class UserMapper_EmptyProxy implements UserMapper {

    protected final UserMapper dependency;

    public UserMapper_EmptyProxy(UserMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public User queryDetailsById(int arg0) {
        return dependency.queryDetailsById(arg0);
    }

    @Override
    public List<User> selectByIds(List<Integer> arg0) {
        return dependency.selectByIds(arg0);
    }

    @Override
    public User queryUserByToken(String arg0, Date arg1) {
        return dependency.queryUserByToken(arg0, arg1);
    }

    @Override
    public IPage<User> queryUserPaging(Page arg0, String arg1) {
        return dependency.queryUserPaging(arg0, arg1);
    }

    @Override
    public Integer updateUserQueue(String arg0, String arg1) {
        return dependency.updateUserQueue(arg0, arg1);
    }

    @Override
    public int updateById(User arg0) {
        return dependency.updateById(arg0);
    }

    @Override
    public User selectById(int arg0) {
        return dependency.selectById(arg0);
    }

    @Override
    public int deleteById(int arg0) {
        return dependency.deleteById(arg0);
    }

    @Override
    public Boolean existUser(String arg0) {
        return dependency.existUser(arg0);
    }

    @Override
    public List<User> queryEnabledUsers() {
        return dependency.queryEnabledUsers();
    }

    @Override
    public List<UserWithWorkflowDefinitionCode> queryUserWithWorkflowDefinitionCode(List<Long> arg0) {
        return dependency.queryUserWithWorkflowDefinitionCode(arg0);
    }

    @Override
    public List<User> queryUserListByAlertGroupId(int arg0) {
        return dependency.queryUserListByAlertGroupId(arg0);
    }

    @Override
    public List<User> queryAuthedUserListByProjectId(int arg0) {
        return dependency.queryAuthedUserListByProjectId(arg0);
    }

    @Override
    public User queryByUserNameAccurately(String arg0) {
        return dependency.queryByUserNameAccurately(arg0);
    }

    @Override
    public User queryUserByNamePassword(String arg0, String arg1) {
        return dependency.queryUserByNamePassword(arg0, arg1);
    }

    @Override
    public List<User> queryAllGeneralUser() {
        return dependency.queryAllGeneralUser();
    }

    @Override
    public List<User> queryUserListByTenant(int arg0) {
        return dependency.queryUserListByTenant(arg0);
    }

    @Override
    public User queryTenantCodeByUserId(int arg0) {
        return dependency.queryTenantCodeByUserId(arg0);
    }

    @Override
    public List<User> queryUserListByQueue(String arg0) {
        return dependency.queryUserListByQueue(arg0);
    }

    @Override
    public int update(User entity, Wrapper<User> updateWrapper) {
        return dependency.update(entity, updateWrapper);
    }

    @Override
    public int insert(User entity) {
        return dependency.insert(entity);
    }

    @Override
    public int delete(Wrapper<User> queryWrapper) {
        return dependency.delete(queryWrapper);
    }

    @Override
    public List<Object> selectObjs(Wrapper<User> queryWrapper) {
        return dependency.selectObjs(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<User> queryWrapper) {
        return dependency.selectMaps(queryWrapper);
    }

    @Override
    public User selectById(Serializable id) {
        return dependency.selectById(id);
    }

    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
        return dependency.selectList(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return dependency.deleteById(id);
    }

    @Override
    public int deleteById(User entity) {
        return dependency.deleteById(entity);
    }

    @Override
    public <P extends IPage<User>> P selectPage(P page, Wrapper<User> queryWrapper) {
        return dependency.selectPage(page, queryWrapper);
    }

    @Override
    public List<User> selectBatchIds(Collection<? extends Serializable> idList) {
        return dependency.selectBatchIds(idList);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return dependency.deleteByMap(columnMap);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return dependency.deleteBatchIds(idList);
    }

    @Override
    public List<User> selectByMap(Map<String, Object> columnMap) {
        return dependency.selectByMap(columnMap);
    }

    @Override
    public Long selectCount(Wrapper<User> queryWrapper) {
        return dependency.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<User> queryWrapper) {
        return dependency.selectMapsPage(page, queryWrapper);
    }

}
