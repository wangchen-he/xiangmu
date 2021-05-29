package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.onlineexam.utils.GetUser;

import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class ExamAuditDao extends AbstractBaseDao<ExamAudit, String> {
    /**
     * @param data:"确认通过的用户列表"
     * @return
     * @author:
     * @description: TODO
     * @date: 2021-05-17 16:38
     */
    public void userauditpass(String data) {


        String val = GetUser.getStringli(data);


        Query addDictInfo = entityManager.createNativeQuery("update exam_user set state=1 where oid in (" + val + ")");
        addDictInfo.executeUpdate();

//           Query addDictInfo = entityManager.createNativeQuery(
//                   "select * from exam_audit where oid in (:data1)", ExamAudit.class).setParameter("data1","1,2,3");
//
//           List<ExamAudit> resultList = addDictInfo.getResultList();
//           resultList.forEach(System.out::println);
    }

    public void userdictno(String data) {
        String val = GetUser.getStringli(data);
        Query addDictInfo = entityManager.createNativeQuery("update exam_user set state=2 where oid in (" + val + ")");
        addDictInfo.executeUpdate();

    }

    /**
     * @param id
     * @return
     * @author:a
     * @description: TODO
     * @date: 2021-05-17 16:38
     */
    public void deleteid(String id) {
        int addDictInfo = entityManager.createNativeQuery("update exam_user set user_status=?1 where oid=?2")
                .setParameter(1, CommonCode.DEL_FLAG).setParameter(2, id).executeUpdate();
    }

    @Autowired
    GetUser user;

    /**
     * @param
     * @return
     * @author:
     * @description: TODO
     * @date: 2021-05-17 16:38
     */
    public List<ExamAudit> getall() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamAudit> criteria = cb.createQuery(ExamAudit.class);
        Root<ExamAudit> contactRoot = criteria.from(ExamAudit.class);
        criteria.select(contactRoot);

        SysUser us = user.getUsername();

        //排序
        List<Order> orderList = new ArrayList();
        orderList.add(cb.asc(contactRoot.get("state")));
        orderList.add(cb.desc(contactRoot.get("created_time")));
        //条件
        Predicate s1 = cb.notEqual(contactRoot.get("user_status"), CommonCode.DEL_FLAG);
        Predicate s2 = cb.equal(contactRoot.get("depid"), us.getDeptID());

        criteria.where(cb.and(s1, s2));
        criteria.orderBy(orderList);
        List<ExamAudit> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
//           Query all=entityManager.createNativeQuery("select * from exam_user where user_status=?1").setParameter(1,CommonCode.STATUS_NORMAL);
//           ExamAudit.

    }

    @Autowired
    GetUser getUser;

    public List<ExamAudit> getfild(String id) {
        SysUser user = getUser.getUsername();
        if (!CommonCode.adminid.equals(user.getDeptID()) && !user.getDeptID().equals(id)) {
            List<ExamAudit> list = new ArrayList<>();
            return list;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamAudit> criteria = cb.createQuery(ExamAudit.class);
        Root<ExamAudit> contactRoot = criteria.from(ExamAudit.class);
        criteria.select(contactRoot);


        List<Order> orderList = new ArrayList();
        orderList.add(cb.asc(contactRoot.get("state")));
        orderList.add(cb.desc(contactRoot.get("created_time")));
        criteria.where(cb.notEqual(contactRoot.get("user_status"), CommonCode.DEL_FLAG), cb.equal(contactRoot.get("depid"), id));
        criteria.orderBy(orderList);
        List<ExamAudit> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }


    public List<ExamAudit> getcondition(String params) {
        SysUser user=getUser.getUsername();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamAudit> criteria = cb.createQuery(ExamAudit.class);
        Root<ExamAudit> contactRoot = criteria.from(ExamAudit.class);
        criteria.select(contactRoot);

        Predicate s1=cb.like(contactRoot.get("name"),"%"+params+"%");
        Predicate s2=cb.equal(contactRoot.get("depid"),user.getDeptID());
        if(CommonCode.adminid.equals(user.getDeptID())){
             criteria.where(s1);

        }else {
            criteria.where(cb.and(s1,s2));
        }

        List<ExamAudit> resultList = getSession().createQuery(criteria).getResultList();
        return resultList;
    }
}
