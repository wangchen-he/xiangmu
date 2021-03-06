/**
 * Created with IntelliJ IDEA.
 *
 * @date： 2021/5/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.system.common.CommonCode;
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
                .setParameter(1, CommonCode.DEL_FLAG)
                .setParameter(2, start)
                .setParameter(3, size);
        pageDto.setList(query.getResultList());
        return pageDto;
    }

    public List<ExamSession> findBySessionId(String batchId){  //TODO 场次数据查询SQL
//        Query query = entityManager.createNativeQuery("select * from exam_session where del_flag != ?1 and batch_id = ?2 order by sort_no", ExamSession.class)
//                .setParameter(1, CommonCode.DEL_FLAG)
//                .setParameter(2, batchId);
        Query query = entityManager.createNativeQuery("select * from exam_session where del_flag != ?1 and batch_id = ?2", ExamSession.class)
                .setParameter(1, CommonCode.DEL_FLAG)
                .setParameter(2, batchId);

        List<ExamSession> resultList = query.getResultList();
        return resultList;
    }
}
