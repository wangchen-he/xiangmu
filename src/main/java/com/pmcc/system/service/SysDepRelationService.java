package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.system.dao.SysDepRelationDao;
import com.pmcc.system.model.SysDepRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: SysDepRelationService <br>
 * @Description: TODO 组织机构关系
 * @Date: 2020/2/6 13:52 <br>
 * @Author: darkbf <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysDepRelationService extends CommonServiceImpl<SysDepRelation,String> {

    @Autowired
    private SysDepRelationDao sysDepRelationDao;

    @Override
    protected AbstractBaseDao<SysDepRelation, String> getEntityDao() {
        return sysDepRelationDao;
    }

    //根据ID值获取对象
    public SysDepRelation getByDepID(String id) {
        return sysDepRelationDao.getByDepID(id);
    }

    /**
     * 根据映射表父ID 获取子集
     *
     * @param parentID
     * @return
     */
    public List<SysDepRelation> getChildrens(String parentID) {
        return sysDepRelationDao.getChildrens(parentID);
    }
}
