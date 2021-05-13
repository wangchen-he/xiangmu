package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysRoleMenu;
import com.pmcc.system.model.SysRoleUser;
import com.pmcc.system.model.SysUser;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SysRoleUserDao <br>
 * @Description: TODO角色用户对照表
 * @Date: 2020/12/26 11:22 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysRoleUserDao extends AbstractBaseDao<SysRoleUser, String> {

    /**
     * 根据角色获取对应用户
     * @param roleID
     * @return
     */
    public List<SysRoleUser> getUsersByRoleID(String roleID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysRoleUser> criteria = cb.createQuery(SysRoleUser.class);
        Root<SysRoleUser> contactRoot = criteria.from(SysRoleUser.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("role").get("id"), roleID));
        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 根据用户获取角色列表
     * @param userID
     * @return
     */
    public List<SysRoleUser> getRoleByUserID(String userID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysRoleUser> criteria = cb.createQuery(SysRoleUser.class);
        Root<SysRoleUser> contactRoot = criteria.from(SysRoleUser.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("user").get("id"), userID));
        return getSession().createQuery(criteria).getResultList();
    }

    /**
     * 根据用户名查询  （鉴权登录使用）
     * @return
     */
    public List<String> userRole(String uID){
        Query query = entityManager
                .createNativeQuery(
                        "SELECT r.e_name FROM sys_role_user u " +
                                "LEFT JOIN  sys_role r ON r.oid = u.role_id " +
                                "where u.user_id =?1").setParameter(1, uID);
        List<String> result = query.getResultList();
//                .stream()
//                .map(item -> item.)
//                .collect(Collectors.toList());;

//        List<String> result = getSession().createQuery(criteria)
//                .getResultList()
//                .stream()
//                .map(item -> item.getRole().geteName())
//                .collect(Collectors.toList());
        return result;
    }
}
