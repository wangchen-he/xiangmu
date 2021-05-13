package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.system.dao.SysAclDao;
import com.pmcc.system.model.SysAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SysAclDao <br>
 * @Description: TODO权限基础表
 * @Date: 2020/12/26 11:30 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysAclService extends CommonServiceImpl<SysAcl, String> {

    @Autowired
    private SysAclDao sysAclDao;

    @Override
    protected AbstractBaseDao<SysAcl, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysAclDao;
    }
}
