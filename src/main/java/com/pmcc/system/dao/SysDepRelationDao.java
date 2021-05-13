package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.system.model.SysDepRelation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysDepRelation <br>
 * @Description: TODO 组织机构关系
 * @Date: 2020/2/6 13:50 <br>
 * @Author: darkbf <br>
 * @Version: 1.0 <br>
 *     查询方法参考 https://www.jianshu.com/p/69fa02602904
 */
@Repository
public class SysDepRelationDao extends AbstractBaseDao<SysDepRelation, String> {

    /**
     * 根据机构ID获取在机构映射表的ID值
     * @param id
     * @return
     */
    public SysDepRelation getByDepID(String id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysDepRelation> criteria = cb.createQuery(SysDepRelation.class);
        Root<SysDepRelation> contactRoot = criteria.from(SysDepRelation.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("depID").get("id"), id));

        SysDepRelation sysDepRelation =new SysDepRelation();
        List<SysDepRelation> list = getSession().createQuery(criteria).getResultList();
        if(list != null){
            sysDepRelation = list.get(0);
        }
        return sysDepRelation;
    }

    /**
     * 根据父ID获取子集
     * @param parentID
     * @return
     */
    public List<SysDepRelation> getChildrens(String parentID){
        SysDepRelation sysDepRelation = getByDepID(parentID);
        //排除已删除机构
        List<SysDepRelation> list = new ArrayList<SysDepRelation>(sysDepRelation.getChildrens());
        List<SysDepRelation> sysDepRelationList = new ArrayList<>();
        for (SysDepRelation item:list) {
            if(item.getStatus() != CommonConstant.DEL_FLAG){
                sysDepRelationList.add(item);
            }
        }
        return sysDepRelationList;
    }

    /**
     * 根据父ID获取除根节点之外的机构关系列表
     * 不能查询映射条件不存在的对象
     *
     * @return
     */
    public List<SysDepRelation> getDepByPRID(String prID) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysDepRelation> criteria = cb.createQuery(SysDepRelation.class);
        Root<SysDepRelation> contactRoot = criteria.from(SysDepRelation.class);
        criteria.select(contactRoot);

//        List<Predicate> predicateList = new ArrayList<>();
//        predicateList.add(cb.equal(contactRoot.get("depParent").get("id"), prID));
//        predicateList.add(cb.between(contactRoot.get("createTime"), prID, prID));
//        predicateList.add(cb.like(contactRoot.get("groupName"), "%" + prID + "%"));
//
//        if (predicateList.size() > 0) {
//            Predicate[] predicates = new Predicate[predicateList.size()];
//            for (int i = 0; i < predicates.length; i++) {
//                predicates[i] = predicateList.get(i);
//            }
//            criteria.where(predicates);
//        }



        criteria.where(cb.and(
                cb.equal(contactRoot.get("depParent").get("id"), prID),
                cb.notEqual(contactRoot.get("status"), CommonConstant.DEL_FLAG) //排除删除状态
        ));


//        return entityManager
//                .unwrap(Session.class)
//                .createQuery(criteria).getResultList();

        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 获取根节点  根节点的父ID为-1的
     *
     * @return
     */
    public List<SysDepRelation> getDepSQLByPRID(String node) {
        Query query = entityManager
                .createNativeQuery("select * from sys_dep_relation where parent_id=?1 and status!=?2", SysDepRelation.class)
                .setParameter(1, node)
                .setParameter(2,CommonConstant.DEL_FLAG);  //2删除状态
        return query.getResultList();
    }

}
