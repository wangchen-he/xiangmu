package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysRole;
import com.pmcc.system.model.SysRoleMenu;
import com.pmcc.system.common.CommonCode;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName: SysRoleMenu <br>
 * @Description: TODO角色菜单对照表
 * @Date: 2020/12/26 11:20 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysRoleMenuDao extends AbstractBaseDao<SysRoleMenu, String> {

    /**
     * 根据角色获取对应菜单
     * @param roleID
     * @return
     */
    public List<SysRoleMenu> getMenusByRoleID(String roleID){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysRoleMenu> criteria = cb.createQuery(SysRoleMenu.class);
        Root<SysRoleMenu> contactRoot = criteria.from(SysRoleMenu.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("role").get("id"), roleID));
        return getSession().createQuery(criteria).getResultList();
    }

}
