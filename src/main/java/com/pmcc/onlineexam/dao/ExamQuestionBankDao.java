package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.model.ExamQuestionBank;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：闫小明
 * @description：TODO
 * @date ：2021-05-17 16:45
 */
@Repository
public class ExamQuestionBankDao extends AbstractBaseDao<ExamQuestionBank, String> {

    public List<ExamQuestionBank> getall(){
       List<ExamQuestionBank> list=  entityManager.createNativeQuery("select * from exam_question_bank where status!=?1 ORDER BY chstatus ASC,created_time DESC",ExamQuestionBank.class)
         .setParameter(1, CommonCode.DEL_FLAG).getResultList();
        return list;
    }

    public void autopass(String list){
        entityManager.createNativeQuery("update exam_question_bank set chstatus=?1 where oid in ("+list+")")
                .setParameter(1,CommonCode.AUDITO).executeUpdate();
    }
    public void autono(String list){
        entityManager.createNativeQuery("update exam_question_bank set chstatus=?1 where oid in ("+list+")")
                .setParameter(1,CommonCode.NO_AUDITO).executeUpdate();
    }

    public void deletequ(String id ){
        entityManager.createNativeQuery("update  exam_question_bank set status=?1 where oid=?2")
                .setParameter(1,CommonCode.DEL_FLAG).setParameter(2,id).executeUpdate();
    }

    public List<ExamQuestionBank>  getlike(String data){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamQuestionBank> criteria = cb.createQuery(ExamQuestionBank.class);
        Root<ExamQuestionBank> contactRoot = criteria.from(ExamQuestionBank.class);
        criteria.select(contactRoot);

        Predicate s1= cb.like(contactRoot.get("test_content"),"%"+data+"%");
        Predicate s2= cb.like(contactRoot.get("optionsA"),"%"+data+"%");
        Predicate s3= cb.like(contactRoot.get("optionsB"),"%"+data+"%");

        criteria.where(cb.or(s1,s2,s3),cb.notEqual(contactRoot.get("status"),CommonCode.DEL_FLAG));
        List<ExamQuestionBank> resultList = getSession().createQuery(criteria).getResultList();
        return resultList;
    }
}
