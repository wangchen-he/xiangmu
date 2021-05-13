package com.pmcc.core.common.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: AbstractBaseDao <br>
 * @Description: 通用查询接口实现
 * @Date: 2019/12/10 22:31 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class AbstractBaseDao<T, PK extends Serializable> implements BaseDao<T, PK> {

    @PersistenceContext
    public EntityManager entityManager;

    private Class<T> clazz = null;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public Session getSession() {
//        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
        return entityManager.unwrap(Session.class);
    }


    public AbstractBaseDao() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Transactional
    @Override
    public T add(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }

    @Transactional
    @Override
    public boolean deleteByID(Class<T> clazz,PK id) {
        boolean flag = false;
        try {
            T t = entityManager.getReference(clazz, id);
            entityManager.remove(t);
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }

    @Override
    public int deleteListEntitys(List<T> entitys) {
        int flag=0;
        for(T entity:entitys) {
            entityManager.remove(entityManager.merge(entity));
            flag++;
        }
        return flag;
    }

    @Transactional
    @Override
    public int deleteByField(Class<T> clazz, Map<String, Object> params) {
        String sql="delete from "+clazz.getSimpleName()+" u WHERE ";
        Set<String> set=null;
        set=params.keySet();
        List<String> list=new ArrayList<String>(set);
        List<Object> fieldList=new ArrayList<Object>();
        int sqlcount=1;
        for(String field:list) {
            sql+="u."+field+"=?"+sqlcount+" and ";
            sqlcount++;
            fieldList.add(field);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println("sql:"+sql);
        Query query=entityManager.createQuery(sql);
        for(int i=0;i<fieldList.size();i++) {
            query.setParameter(i+1, params.get(fieldList.get(i)));
        }
        int executeUpdate = query.executeUpdate();
        return executeUpdate;
    }

    @Transactional
    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }

    @Override
    public T findById(Class<T> clazz,PK id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery("from " + clazz.getSimpleName()).getResultList();
    }

    @Override
    public List<T> findByFieldAndPages(Class<T> clazz,Map<String, Object> params,int page, int size) {
        String sql="from "+clazz.getSimpleName()+" u WHERE ";
        Set<String> set=null;
        set=params.keySet();
        List<String> list=new ArrayList<String>(set);
        List<Object> fieldList=new ArrayList<Object>();
        for(String field:list) {
            sql+="u."+field+"=? and ";
            fieldList.add(field);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println("sql:"+sql);
        Query query=entityManager.createQuery(sql);
        for(int i=0;i<fieldList.size();i++) {
            query.setParameter(i+1, params.get(fieldList.get(i)));
        }
        query.setFirstResult((page-1)*size);
        query.setMaxResults(size);
        List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> findByField(Class<T> clazz, Map<String, Object> params) {
        String sql="from "+clazz.getSimpleName()+" u WHERE ";
        Set<String> set=null;
        set=params.keySet();
        List<String> list=new ArrayList<String>(set);
        List<Object> fieldList=new ArrayList<Object>();
        int sqlcount=1;
        for(String field:list) {
            sql+="u."+field+"=?"+sqlcount+" and ";
            fieldList.add(field);
            sqlcount++;
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println("sql:"+sql);
        Query query=entityManager.createQuery(sql);
        for(int i=0;i<fieldList.size();i++) {
            query.setParameter(i+1, params.get(fieldList.get(i)));
        }
        List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> findByPages(Class<T> clazz,int page,int size){
        Query query = entityManager.createQuery("from " +clazz.getSimpleName());
        query.setFirstResult((page-1)*size);
        query.setMaxResults(size);
        List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public int count() {
        Long num = (Long) entityManager.createQuery("select count(*) from " + clazz.getSimpleName()).getSingleResult();
        return num.intValue();
    }

    @Override
    public int count(String jpql, Object... obj) {
        try {
            Query query = entityManager.createQuery(jpql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    query.setParameter((i + 1), obj[i]);
                }
            }
            Long num = (Long) query.getSingleResult();
            return num.intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Long countByFields(Class<T> clazz, Map<String, Object> params) {
        String sql="from "+clazz.getSimpleName()+" u WHERE ";
        Set<String> set=null;
        set=params.keySet();
        List<String> list=new ArrayList<String>(set);
        List<Object> fieldList=new ArrayList<Object>();
        for(String field:list) {
            sql+="u."+field+"=? and ";
            fieldList.add(field);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println("sql:"+sql);
        Query query=entityManager.createQuery(sql);
        for(int i=0;i<fieldList.size();i++) {
            query.setParameter(i+1, params.get(fieldList.get(i)));
        }
        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.longValue();
    }

    @Override
    public List<Object[]> findBySql(String sql) {
        Query q = entityManager.createNativeQuery(sql);
        List<Object[]> results = q.getResultList();
        return results;
    }
    @Override
    public List<Object[]> findBySql(String sql, int page, int size) {
        Query q = entityManager.createNativeQuery(sql);
        List<Object[]> results = q.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
        return results;
    }
    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params) {
        Query q = entityManager.createNativeQuery(sql);
        setQueryParameter(q, params);
        List<Object[]> results = q.getResultList();
        return results;
    }
    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int size) {
        Query q = entityManager.createNativeQuery(sql);
        setQueryParameter(q, params);
        List<Object[]> results = q.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
        return results;
    }

    @Override
    public int executeSql(String sql) {
        Query q = entityManager.createNativeQuery(sql);
        int num = q.executeUpdate();
        return num;
    }
    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        Query q = entityManager.createNativeQuery(sql);
        setQueryParameter(q, params);
        int num = q.executeUpdate();
        return num;
    }
    @Override
    public Long countBySql(String sql) {
        Query q = entityManager.createNativeQuery(sql);
        BigDecimal count = (BigDecimal) q.getSingleResult();
        return count.longValue();
    }
    @Override
    public Long countBySql(String sql, Map<String, Object> params) {
        Query q = entityManager.createNativeQuery(sql);
        setQueryParameter(q, params);
        BigDecimal count = (BigDecimal) q.getSingleResult();
        return count.longValue();
    }


    @Override
    public int executeUpdate(String jpql, Object... obj) {
        Query query = entityManager.createQuery(jpql);
        if (obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                query.setParameter((i + 1), obj[i]);
            }
        }
        return query.executeUpdate();
    }

    @Override
    public T load(String jpql, Object... obj) {
        try {
            Query query = entityManager.createQuery(jpql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    query.setParameter((i + 1), obj[i]);
                }
            }
            return (T) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<T> find(String jpql, Object... obj) {
        try {
            Query query = entityManager.createQuery(jpql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    query.setParameter((i + 1), obj[i]);
                }
            }
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object findByAggregate(String jpql, Object... obj) {
        Query query = entityManager.createQuery(jpql);
        if (obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                query.setParameter((i + 1), obj[i]);
            }
        }
        Object result = query.getSingleResult();
        return result;
    }



    @Override
    public List<T> findPage(Integer firstIndex, Integer maxResults) {
        return entityManager.createQuery("from " + clazz.getSimpleName()).setFirstResult(firstIndex).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<T> findPage(Integer firstIndex, Integer maxResults, String jpql, Object... obj) {
        try {
            Query query = entityManager.createQuery(jpql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    query.setParameter((i + 1), obj[i]);
                }
            }
            query.setFirstResult(firstIndex).setMaxResults(maxResults);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 自定义分页
     */
//    @Override
//    public Page<T> findPage(Page page, String jpql, Object... obj) {
//        Query query = entityManager.createQuery(jpql);
//        if (obj.length > 0) {
//            for (int i = 0; i < obj.length; i++) {
//                query.setParameter((i + 1), obj[i]);
//            }
//        }
//        int total = ((Long) query.getSingleResult()).intValue();
//        page.setItemTotal(total);
//        query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageCount());
//        List<T> list = query.getResultList();
//        page.setItems(list);
//        return page;
//    }



    private void setQueryParameter(Query q, Map<String, Object> params) {
        if (null != params && !params.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                q.setParameter(key, value);
            }
        }
    }
}
