/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.dao;


import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.model.ExamSessionPaper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ExamSessionPaperDao extends AbstractBaseDao<ExamSessionPaper, String> {

    public ExamSessionPaper findByExamSession(String examSession){

        System.out.println("Dao层："+examSession);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamSessionPaper> criteria = cb.createQuery(ExamSessionPaper.class);
        Root<ExamSessionPaper> contactRoot = criteria.from(ExamSessionPaper.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("examSession"), examSession));
        ExamSessionPaper examSessionPaper = getSession().createQuery(criteria).getSingleResult();
        return examSessionPaper;
    }

}
