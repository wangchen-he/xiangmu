package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.system.dao.SysUreportDao;
import com.pmcc.system.model.SysUreport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname SysUreportService
 * @Description TODO
 * @Date 2021/1/5 16:58
 * @Created by yanglei
 * @Version 1.0
 */
@Service
@Transactional
public class SysUreportService extends CommonServiceImpl<SysUreport,String> {

    @Autowired
    private SysUreportDao sysUreportDao;

    @Override
    protected AbstractBaseDao<SysUreport, String> getEntityDao() {
        return sysUreportDao;
    }

    public SysUreport findByName(String ureportName) {
        return sysUreportDao.findByName(ureportName);
    }

    public List<SysUreport> findUAll(){
        return sysUreportDao.findUAll();
    }

}
