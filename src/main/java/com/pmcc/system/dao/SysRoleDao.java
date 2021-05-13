package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysRole;
import com.pmcc.system.common.CommonCode;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName: SysRoleDao <br>
 * @Description: TODO角色表
 * @Date: 2020/12/26 11:17 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysRoleDao extends AbstractBaseDao<SysRole, String> {


    /**
     * 根据角色查询
     */
    public List<SysRole> getRole(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysRole> criteria = cb.createQuery(SysRole.class);
        Root<SysRole> contactRoot = criteria.from(SysRole.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.notEqual(contactRoot.get("status"), CommonCode.DEL_FLAG)); //排除删除状态
        criteria.orderBy(cb.asc(contactRoot.get("sort")));
        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 根据角色查询
     */
    public List<SysRole> getRoleByName(String roleName){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysRole> criteria = cb.createQuery(SysRole.class);
        Root<SysRole> contactRoot = criteria.from(SysRole.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.equal(contactRoot.get("name"), roleName));
        return getSession().createQuery(criteria).getResultList();
    }

}
