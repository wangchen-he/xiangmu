package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.system.model.SysUreport;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Classname SysUreportDao
 * @Description TODO
 * @Date 2021/1/5 16:52
 * @Created by yanglei
 * @Version 1.0
 */
@Repository
public class SysUreportDao extends AbstractBaseDao<SysUreport, String> {


    public List<SysUreport> findUAll(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUreport> criteria = cb.createQuery(SysUreport.class);
        Root<SysUreport> contactRoot = criteria.from(SysUreport.class);
        criteria.select(contactRoot);

        List<SysUreport> list = getSession().createQuery(criteria).getResultList();

        return list;

    }


    public SysUreport findByName(String ureportName) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUreport> criteria = cb.createQuery(SysUreport.class);
        Root<SysUreport> contactRoot = criteria.from(SysUreport.class);
        criteria.select(contactRoot);

        criteria.where(cb.equal(contactRoot.get("ureportName"), ureportName ));

        List<SysUreport> resultList = getSession().createQuery(criteria).getResultList();
        SysUreport sysUreport =new SysUreport();
        if(resultList.size() > 0){

            sysUreport= resultList.get(0);
        }else {
            sysUreport =null;
        }
        return sysUreport;
    }
}
