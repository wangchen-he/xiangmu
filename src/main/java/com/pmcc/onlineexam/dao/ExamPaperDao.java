package com.pmcc.onlineexam.dao;


import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamPaper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * @ClassName: ExamPaperDao
 * @Description:定义分页信息分页查询
 * @Author: 94105
 * @Date: 2021/4/9 16:14
 */


@Repository
public class ExamPaperDao extends AbstractBaseDao<ExamPaper,String> {
    public PageDto<ExamPaper> getAllExamPaper(PageDto pageDto){

        System.out.println("dao--当前页码："+pageDto.getPage());
        int page = pageDto.getPage(); //当前页码
        int size = pageDto.getSize(); //每页的数据行数
        int start = (page - 1) * size;


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Query query = entityManager.createNativeQuery("select * from exam_paper order by total_score offset ?1 row fetch next ?2 row only", ExamPaper.class)
                .setParameter(1,start)
                .setParameter(2,size);
        pageDto.setList(query.getResultList());
        return pageDto;
    }
}

