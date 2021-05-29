package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.system.common.CommonCode;
import com.pmcc.system.model.SysDepRelation;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
                .createNativeQuery("select * from exam_batch where del_flag !=?1 order by begin_time offset ?2 row fetch next ?3 row only", ExamBatch.class)
                .setParameter(2, CommonCode.DEL_FLAG)
                .setParameter(2, start)
                .setParameter(3, size);
        pageDto.setList(query.getResultList());
        return pageDto;
    }
    /**
     * 根据角色查询
     */
    public List<ExamBatch> Getbatch(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamBatch> criteria = cb.createQuery(ExamBatch.class);
        Root<ExamBatch> contactRoot = criteria.from(ExamBatch.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.notEqual(contactRoot.get("delFlag"), CommonCode.DEL_FLAG)); //排除删除状态
        return getSession().createQuery(criteria).getResultList();
    }
    /**
     * 查询
     */
    public  List<ExamBatch> conditionQuery(String name, String sessionType, Date testDatestare,Date testDateend,Date beginTimestare,Date beginTimeend,Date endTimestare,Date endTimeend) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamBatch> criteria = cb.createQuery(ExamBatch.class);
        Root<ExamBatch> contactRoot = criteria.from(ExamBatch.class);
        criteria.select(contactRoot);

        List<Predicate> predicateList = new ArrayList<>();
        if (!name.equals("")) {
            predicateList.add(cb.like(contactRoot.get("name"), "%" + name + "%"));
        }
        if (!sessionType.equals("")) {
            predicateList.add(cb.like(contactRoot.get("sessionType"), "%" + sessionType + "%"));
        }
        if (testDatestare != null && testDateend != null) {
            predicateList.add(cb.between(contactRoot.get("testDate"), testDatestare,testDateend));
        }
        if (beginTimestare != null && beginTimeend != null) {
            predicateList.add(cb.between(contactRoot.get("beginTime"), beginTimestare,beginTimeend));
        }
        if (endTimestare != null && endTimeend != null) {
            predicateList.add(cb.between(contactRoot.get("endTime"), endTimestare,endTimeend));
        }
        predicateList.add(cb.notEqual(contactRoot.get("delFlag"), CommonCode.DEL_FLAG));
        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            for (int i = 0; i < predicates.length; i++) {
                predicates[i] = predicateList.get(i);
            }
            criteria.where(predicates);
        }
        List<ExamBatch> resultList = getSession().createQuery(criteria).getResultList();
        return resultList;
    }
}
