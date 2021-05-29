package com.pmcc.onlineexam.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.model.Person;

import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * @ClassName: DictDao
 * @Description: 对数据表exam_dict进行查询操作
 * @Author: zky
 * @Date: 2021/4/6 15:39
 */

@Repository
public class DictDao extends AbstractBaseDao<Dict, String> {

    public void deleteid(String id){
        entityManager.createNativeQuery("update exam_dict set status=?1 where oid=?2")
                .setParameter(1,CommonCode.DEL_FLAG)
                .setParameter(2,id).executeUpdate();

    }


  public List<Dict> getall(){
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Dict> criteria = cb.createQuery(Dict.class);
      Root<Dict> contactRoot = criteria.from(Dict.class);
      criteria.select(contactRoot);
      Predicate s1=cb.notEqual(contactRoot.get("status"), CommonCode.NO_AUDITO);


      criteria.where(s1);
      criteria.orderBy(cb.asc(contactRoot.get("sort")));

      List<Dict> resultList = getSession().createQuery(criteria).getResultList();
      return resultList;
  }


    public List<Dict> getdict(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dict> criteria = cb.createQuery(Dict.class);
        Root<Dict> contactRoot = criteria.from(Dict.class);
        criteria.select(contactRoot);
        Predicate s1=cb.equal(contactRoot.get("status"), CommonCode.STATUS_NORMAL);


        criteria.where(s1);
        criteria.orderBy(cb.asc(contactRoot.get("sort")));

        List<Dict> resultList = getSession().createQuery(criteria).getResultList();
        return resultList;
    }


    /**
     * @author: 模糊查询
     * @description: TODO
     * @date: 2021-05-29 10:50
     * @param “条件
     * @return
     */

    public  List<Dict> getlike(String like){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dict> criteria = cb.createQuery(Dict.class);
        Root<Dict> contactRoot = criteria.from(Dict.class);
        criteria.select(contactRoot);

        Predicate s1=  cb.like(contactRoot.get("name"),"%"+like+"%");
        Predicate s2=  cb.like(contactRoot.get("value"),"%"+like+"%");

        Predicate s3=cb.notEqual(contactRoot.get("status"),CommonCode.DEL_FLAG);
        criteria.where(cb.or(s1,s2),s3);


        criteria.orderBy(cb.asc(contactRoot.get("sort")));
      return   getSession().createQuery(criteria).getResultList();
    }
  /**
     *
     * @Description ：查询exam_dict中的工种数据
     * @param pageDto
     * @return com.pmcc.exam.entity.PageDto<com.pmcc.exam.model.Dict>
     * @author zky
     * @Date 2021/4/7 9:35
     */
    public PageDto<Dict> getAllDict(PageDto pageDto){
//        getAllExamBatch(PageDto pageDto){
        System.out.println("dao--当前页码："+pageDto.getPage());
        int page = pageDto.getPage(); //当前页码
        int size = pageDto.getSize(); //每页的数据行数
        int start = (page - 1) * size;


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Query addDictInfo = entityManager.createNativeQuery(
                "select * from exam_dict where status =0 order by dictid offset ?1 row fetch next ?2 row only",
                Dict.class)                    //从exam_dict中获取status为0的并按照dictid来进行排序工种数据
                .setParameter(1, start)      //定义从第一页开始
                .setParameter(2, size);      //定义每一页返回多少条数据
        pageDto.setList(addDictInfo.getResultList());
        return pageDto;
    }



    /**
     *
     * @Description:  根据dicttype获取dictname列表
     * @param dictType
     * @author: lzx
     * @Date: 2021/4/14 9:47
     */
    public List<Dict> findByDictType(String dictType){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Query addDictInfo = entityManager.createNativeQuery(
                "select * from exam_dict where dicttype=?1 order by dictid", Dict.class)
                .setParameter(1, dictType);
        List<Dict> resultList = addDictInfo.getResultList();
        return resultList;
    }

}
