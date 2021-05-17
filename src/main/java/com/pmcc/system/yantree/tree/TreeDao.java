package com.pmcc.system.yantree.tree;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TreeDao extends AbstractBaseDao<TreePojo, String> {
   @Autowired
   GetUser sysuser;
   public  String getid(){
      SysUser user= sysuser.getUsername();
      String depid=user.getDeptID();
      return "s";
   }
}
