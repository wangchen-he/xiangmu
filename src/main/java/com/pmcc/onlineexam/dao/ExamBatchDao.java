package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.system.common.CommonCode;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ExamBatchDao extends AbstractBaseDao<ExamBatch, String> {

    /**
     *
     * @Description: 定义场次分页查询
     * @param pageDto
     * @author: lzx
     * @Date: 2021/4/7 9:47
     */
    public PageDto<ExamBatch> getAllExamBatch(PageDto pageDto){

//        System.out.println("dao--当前页码："+pageDto.getPage());
        int page = pageDto.getPage(); //当前页码
        int size = pageDto.getSize(); //每页的数据行数
        int start = (page - 1) * size;


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Query query = entityManager
                //select * from exam_batch where del_flag !=?1 order by begin_date offset ?2 row fetch next ?3 row only
                //select * from exam_batch where del_flag !=?1 limit ?2,?3
                .createNativeQuery("select * from exam_batch where del_flag !=?1 order by begin_date offset ?2 row fetch next ?3 row only", ExamBatch.class)
                .setParameter(1, CommonConstant.DEL_FLAG)
                .setParameter(2, start)
                .setParameter(3, size);
        pageDto.setList(query.getResultList());
        return pageDto;
    }
    /**
     * 根据角色查询
     */
    public List<ExamBatch> getbatch(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamBatch> criteria = cb.createQuery(ExamBatch.class);
        Root<ExamBatch> contactRoot = criteria.from(ExamBatch.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.notEqual(contactRoot.get("del_flag"), CommonCode.DEL_FLAG)); //排除删除状态
        return getSession().createQuery(criteria).getResultList();
    }
}
