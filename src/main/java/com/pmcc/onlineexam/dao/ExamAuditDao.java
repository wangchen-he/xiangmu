package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.common.CommonCode;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ExamAuditDao extends AbstractBaseDao<ExamAudit, String> {
    /**
     * @author:
     * @description: TODO
     * @date: 2021-05-17 16:38
     * @param data:"确认通过的用户列表"
     * @return
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
     * @author:a
     * @description: TODO
     * @date: 2021-05-17 16:38
     * @param id
     * @return
     */
    public void deleteid(String id) {
        int addDictInfo = entityManager.createNativeQuery("update exam_user set user_status=?1 where oid=?2")
                .setParameter(1, CommonCode.DEL_FLAG).setParameter(2, id).executeUpdate();
    }

    @Autowired
    GetUser user;
/**
 * @author:
 * @description: TODO
 * @date: 2021-05-17 16:38
 * @param
 * @return
 */
    public List<ExamAudit> getall() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamAudit> criteria = cb.createQuery(ExamAudit.class);
        Root<ExamAudit> contactRoot = criteria.from(ExamAudit.class);
        criteria.select(contactRoot);

        SysUser us= user.getUsername();
        criteria.where(cb.notEqual(contactRoot.get("user_status"), CommonCode.DEL_FLAG));

        List<Order> orderList = new ArrayList();
        orderList.add(cb.asc(contactRoot.get("state")));
        orderList.add(cb.desc(contactRoot.get("created_time")));
        criteria.where(cb.equal(contactRoot.get("depid"),us.getDeptID()));
        criteria.orderBy(orderList);
        List<ExamAudit> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
//           Query all=entityManager.createNativeQuery("select * from exam_user where user_status=?1").setParameter(1,CommonCode.STATUS_NORMAL);
//           ExamAudit.

    }
public  List<ExamAudit> getfild(String id){

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ExamAudit> criteria = cb.createQuery(ExamAudit.class);
    Root<ExamAudit> contactRoot = criteria.from(ExamAudit.class);
    criteria.select(contactRoot);




    List<Order> orderList = new ArrayList();
    orderList.add(cb.asc(contactRoot.get("state")));
    orderList.add(cb.desc(contactRoot.get("created_time")));
    criteria.where(cb.notEqual(contactRoot.get("user_status"),CommonCode.DEL_FLAG),cb.equal(contactRoot.get("depid"),id));
    criteria.orderBy(orderList);
    List<ExamAudit> resultList = getSession().createQuery(criteria).getResultList();

    return resultList;
}
}
