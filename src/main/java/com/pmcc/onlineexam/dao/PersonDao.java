/**
 * @ClassName: PersonDao
 * @Description:
 * @Author: fangqian
 * @Date: 2021/4/6 16:00
 */


package com.pmcc.onlineexam.dao;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.system.model.SysUser;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDao extends AbstractBaseDao<Person, String> {

    public List<Person> findByIdno(String idno) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> contactRoot = criteria.from(Person.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("idno"), idno));
//        criteria.orderBy(cb.desc(contactRoot.get("creatorTime")));
        List<Person> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }







    /**
     * 按条件查询离退休人员信息
     *
     * @param personname 姓名
     * @param sex        性别
     * @param idtype     证件类型
     * @param idno       证件号码
     * @param race       民族
     * @param subject    工种
     * @return
     */

    /**
     * 根据机构ID获取考生列表
     * @return
     */
    public List<Person> getPersonByDep(String deptID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //查询结果所需要的类型(Entity相对应)
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        //查询所需要的主体类（Entity0相对应）
        Root<Person> contactRoot = criteria.from(Person.class);
        //查询结果-select（此处查询所有符合条件的主体类）
        criteria.select(contactRoot);

        //组件查询方法
        List<Predicate> predicateList = new ArrayList<>();
        if(deptID.length() > 0){
            predicateList.add(cb.equal(contactRoot.get("deptID"),deptID));   //根据机构ID查用户
        }
        predicateList.add(cb.notEqual(contactRoot.get("delflag"), CommonConstant.DEL_FLAG)); //排除删除状态

        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            for (int i = 0; i < predicates.length; i++) {
                predicates[i] = predicateList.get(i);
            }
            criteria.where(predicates);
            criteria.orderBy(cb.asc(contactRoot.get("idno")));
        }
        return getSession().createQuery(criteria).getResultList();
    }
//    public List<Person> getPersonByDep(String depID){
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
//        Root<Person> contactRoot = criteria.from(Person.class);
//        criteria.select(contactRoot);
//
//        //组件查询方法
//        List<Predicate> predicateList = new ArrayList<>();
//        if(depID.length() > 0){
//            predicateList.add(cb.equal(contactRoot.get("deptID"),depID));   //根据机构ID查用户
//        }
//        predicateList.add(cb.notEqual(contactRoot.get("delflag"), CommonConstant.DEL_FLAG)); //排除删除状态
//
//        if (predicateList.size() > 0) {
//            Predicate[] predicates = new Predicate[predicateList.size()];
//            for (int i = 0; i < predicates.length; i++) {
//                predicates[i] = predicateList.get(i);
//            }
//            criteria.where(predicates);
//            criteria.orderBy(cb.asc(contactRoot.get("idno")));
//        }
//        return getSession().createQuery(criteria).getResultList();
//    }
    /**
     * 根据用户组织机构，查询所在机构离退休人员
     *
     * @param depID
     * @return
     */
//    public List<Person> getByDepID(String depID) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
//        Root<Person> contactRoot = criteria.from(Person.class);
//        criteria.select(contactRoot);
//
//        criteria.where(cb.equal(contactRoot.get("depID"), depID));
//        criteria.orderBy(cb.desc(contactRoot.get("creatorTime")));
//        List<Person> resultList = getSession().createQuery(criteria).getResultList();
//
//        return resultList;
//    }
    /**
     * 根据离退休人员审核状态查询
     *
     * @param auditStatus
     * @return
     */
    public List<Person> getByAuditStatus(String auditStatus) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> contactRoot = criteria.from(Person.class);
        criteria.select(contactRoot);

        criteria.where(cb.equal(contactRoot.get("auditStatus"), auditStatus));
        criteria.orderBy(cb.desc(contactRoot.get("creatorTime")));
        List<Person> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }
    /**
     * 根据离退休人员审核状态查询
     *
     * @param auditStatus
     * @param depID
     * @return
     */
    public List<Person> getByAuditStatus(String depID,String auditStatus) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> contactRoot = criteria.from(Person.class);
        criteria.select(contactRoot);

        criteria.where(cb.and(cb.equal(contactRoot.get("depID"), depID),cb.equal(contactRoot.get("auditStatus"), auditStatus) ));
        criteria.orderBy(cb.desc(contactRoot.get("creatorTime")));
        List<Person> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }


    /**
     * 按条件查询离退休人员信息
     *
     * @param personname 姓名
     * @param sex        性别

     * @param idno       证件号码
     * @param race       民族
     * @param subject    工种
     * @return
     */


    public List<Person> conditionQuery(String personname, String sex, String idno, String race, String subject) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> contactRoot = criteria.from(Person.class);
        criteria.select(contactRoot);

        List<Predicate> predicateList = new ArrayList<>();
        if (!personname.equals("")) {
            predicateList.add(cb.like(contactRoot.get("personname"), "%" + personname + "%"));
        }
        if (!sex.equals("")) {
            predicateList.add(cb.like(contactRoot.get("sex"), "%" + sex + "%"));
        }
        if (!idno.equals("")) {
            predicateList.add(cb.like(contactRoot.get("idno"), "%" + idno + "%"));
        }
        if (!race.equals("")) {
            predicateList.add(cb.like(contactRoot.get("race"), "%" + race + "%"));
        }
//        if (!auditstatus.equals("")) {
//            predicateList.add(cb.like(contactRoot.get("auditstatus"), "%" + auditstatus + "%"));
//        }
        if (!subject .equals("")) {
            predicateList.add(cb.like(contactRoot.get("subject"), "%" + subject + "%"));
        }
        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            for (int i = 0; i < predicates.length; i++) {
                predicates[i] = predicateList.get(i);
            }
            criteria.where(predicates);
        }
        criteria.orderBy(cb.desc(contactRoot.get("idno")));
        List<Person> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }
}



