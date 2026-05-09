package org.apache.dolphinscheduler.api.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.dolphinscheduler.dao.entity.EnvironmentWorkerGroupRelation;
import org.apache.dolphinscheduler.dao.mapper.EnvironmentWorkerGroupRelationMapper;

public class EnvironmentWorkerGroupRelationMapper_EmptyProxy implements EnvironmentWorkerGroupRelationMapper {

    protected final EnvironmentWorkerGroupRelationMapper dependency;

    public EnvironmentWorkerGroupRelationMapper_EmptyProxy(EnvironmentWorkerGroupRelationMapper dependency) {
        this.dependency = dependency;
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByWorkerGroupName(String arg0) {
        return dependency.queryByWorkerGroupName(arg0);
    }

    @Override
    public int deleteByCode(Long arg0, String arg1) {
        return dependency.deleteByCode(arg0, arg1);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByEnvironmentCode(Long arg0) {
        return dependency.queryByEnvironmentCode(arg0);
    }

    @Override
    public int update(EnvironmentWorkerGroupRelation entity, Wrapper<EnvironmentWorkerGroupRelation> updateWrapper) {
        return dependency.update(entity, updateWrapper);
    }

    @Override
    public int insert(EnvironmentWorkerGroupRelation entity) {
        return dependency.insert(entity);
    }

    @Override
    public int delete(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.delete(queryWrapper);
    }

    @Override
    public int updateById(EnvironmentWorkerGroupRelation entity) {
        return dependency.updateById(entity);
    }

    @Override
    public List<Object> selectObjs(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectObjs(queryWrapper);
    }

    @Override
    public EnvironmentWorkerGroupRelation selectById(Serializable id) {
        return dependency.selectById(id);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectMaps(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return dependency.deleteById(id);
    }

    @Override
    public int deleteById(EnvironmentWorkerGroupRelation entity) {
        return dependency.deleteById(entity);
    }

    @Override
    public <P extends IPage<EnvironmentWorkerGroupRelation>> P selectPage(P page, Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectPage(page, queryWrapper);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectList(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectList(queryWrapper);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectBatchIds(Collection<? extends Serializable> idList) {
        return dependency.selectBatchIds(idList);
    }

    @Override
    public Long selectCount(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectCount(queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return dependency.deleteBatchIds(idList);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return dependency.deleteByMap(columnMap);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectByMap(Map<String, Object> columnMap) {
        return dependency.selectByMap(columnMap);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return dependency.selectMapsPage(page, queryWrapper);
    }

}
