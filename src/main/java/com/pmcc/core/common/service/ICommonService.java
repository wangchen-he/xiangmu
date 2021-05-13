package com.pmcc.core.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ICommonService <br>
 * @Description: TODO 泛型service接口
 * @Date: 2019/12/14 19:30 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public interface ICommonService<T, PK extends Serializable> {

    /*************** 保存 ****************************/
    T save(T entity);

    /*************** 删除 ****************************/
    boolean delete(T entity);
    boolean deleteByID(PK id);
    int deleteListEntitys(List<T> entitys);

    /*************** 更新 ****************************/
    T update(T entity);

    /*************** 查找 ****************************/
    List<T> findAll();
    T findById(PK id);
    List<T> findByField(Map<String, Object> params);

    /*************** 分页 ****************************/
    List<T> findByFieldAndPages(Class<T> clazz, Map<String, Object> params, int page, int size);

    /*************** 统计 ****************************/
    int count();
}
