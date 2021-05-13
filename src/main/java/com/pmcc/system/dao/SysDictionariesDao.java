package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysDictionaries;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysDictionariesDao <br>
 * @Description:
 * @Date: 2021/1/22 16:42 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysDictionariesDao extends AbstractBaseDao<SysDictionaries, String> {

    /**
     * SQL获取各根节点
     *
     * @param parentID   父级ID
     * @param deleteFlag 是否删除
     * @return
     */
    public List<SysDictionaries> getDictionariesRootNode(String parentID, String deleteFlag) {

        Query query = entityManager
                .createNativeQuery("select * from sys_dictionaries where parent_id = ?1 and delete_flag = ?2 ", SysDictionaries.class)
                .setParameter(1, parentID)
                .setParameter(2, deleteFlag);

        return query.getResultList();
    }

    /**
     * 根据表名、列名、父级ID、是否启用、是否删除获取节点，主要用于获取字典内指定根节点，根节点下的子集
     *
     * @param tableName  表名
     * @param fieldName  列名
     * @param parentID   父级ID
     * @param status     是否启用
     * @param deleteFlag 是否删除
     * @return
     */
    public List<SysDictionaries> getDictionariesByTableField(String tableName, String fieldName, String parentID, String status, String deleteFlag) {

        Query query = entityManager
                .createNativeQuery("select * from sys_dictionaries where table_name = ?1 and field_name = ?2 and parent_id = ?3 and status = ?4 and delete_flag = ?5 ", SysDictionaries.class)
                .setParameter(1, tableName)
                .setParameter(2, fieldName)
                .setParameter(3, parentID)
                .setParameter(4, status)
                .setParameter(5, deleteFlag);

        return query.getResultList();
    }

    /**
     * 根据父级ID获取子集
     *
     * @param parentID 父级ID
     * @return
     */
    public List<SysDictionaries> getSysDictionariesByParentID(String parentID, String deleteFlag) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysDictionaries> criteria = cb.createQuery(SysDictionaries.class);
        Root<SysDictionaries> contactRoot = criteria.from(SysDictionaries.class);
        criteria.select(contactRoot);
        criteria.where(cb.and(cb.equal(contactRoot.get("parentID").get("id"), parentID), cb.equal(contactRoot.get("deleteFlag"), deleteFlag)));

        List<SysDictionaries> resultList = getSession().createQuery(criteria).getResultList();
        return resultList;
    }

    /**
     * 根据父级ID获取子集
     *
     * @param parentID   父级ID
     * @param status     是否启用
     * @param deleteFlag 是否删除
     * @return
     */
    public List<SysDictionaries> getSysDictionariesByParentID(String parentID, String status, String deleteFlag) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysDictionaries> criteria = cb.createQuery(SysDictionaries.class);
        Root<SysDictionaries> contactRoot = criteria.from(SysDictionaries.class);
        criteria.select(contactRoot);
        List<Predicate> predicateList = new ArrayList<>();

        if (!parentID.equals("")) {
            predicateList.add(cb.equal(contactRoot.get("parentID").get("id"), parentID));
        }
        if (!status.equals("")) {
            predicateList.add(cb.equal(contactRoot.get("status"), status));
        }
        if (!deleteFlag.equals("")) {
            predicateList.add(cb.equal(contactRoot.get("deleteFlag"), deleteFlag));
        }

        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            for (int i = 0; i < predicates.length; i++) {
                predicates[i] = predicateList.get(i);
            }
            criteria.where(predicates);
        }

        criteria.orderBy(cb.asc(contactRoot.get("sort")));
        List<SysDictionaries> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }
}
