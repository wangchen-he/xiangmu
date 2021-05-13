package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.system.common.CommonCode;
import com.pmcc.system.model.SysUser;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysUserDao <br>
 * @Description: 用户
 * @Date: 2019/11/14 14:29 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysUserDao extends AbstractBaseDao<SysUser, String> {

    /**
     * 根据机构ID获取用户列表
     * @return
     */
    public List<SysUser> getUserByDep(String depID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUser> criteria = cb.createQuery(SysUser.class);
        Root<SysUser> contactRoot = criteria.from(SysUser.class);
        criteria.select(contactRoot);

        //组件查询方法
        List<Predicate> predicateList = new ArrayList<>();
        if(depID.length() > 0){
            predicateList.add(cb.equal(contactRoot.get("deptID"),depID));   //根据机构ID查用户
        }
        predicateList.add(cb.notEqual(contactRoot.get("status"), CommonConstant.DEL_FLAG)); //排除删除状态

        if (predicateList.size() > 0) {
            Predicate[] predicates = new Predicate[predicateList.size()];
            for (int i = 0; i < predicates.length; i++) {
                predicates[i] = predicateList.get(i);
            }
            criteria.where(predicates);
            criteria.orderBy(cb.asc(contactRoot.get("sort")));
        }
        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 根据用户英文名获取用户列表
     * @return
     */
    public List<SysUser> getUserByEName(String eName){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUser> criteria = cb.createQuery(SysUser.class);
        Root<SysUser> contactRoot = criteria.from(SysUser.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.and(
                cb.equal(contactRoot.get("userEName"),eName),     //根据机构ID查用户
                cb.notEqual(contactRoot.get("status"), CommonCode.DEL_FLAG)  //排除删除状态
        ));
        criteria.orderBy(cb.asc(contactRoot.get("sort")));
        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 根据用户名查询  （鉴权登录使用）
     * @return
     */
    public SysUser getUserByName(String name){
        Query query = entityManager
                .createNativeQuery("select * from sys_user where user_ename=?1 and status !=?2", SysUser.class)
                .setParameter(1, name)
                .setParameter(2, CommonConstant.DEL_FLAG);
        List<SysUser> result = query.getResultList();
        SysUser sysUser = null;
        if(result.size() >0){
            sysUser = result.get(0);
        }
        entityManager.close();
        return sysUser;
    }

    public List<SysUser> getByDid(String deptID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUser> criteria = cb.createQuery(SysUser.class);
        Root<SysUser> contactRoot = criteria.from(SysUser.class);
        criteria.select(contactRoot);

        criteria.where(cb.equal(contactRoot.get("deptID"), deptID));
        List<SysUser> resultList = getSession().createQuery(criteria).getResultList();

        return resultList;
    }
}
