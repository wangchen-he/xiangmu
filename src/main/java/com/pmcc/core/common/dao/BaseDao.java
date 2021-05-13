package com.pmcc.core.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BaseDao <br>
 * @Description: 持久层接口
 * @Date: 2019/11/14 10:20 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public interface BaseDao<T, PK extends Serializable> {
    /**
     * 添加实体类
     *
     * @param entity 实体类
     * @return
     */
    T  add(T entity);

    /**
     *
     * 面向对象方式进行删除
     *
     * @param entity
     * @return 数量
     */
    boolean delete(T entity);
    /**
     * 根据主键ID删除实体类
     *
     * @param id
     */
    boolean deleteByID(Class<T> clazz,PK id);
    /**
     *
     * 面向对象方式进行删除
     *
     * @param entitys
     * @return 数量
     */
    int deleteListEntitys(List<T> entitys);
    /**
     *
     * 面向对象方式进行参数删除
     *
     * @param clazz 实体类的class
     * @param params
     * @return
     */
    int deleteByField(Class<T> clazz, Map<String, Object> params);

    /**
     * 更新实体类
     *
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * 根据主键ID查找单个实体类
     *
     * @param id
     * @return
     */
    T findById(Class<T> clazz,PK id);
    /**
     *
     * 面向对象方式进行全查
     *
     * @param clazz 实体类的class
     * @return 查询结果数据集合
     */
    List<T> findAll(Class<T> clazz);
    /**
     *
     * 面向对象方式进行带参数的分页查询
     *
     * @param clazz 实体类的class
     * @param params 参数集合
     * @param page 页码
     * @param size 每页条数
     * @return 查询结果数据集合
     */
    List<T> findByFieldAndPages(Class<T> clazz,Map<String, Object> params,int page,int size);

    /**
     *
     * 面向对象方式进行参数查询
     *
     * @param clazz 实体类的class
     * @param params 参数集合
     * @return
     */
    List<T> findByField(Class<T> clazz,Map<String, Object> params);

    /**
     *
     * 面向对象方式进行分页查询
     *
     * @param clazz 实体类的class
     * @param page 页码
     * @param size 每页条数
     * @return 查询结果数据集合
     */
    List<T> findByPages(Class<T> clazz,int page,int size);

    /**
     * 查找总记录数
     *
     * @return
     */
    int count();
    /**
     * 根据JPQL语句查询记录数
     *
     * @param jpql
     * @param obj
     * @return
     */
    int count(String jpql, Object... obj);
    /**
     *
     * 面向对象方式进行带参查询数据数量
     *
     * @param clazz
     * @param params
     * @return 数量
     */
    Long countByFields(Class<T> clazz,Map<String, Object> params);

    /**
     * 根据原生sql查询语句，查询数据集合
     *
     * @param sql 原生sql语句
     * @return 查询结果数据集合
     */
    List<Object[]> findBySql(String sql);
    /**
     * 根据原生sql查询语句，分页查询数据
     *
     * @param sql  原生sql语句
     * @param page 页码
     * @param size 每页条数
     * @return 查询结果数据集合
     */
    List<Object[]> findBySql(String sql, int page, int size);
    /**
     * 根据原生sql与参数查询语句，查询数据集合
     *
     * @param sql      原生sql语句
     * @param paramMap 参数集合
     * @return 查询结果数据集合
     */
    List<Object[]> findBySql(String sql, Map<String, Object> paramMap);
    /**
     * 根据原生sql与参数查询语句，分页查询数据
     *
     * @param sql      原生sql语句
     * @param paramMap 参数集合
     * @param page     页码
     * @param size     每页条数
     * @return 查询结果数据集合
     */
    List<Object[]> findBySql(String sql, Map<String, Object> paramMap, int page, int size);

    /**
     * 根据原生sql，插入或更新或删除数据
     *
     * @param sql
     * @return 更改条数
     */
    int executeSql(String sql);
    /**
     * 根据原生sql与参数，插入或更新或删除数据
     *
     * @param sql      原生sql
     * @param paramMap 参数集合
     * @return 更改条数
     */
    int executeSql(String sql, Map<String, Object> paramMap);
    /**
     * 根据原生sql，查询数量
     *
     * @param sql 原生sql
     * @return 数量
     */
    Long countBySql(String sql);
    /**
     * 根据原生sql与参数，查询数量
     *
     * @param sql      原生sql
     * @param paramMap 参数集合
     * @return 数量
     */
    Long countBySql(String sql, Map<String, Object> paramMap);

    /**
     * 根据JPQL语句更新或删除操作
     *
     * @param jpql
     * @param obj
     */
    int executeUpdate(String jpql, Object... obj);

    /**
     * 根据JPQL语句查询单个实体类
     *
     * @param jpql
     * @param obj（参数可有可无）
     * @return
     */
    T load(String jpql, Object... obj);


    /**
     * 根据JPQL语句查询集合实体类
     *
     * @param jpql
     * @param obj（参数可有可无）
     * @return
     */
    List<T> find(String jpql, Object... obj);

    /**
     * 聚合查询
     *
     * @param jpql
     * @param obj
     * @return
     */
    Object findByAggregate(String jpql, Object... obj);

    /**
     * 分页查询集合实体类
     *
     * @param firstIndex
     * @param maxResults
     * @return
     */
    List<T> findPage(Integer firstIndex, Integer maxResults);

    /**
     * 根据JPQL语句查询集合实体类
     *
     * @param firstIndex
     * @param maxResults
     * @param jpql
     * @param obj
     * @return
     */
    List<T> findPage(Integer firstIndex, Integer maxResults, String jpql, Object... obj);


/**
 * 自定义分页
 */
//    Page findPage(Page page, String jpql, Object... obj);

}

