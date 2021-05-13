package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysMenuRelation;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName: SysMenuRelationDao <br>
 * @Description: TODO导航关系表
 * @Date: 2020/10/15 15:19 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysMenuRelationDao extends AbstractBaseDao<SysMenuRelation, String> {

    /**
     * 根据菜单ID获取在机构映射表的ID值
     * @param id
     * @return
     */
    public SysMenuRelation getByMenuID(String id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysMenuRelation> criteria = cb.createQuery(SysMenuRelation.class);
        Root<SysMenuRelation> contactRoot = criteria.from(SysMenuRelation.class);
        criteria.select(contactRoot);
        criteria.where(cb.equal(contactRoot.get("menuID").get("id"), id));

        SysMenuRelation sysMenuRelation =new SysMenuRelation();
        List<SysMenuRelation> list = getSession().createQuery(criteria).getResultList();
        if(list != null){
            sysMenuRelation = list.get(0);
        }
        return sysMenuRelation;
    }
}
