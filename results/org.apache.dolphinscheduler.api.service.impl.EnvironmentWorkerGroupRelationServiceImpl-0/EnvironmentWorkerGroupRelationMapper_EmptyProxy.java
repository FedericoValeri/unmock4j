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

    protected final EnvironmentWorkerGroupRelationMapper environmentWorkerGroupRelationMapper;

    public EnvironmentWorkerGroupRelationMapper_EmptyProxy(EnvironmentWorkerGroupRelationMapper environmentWorkerGroupRelationMapper) {
        this.environmentWorkerGroupRelationMapper = environmentWorkerGroupRelationMapper;
    }

    @Override
    public int deleteByCode(Long arg0, String arg1) {
        return environmentWorkerGroupRelationMapper.deleteByCode(arg0, arg1);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByWorkerGroupName(String arg0) {
        return environmentWorkerGroupRelationMapper.queryByWorkerGroupName(arg0);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> queryByEnvironmentCode(Long arg0) {
        return environmentWorkerGroupRelationMapper.queryByEnvironmentCode(arg0);
    }

    @Override
    public int update(EnvironmentWorkerGroupRelation entity, Wrapper<EnvironmentWorkerGroupRelation> updateWrapper) {
        return environmentWorkerGroupRelationMapper.update(entity, updateWrapper);
    }

    @Override
    public int insert(EnvironmentWorkerGroupRelation entity) {
        return environmentWorkerGroupRelationMapper.insert(entity);
    }

    @Override
    public int delete(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.delete(queryWrapper);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectByMap(Map<String, Object> columnMap) {
        return environmentWorkerGroupRelationMapper.selectByMap(columnMap);
    }

    @Override
    public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectMapsPage(page, queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return environmentWorkerGroupRelationMapper.deleteBatchIds(idList);
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return environmentWorkerGroupRelationMapper.deleteByMap(columnMap);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectBatchIds(Collection<? extends Serializable> idList) {
        return environmentWorkerGroupRelationMapper.selectBatchIds(idList);
    }

    @Override
    public Long selectCount(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteById(Serializable id) {
        return environmentWorkerGroupRelationMapper.deleteById(id);
    }

    @Override
    public int deleteById(EnvironmentWorkerGroupRelation entity) {
        return environmentWorkerGroupRelationMapper.deleteById(entity);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectMaps(queryWrapper);
    }

    @Override
    public List<Object> selectObjs(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectObjs(queryWrapper);
    }

    @Override
    public <P extends IPage<EnvironmentWorkerGroupRelation>> P selectPage(P page, Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectPage(page, queryWrapper);
    }

    @Override
    public EnvironmentWorkerGroupRelation selectById(Serializable id) {
        return environmentWorkerGroupRelationMapper.selectById(id);
    }

    @Override
    public List<EnvironmentWorkerGroupRelation> selectList(Wrapper<EnvironmentWorkerGroupRelation> queryWrapper) {
        return environmentWorkerGroupRelationMapper.selectList(queryWrapper);
    }

    @Override
    public int updateById(EnvironmentWorkerGroupRelation entity) {
        return environmentWorkerGroupRelationMapper.updateById(entity);
    }

}
