package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ExamAuditDao extends AbstractBaseDao<ExamAudit, String> {

       public void userauditpass(String data){
           Query addDictInfo= entityManager.createNativeQuery("update exam_audit set state='1' where oid in ("+data+")");
           addDictInfo.executeUpdate();

//           Query addDictInfo = entityManager.createNativeQuery(
//                   "select * from exam_audit where oid in (:data1)", ExamAudit.class).setParameter("data1","1,2,3");
//
//           List<ExamAudit> resultList = addDictInfo.getResultList();
//           resultList.forEach(System.out::println);
       }
       public void userdictno(String data ){
           Query addDictInfo= entityManager.createNativeQuery("update exam_audit set state='2' where oid in ("+data+")");
           addDictInfo.executeUpdate();
       }




}
