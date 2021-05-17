package com.pmcc.system.yantree.tree;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.model.ExamAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeServer  extends CommonServiceImpl<TreePojo,String> {
    @Autowired
    TreeDao treeDao;
    @Override
    protected AbstractBaseDao<TreePojo, String> getEntityDao() {
        return treeDao;
    }


}
