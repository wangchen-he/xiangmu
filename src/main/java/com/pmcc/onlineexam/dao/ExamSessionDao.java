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
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class ExamSessionDao extends AbstractBaseDao<ExamSession, String> {

    public PageDto<ExamSession> findBySessionIdExamBatch(PageDto pageDto){

        System.out.println("dao--当前页码："+pageDto.getPage());
        int page = pageDto.getPage(); //当前页码
        int size = pageDto.getSize(); //每页的数据行数
        int start = (page - 1) * size;


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Query query = entityManager
                //select * from exam_batch where del_flag !=?1 order by begin_date offset ?2 row fetch next ?3 row only
                //select * from exam_batch where del_flag !=?1 limit ?2,?3
                .createNativeQuery("select * from exam_session where del_flag !=?1 order by sort_no offset ?2 row fetch next ?3 row only", ExamSession.class)
                .setParameter(1, CommonConstant.DEL_FLAG)
                .setParameter(2, start)
                .setParameter(3, size);
        pageDto.setList(query.getResultList());
        return pageDto;
    }

    public List<ExamSession> findBySessionId(String sessionId){
        Query query = entityManager.createNativeQuery("select * from exam_session where del_flag != ?1 and session_id = ?2 order by sort_no", ExamSession.class)
                .setParameter(1, CommonConstant.DEL_FLAG)
                .setParameter(2, sessionId);

        List<ExamSession> resultList = query.getResultList();
        return resultList;
    }
}
