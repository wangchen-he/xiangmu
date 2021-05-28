package com.pmcc.core.common.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CommonServiceImpl <br>
 * @Description: TODO
 * @Date: 2019/12/14 19:33 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>通用Service
 */
@Transactional
public abstract class CommonServiceImpl<T, PK extends Serializable> implements ICommonService<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract AbstractBaseDao<T, PK> getEntityDao();

    protected Class<T> entityClass;

    public CommonServiceImpl() {
        this.entityClass = ReflectionUtils.getSuperGenericType(getClass());
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public T save(T entity) {
        return (T) getEntityDao().add(entity);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public boolean delete(T entity) {
        return getEntityDao().delete(entity);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public boolean deleteByID(PK id) {
        return getEntityDao().deleteByID(entityClass,id);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public int deleteListEntitys(List<T> entitys) {
        return getEntityDao().deleteListEntitys(entitys);
    }

    @Override
    public T update(T entity) {
        return (T) getEntityDao().update(entity);
    }

    @Transactional(readOnly=true)
    @Override
    public List<T> findAll() {
        return getEntityDao().findAll(entityClass);
    }

    @Transactional(readOnly=true)
    @Override
    public T findById(PK id) {
        return (T) getEntityDao().findById(entityClass, id);
    }

    @Transactional(readOnly=true)
    @Override
    public List<T> findByField(Map<String, Object> params) {
        return (List<T>) getEntityDao().findByField(entityClass, params);
    }

    @Transactional(readOnly=true)
    @Override
    public List<T> findByFieldAndPages(Class<T> clazz, Map<String, Object> params, int page, int size) {
        return (List<T>) getEntityDao().findByFieldAndPages(entityClass, params, page, size);
    }

    @Override
    public int count() {
        return getEntityDao().count();
    }


}
