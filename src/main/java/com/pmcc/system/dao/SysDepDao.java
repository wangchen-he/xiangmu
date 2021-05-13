package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.common.CommonCode;
import com.pmcc.system.model.SysDep;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName: SysDepDao <br>
 * @Description: 组织机构
 * @Date: 2020/2/5 14:29 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysDepDao extends AbstractBaseDao<SysDep, String>{

    /**
     * 根据机构ID获取正常使用的机构列表
     * @param depID
     * @return
     */
    public List<SysDep> getDepByID(String depID){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysDep> criteria = cb.createQuery(SysDep.class);
        Root<SysDep> contactRoot = criteria.from(SysDep.class);
        criteria.select(contactRoot);
        //查询排除已删除数据
        criteria.where(cb.and(
                cb.equal(contactRoot.get("id"),depID),     //根据机构ID查用户
                cb.notEqual(contactRoot.get("status"), CommonCode.DEL_FLAG)  //排除删除状态
        ));
        criteria.orderBy(cb.asc(contactRoot.get("sort")));
        return getSession().createQuery(criteria).getResultList();
    }
}
